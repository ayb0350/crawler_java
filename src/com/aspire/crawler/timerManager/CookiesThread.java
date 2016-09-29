package com.aspire.crawler.timerManager;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.crawler.function.PutbocaiMemcached;
import com.aspire.crawler.function.ScanFirstOdds;
import com.aspire.crawler.function.TeamFunc;


/**
 * 循环登录，保持登录状态
 */
public class CookiesThread{

	public static Logger log4j = Logger.getLogger(TeamManagerThread.class.getSimpleName());
	
	FootballMapperDao bocaiDao = new FootballMapperDao();

	public void run(){

			while(true){
			}
			
			
	}

}