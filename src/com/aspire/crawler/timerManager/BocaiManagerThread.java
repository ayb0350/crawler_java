package com.aspire.crawler.timerManager;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;

import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.crawler.function.CrawlerFunc;


/**
 * 扫描菠菜
 */
public class BocaiManagerThread extends MybatisConfigThread{

	FootballMapperDao bocaiDao = new FootballMapperDao();

	public void run(){

		try {
			SqlSession session = ssf.openSession();
			List<String> list = CrawlerFunc.doCrawler();
			Transaction trans = new JdbcTransaction(session.getConnection());
			bocaiDao.addContent(session, list);
			trans.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}