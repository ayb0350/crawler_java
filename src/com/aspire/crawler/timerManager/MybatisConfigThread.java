package com.aspire.crawler.timerManager;

import java.io.Reader;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class MybatisConfigThread extends Thread {
	protected Logger log = Logger.getLogger(this.getName());

	protected SqlSessionFactory ssf = null;
	
	public MybatisConfigThread() {
		try {
			String resource = "config/mybatis.cfg.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			ssf = new SqlSessionFactoryBuilder().build(reader);
			log.info("加载MyBatis配置信息成功。。。");
		} catch (Exception e) {
			log.error("获取MyBatis配置失败", e);
		}
	}
	
}