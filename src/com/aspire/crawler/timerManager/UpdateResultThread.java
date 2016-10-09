package com.aspire.crawler.timerManager;

import com.aspire.crawler.function.UpdateResult;
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

			while(true){
				SqlSession session = ssf.openSession();
				log4j.info("开始处理没有比分结果的记录。。。");
				UpdateResult.doUpdateResult(session);
				log4j.info("没有比分结果的记录处理完成。。。");
				try {
					Thread.sleep(10000*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}

}