package com.aspire.crawler.timerManager;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.crawler.function.PutbocaiMemcached;
import com.aspire.crawler.function.ScanFirstOdds;
import com.aspire.crawler.function.TeamFunc;


/**
 * 扫描对阵球队，比赛时间
 */
public class UpdateResultThread extends MybatisConfigThread{

	public static Logger log4j = Logger.getLogger(UpdateResultThread.class.getSimpleName());
	
	FootballMapperDao bocaiDao = new FootballMapperDao();

	public void run(){

			/*while(true){
				SqlSession session = ssf.openSession();
				PutbocaiMemcached.putbocai(session);
				log4j.info("加载菠菜完成。。");
				TeamFunc.doCrawler(session);
				log4j.info("扫描对阵双方信息完成。。。");
				ScanFirstOdds.doScan(session);
				try {
					Thread.sleep(10000*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
			
			
	}

}