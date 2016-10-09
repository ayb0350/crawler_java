package com.aspire.main;

import com.aspire.crawler.timerManager.*;
import org.apache.log4j.Logger;


/**
  * @see 程序入口
 */
public class AppletClient {
	
	public static Logger log4j = Logger.getLogger("");
	
	/**主程序入口*/
	public static void main(String[] args) {
		
		log4j.info("检测是否为单例执行...");
		new SingleStart().checkStart();	
		log4j.info("启动采集");
		//new BocaiManagerThread().run();
		new TeamManagerThread().run();
		//new GameSameOddsThread().run();

		//new UpdateResultThread().run();
	}
}
