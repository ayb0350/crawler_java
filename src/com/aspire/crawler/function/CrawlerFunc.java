package com.aspire.crawler.function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aspire.util.ParamComm;

public class CrawlerFunc {
	public static Logger log4j = Logger.getLogger(CrawlerFunc.class.getSimpleName());
	
	/**
	 * 连接网站，模拟浏览器登陆，避免网站识别为手机进入
	 */
	private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0";
	
	/**
	 *设置超时时间 
	 */
	private static final int timeout = 50000000;
	
	public static  String url = ParamComm.getInstance().getCrawlerUrl();
	
	public static boolean  process = false;
	
	public static List<String> doCrawler(){
		List<String> list = new ArrayList<String>();
		process = true;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent(USERAGENT).timeout(timeout).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements elements = doc.getElementsByAttributeValue("class", "clearfix container_wrapper pankoudata");
		for (Element e:elements) {
			String name = e.select(".gray6").text();
			String[] nameArr = name.split(" ");
			for(String str : nameArr){
				log4j.info(new String("菠菜名称："+str));
				list.add(str);
			}
			break;
		}
		process = false;
		return list;
	}
	
}
