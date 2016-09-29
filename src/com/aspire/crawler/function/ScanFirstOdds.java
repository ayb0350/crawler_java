package com.aspire.crawler.function;

import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.aspire.crawler.bean.FirstOddsInfo;
import com.aspire.crawler.bean.KailiChaInfo;
import com.aspire.crawler.bean.NewOddsInfo;
import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.util.ConnectUtils;
import com.aspire.util.FileUtils;
import com.aspire.util.ParamComm;
import com.aspire.util.httpclient.Singleton;

/**
 * 扫描初始指数入库
 * @author yangbin
 *
 */
public class ScanFirstOdds {

	public static Logger log4j = Logger.getLogger(ScanFirstOdds.class.getSimpleName());

	private static  MemcachedClient client = ParamComm.getInstance().getMemcached();
	/**
	 * 连接网站，模拟浏览器登陆，避免网站识别为手机进入
	 */

	/**
	 *设置超时时间 
	 */

	public static  String url = ParamComm.getInstance().getCrawlerUrl();

	public static boolean  process = false;

	static FootballMapperDao dao = new FootballMapperDao();

	private static DefaultHttpClient httpclient = new DefaultHttpClient();

	public static String content = Singleton.getInstance().getContent(null);

	public ScanFirstOdds(){
		
	}

	public static boolean doScan(SqlSession sqlSession) {
		process = true;
		Document docOld = null;
		try {
			ConnectUtils conntectUtils = new ConnectUtils();
			String content = conntectUtils.getContent(null);
			docOld = Jsoup.parse(content);
			Elements eleDate = docOld.select("p.qihaoselect");
			String[] dateData = eleDate.text().split("期");
 			for(String datedata : dateData){
				String url1 = "http://www.okooo.com/jingcai/shuju/peilv/"+datedata.trim();
				String content1 = conntectUtils.getContent(url1);
				Document doc1 = Jsoup.parse(content1);
				Elements elePage = doc1.select("table.Pager td");
				if(elePage.text().equals("暂时没有添加记录")){
					log4j.info(datedata.trim()+"暂时没有添加记录");
					continue;
				}
				int page = Integer.valueOf(elePage.get(1).text().substring(1, 2));
				log4j.info(datedata.trim()+"共有"+page+"页");
				for(int m=1;m<=page;m++){
					/*String url = null;
					if(page == 1){
						url = url1;
					}else{
						url = url1+"/?"
								+ "LeagueID=463,7,18,372,384,498,679&HandicapNumber=-1,1&BetDate="
								+ "3,4,5&MakerType=AuthoriteBooks&PageID="+m+"&HasEnd=1";
					}*/
					String contentNew = conntectUtils.getContent(url1);
					Document doc = Jsoup.parse(contentNew);
					Elements elements = doc.select("div.clearfix div.font_14 p.float_l b");
					int size = elements.size();
					if(size == 0){
						continue;
					}
					String date_time = null;
					String wc = null;
					for(int i=0;i<size;i++){
						int j = (i+1)%3;
						switch(j){
						case 1:
							wc =  elements.get(i).text();
							continue;
						case 2:
							continue;
						case 0:
							date_time = elements.get(i).text().replace(" ", "");
							break;
						}
						//周四00102-1602:00
						String week = wc.substring(0, 2);
						int matches = Integer.valueOf(wc.substring(2));
						String date = date_time.substring(0, 5);
						String time = date_time.substring(5);

						Elements elements1 = doc.select("div.clearfix table tr:not(.tableh)").select("tr:not(.titlebg)");
											log4j.info(elements1.text());
						String firstBocai = elements1.first().text().trim().split(" ")[0];
											log4j.info("每个表格第一个bocai："+firstBocai);
						int tableSize = elements1.text().split(firstBocai).length-1;
											log4j.info("共有表格数量："+tableSize);
						for(int k=0;k<tableSize;k++){
							FirstOddsInfo firstOdds = new FirstOddsInfo();
							NewOddsInfo newOdds = new NewOddsInfo();
							KailiChaInfo kailiCha = new KailiChaInfo();
							Integer team_id = dao.getTeamInfoId(sqlSession, week, matches, date, time);
							if(team_id == null){
								return false;
							}
							String[] oddsInfo = elements1.get(k).text().trim().split(" ");
							System.out.println("i:"+i+"  k:"+((k+(i+1)/3)-1));
							//String href = "http://www.okooo.com"+elements1.get(k+(i-2)*5).getElementsByClass("detail").attr("href");
							//System.out.println(href);
							if((client.get(oddsInfo[0])==null)){
								continue;
							}
							int bocai_id = client.get(oddsInfo[0]);
							boolean boo = dao.checkFirstOdds(sqlSession, week, matches, date, time,bocai_id,team_id);
							//在t_first_odds 表中已存在记录
							if(boo){
								continue;
							}
							// 威廉.希尔 2.15 3.60 2.90 0.92 2.30↓ 3.50 2.70↑ 0.92 0.89 0.94 0.93 1.21 0.59 0.11 
							double f_sheng = Double.parseDouble(oddsInfo[1]);
							double f_ping = Double.parseDouble(oddsInfo[2]);
							double f_fu = Double.parseDouble(oddsInfo[3]);
							double f_pei = Double.parseDouble(oddsInfo[4]);
							firstOdds.setBocai_id(bocai_id);
							firstOdds.setTeam_id(team_id);
							firstOdds.setFirst_odds_sheng(f_sheng);
							firstOdds.setFirst_odds_ping(f_ping);
							firstOdds.setFirst_odds_fu(f_fu);
							firstOdds.setLoss_ration(f_pei);
							//firstOdds.setSame_odds_url(href);
							double n_sheng = Double.parseDouble(FileUtils.parseNewOdds(oddsInfo[5]));
							double n_ping = Double.parseDouble(FileUtils.parseNewOdds(oddsInfo[6]));
							double n_fu = Double.parseDouble(FileUtils.parseNewOdds(oddsInfo[7]));
							double n_pei = Double.parseDouble(oddsInfo[8]);
							newOdds.setBocai_id(bocai_id);
							newOdds.setTeam_id(team_id);
							newOdds.setNew_odds_sheng(n_sheng);
							newOdds.setNew_odds_ping(n_ping);
							newOdds.setNew_odds_fu(n_fu);
							newOdds.setLoss_ration(n_pei);
							double k_sheng = Double.parseDouble(oddsInfo[12]);
							double k_ping = Double.parseDouble(oddsInfo[13]);
							double k_fu = Double.parseDouble(oddsInfo[14]);
							kailiCha.setBocai_id(bocai_id);
							kailiCha.setTeam_id(team_id);
							kailiCha.setKaili_cha_sheng(k_sheng);
							kailiCha.setKaili_cha_ping(k_ping);
							kailiCha.setKaili_cha_fu(k_fu);
							dao.addFirstOdds(sqlSession, firstOdds);
							dao.addNewOdds(sqlSession, newOdds);
							dao.addKailiCha(sqlSession, kailiCha);
							Transaction trans = new JdbcTransaction(sqlSession.getConnection());
							trans.commit();
						}
					}

				}
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		process = false;
		return process;
	}
}
