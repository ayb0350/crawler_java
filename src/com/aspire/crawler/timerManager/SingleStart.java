package com.aspire.crawler.timerManager;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import org.apache.log4j.Logger;

/**
 * @Title. 通过文件锁形式,实现java程序的单例执行
 */
public class SingleStart {

	/** 日志记录器 */
	private static final Logger LOGGER = Logger.getLogger(SingleStart.class
			.getSimpleName());

	/**
	 * 验证程序是否单例执行
	 */
	public void checkStart() {
		FileLock lock = null;
		try {
			// 加锁，如果程序已经启动就无法获得锁
			lock = new RandomAccessFile(new File(".lock"), "rw").getChannel()
					.tryLock();
			if (null == lock) {
				LOGGER.info("程序已经启动，强制退出");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
}