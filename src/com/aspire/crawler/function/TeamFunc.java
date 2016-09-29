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

import com.aspire.crawler.bean.TeamInfo;
import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.util.ConnectUtils;
import com.aspire.util.FileUtils;
import com.aspire.util.ParamComm;

public class TeamFunc {
	public static Logger log4j = Logger.getLogger(TeamFunc.class.getSimpleName());

	private static  MemcachedClient client = ParamComm.getInstance().getMemcached();

	public static  String url = ParamComm.getInstance().getCrawlerUrl();

	public static boolean  process = false;

	static FootballMapperDao dao = new FootballMapperDao();

	private static DefaultHttpClient httpclient = new DefaultHttpClient();

	public TeamFunc(){

	}
	public static void main(String[] args) throws TimeoutException, InterruptedException, MemcachedException {
		System.out.println(client.get("wgy_18734120027"));
	}

	public static boolean doCrawler(SqlSession sqlSession){
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
					Elements elements = doc.select("div.magazineDateTit b,div.magazineDateTit span,div.titname_box strong,"
							+ "div.pai p:not(.pai_p2),div.pai1 p:not(.pai_p2)");
					int size = elements.size();
					if(size == 0){
						continue;
					}
					//					log4j.info(size);
					TeamInfo teamInfo = new TeamInfo();
					String wc = null;
					//					log4j.info(elements.text());
					boolean checkDate = true;
					for(int i=0;i<size&&checkDate;i++){
						//						log4j.info(elements.get(i).text());
						String info = elements.get(i).text().trim(); 
						int j = (i+1)%8;
						switch(j){
						case 1:
							wc = info;
							String week = info.substring(0,2);
							int matches = Integer.valueOf(info.substring(2));
							teamInfo.setWeek(week);
							teamInfo.setMatches(matches);
							continue;
						case 2:
							String league = info;
							teamInfo.setLeague(league);
							continue;
						case 3:
							String date_time = info.replace(" ", "");
							String date = info.substring(0,5);
							String dwc_info = client.get(wc+date_time);
							if(dwc_info!=null && dwc_info.equals(wc+date_time)){
								checkDate = false;
								log4j.info("暂无新数据...");
								break;
							}else{
								String time = date_time.substring(5);
								teamInfo.setDate(date);
								teamInfo.setTime(time);
								client.add(wc+date_time, 0, wc+date_time);
								continue;
							}
						case 4:
							int host_ranking = FileUtils.getTeamRanking(info);
							teamInfo.setHost_ranking(host_ranking);
							continue;
						case 5:
							String host_team = info;
							teamInfo.setHost_team(host_team);
							continue;
						case 6:
							String result = info;
							if(info.equalsIgnoreCase("vs")){
								teamInfo.setHost_result(-1);
								teamInfo.setGuest_result(-1);
								continue;
							}
							int host_result = Integer.valueOf(info.substring(0,1));
							int guest_result = Integer.valueOf(info.substring(2,3));
							teamInfo.setHost_result(host_result);
							teamInfo.setGuest_result(guest_result);
							continue;
						case 7:
							String guest_team = info;
							teamInfo.setGuest_team(guest_team);
							continue;
						case 0:
							int guest_ranking = FileUtils.getTeamRanking(info);
							teamInfo.setGuest_ranking(guest_ranking);
							Transaction trans = new JdbcTransaction(sqlSession.getConnection());
							dao.addTeamInfo(sqlSession,teamInfo);
							trans.commit();
							teamInfo=new TeamInfo();
							break;
						}
					}
				}
			}

		} catch (TimeoutException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (MemcachedException e1) {
			e1.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		process = false;
		return process;
	}

}
