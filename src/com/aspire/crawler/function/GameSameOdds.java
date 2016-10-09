package com.aspire.crawler.function;

import com.aspire.crawler.bean.FirstOddsInfo;
import com.aspire.crawler.bean.KailiChaInfo;
import com.aspire.crawler.bean.NewOddsInfo;
import com.aspire.crawler.bean.SameOdds;
import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.util.ConnectUtils;
import com.aspire.util.FileUtils;
import com.aspire.util.ParamComm;
import com.aspire.util.httpclient.Singleton;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.ibatis.type.DoubleTypeHandler;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 扫描初始指数入库
 * @author yangbin
 *
 */
public class GameSameOdds {

	public static Logger log4j = Logger.getLogger(GameSameOdds.class.getSimpleName());

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

	public GameSameOdds(){

	}

	public static boolean doScanGameSameOddsUrl(SqlSession sqlSession) {
		process = true;
		ConnectUtils conntectUtils = new ConnectUtils();
		try {
			List<FirstOddsInfo> list = dao.getFirstOdds(sqlSession);
			System.out.println("扫描比赛数量:"+list.size());
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					FirstOddsInfo foi = list.get(i);
					String url = foi.getSame_odds_url();
					String content_ =  conntectUtils.getContentSameOdds(url);
					Document doc_ = Jsoup.parse(content_);
					Element elements = doc_.getElementById("paging");
					int page = 1;
					if(elements!=null){
						Elements span = elements.select("span");
						page = span.size()-2;
					}
					System.out.println((i+1)+"  扫描比赛:"+foi.getId()+"  扫描链接:"+foi.getSame_odds_url());
					for(int k=1;k<=page;k++){
						String content =  conntectUtils.getContentSameOdds(url+"&rqvalue=&page="+k);
						Document doc = Jsoup.parse(content);
						Elements elePage = doc.getElementsByClass("sjbg01");
						int size = elePage.size();

						for(int j=0;j<size;j++){
							try{
								String[] oddsInfo = elePage.get(j).text().trim().split(" ");
								SameOdds so = new SameOdds();
								String league = oddsInfo[0];
								String game_date = oddsInfo[1];
								String game_time = oddsInfo[2];
								String odds_begin = oddsInfo[3];
								double	 odds_sheng_begin = Double.valueOf(odds_begin.substring(0,4).replace(" ",""));
								double	 odds_ping_begin = Double.valueOf(odds_begin.substring(7,11).replace(" ",""));
								double	 odds_fu_begin = Double.valueOf(odds_begin.substring(14).replace(" ",""));
								System.out.println(oddsInfo[0]+"  "+oddsInfo[1]+"  "+oddsInfo[2]+"  "+oddsInfo[3]);
								String odds_end = oddsInfo[4];
								double	 odds_sheng_end = Double.valueOf(odds_end.substring(0,4).replace(" ",""));
								double	 odds_ping_end = Double.valueOf(odds_end.substring(7,11).replace(" ",""));
								double	 odds_fu_end = Double.valueOf(odds_end.substring(14,18).replace(" ",""));
								String host_team = oddsInfo[5];
								String[] team_result = oddsInfo[6].split(":");
								int host_result = -1;
								int guest_result = -1;
								for(int m3=0;m3<team_result.length;m3++){
									host_result = Integer.valueOf(team_result[0].replace(" ",""));
									guest_result = Integer.valueOf(team_result[1].replace(" ",""));
								}
								String guest_team = oddsInfo[7];
								String result_ = oddsInfo[8];
								int result = result_.equals("胜")?3:result_.equals("平")?1:result_.equals("负")?0:100;
								so.setTeam_id(foi.getTeam_id());
								so.setBocai_id(foi.getBocai_id());
								so.setLeague(league);
								so.setHost_team(host_team);
								so.setGuest_team(guest_team);
								so.setOdds_sheng_begin(odds_sheng_begin);
								so.setOdds_ping_begin(odds_ping_begin);
								so.setOdds_fu_begin(odds_fu_begin);
								so.setOdds_sheng_end(odds_fu_end);
								so.setOdds_ping_end(odds_ping_end);
								so.setOdds_fu_end(odds_fu_end);
								so.setHost_result(host_result);
								so.setGuest_result(guest_result);
								so.setResult(result);
								so.setGame_date(game_date);
								so.setGame_time(game_time);
								dao.addSameOdds(sqlSession,so);
								Transaction trans = new JdbcTransaction(sqlSession.getConnection());
								trans.commit();
							}catch (Exception e){
								e.printStackTrace();
								continue;
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		process = false;
		return process;
	}

	public  static  void  main(String args[]){
		String ss = " 6.0";
		System.out.println(ss);
		System.out.println(ss.replace(" ",""));
	}
}
