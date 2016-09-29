package com.aspire.crawler.timerManager;

import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.crawler.function.GameSameOdds;
import com.aspire.crawler.function.PutbocaiMemcached;
import com.aspire.crawler.function.ScanFirstOdds;
import com.aspire.crawler.function.TeamFunc;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


/**
 * 扫描对阵球队，比赛时间
 */
public class GameSameOddsThread extends MybatisConfigThread{

	public static Logger log4j = Logger.getLogger(GameSameOddsThread.class.getSimpleName());
	
	FootballMapperDao bocaiDao = new FootballMapperDao();

	public void run(){

			while(true){
				SqlSession session = ssf.openSession();
				log4j.info("开始扫描历史相同赔率数据。。。");
				GameSameOdds.doScanGameSameOddsUrl(session);
				try {
					Thread.sleep(10000*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
	}

}