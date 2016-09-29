package com.aspire.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Properties;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParamComm {

	/**配置文件*/
	private static Properties configProps = null;
	
	/**
	 * memcached
	 */
	private static MemcachedClient client =null;
	
	private static final Log logger = LogFactory.getLog(ParamComm.class.getSimpleName());
	
	private static class ParamCommInstance{
		private static ParamComm param = new ParamComm();
	}
	
	private ParamComm(){
		if(configProps == null){
			getParamComm("config.properties");
		}
		if(client == null){
			getMemcachedClient();
		}
	}
	
	public static ParamComm getInstance(){
		return ParamCommInstance.param;
	}
	
	/**
	 * 抓取网站地址
	 * @return
	 */
	public String getCrawlerUrl(){
		return configProps.getProperty("crawler.url");
	}
	
	/**
	 * memcached client实例
	 * @return
	 */
	public MemcachedClient getMemcached(){
    	return client;
    }
	
	private void  getMemcachedClient(){
		try {
			client =new XMemcachedClient(new InetSocketAddress("127.0.0.1",11211));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getParamComm(String propsname){
		FileInputStream fileStream = getFileContent(propsname);
		try {
			if (configProps == null) {
				configProps = new Properties();
			}
			configProps.load(fileStream);
		}
		catch (Exception e) {
			logger.warn("Can't read the properties file. " + "Make sure " + propsname + " is in the {WEB-INF}/config");
		}
		finally {
			try {
				if (fileStream != null) {
					fileStream.close();
				}
			}
			catch (IOException ioe) {
			}
		}
	}
	
	private FileInputStream getFileContent(String file) {
		FileInputStream is = null;
		try {
			String path = java.net.URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath(), "UTF-8");
			path += "config/" + file;
			is = new FileInputStream(path);
		}
		catch (FileNotFoundException e) {
			logger.warn("File " + file + " not found");
		}
		catch (UnsupportedEncodingException e) {
			logger.error("不支持UTF-8编码！", e);
		}	
		return is;
	}
	
	
}
