package com.aspire.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ConnectUtils {

	private  DefaultHttpClient httpclient =  new DefaultHttpClient();
	public  String content;
	public String uri= null;
	
	public String getContent(String uri){
		if(uri== null||"".equals(uri)){
			uri = "http://www.okooo.com/jingcai/shuju/peilv/";
		}
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Cookie", "PHPSESSID=930f32764df63d47a8cfbec0632000c9af2213bf; FirstOKURL=http%3A//www.okooo.com/jingcai/shuju/peilv/; First_Source=www.okooo.com; IMUserName=ayb0351; LastUrl=; Hm_lvt_5ffc07c2ca2eda4cc1c4d8e50804c94b=1423750989,1423831718,1424834124; Hm_lpvt_5ffc07c2ca2eda4cc1c4d8e50804c94b=1424834509; __utma=56961525.1890889758.1424834116.1424834116.1424834116.1; __utmb=56961525.8.9.1424834509429; __utmc=56961525; __utmz=56961525.1424834116.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); pm=; LStatus=N; LoginStr=%7B%22welcome%22%3A%22%u60A8%u597D%uFF0C%u6B22%u8FCE%u60A8%22%2C%22login%22%3A%22%u767B%u5F55%22%2C%22register%22%3A%22%u6CE8%u518C%22%2C%22TrustLoginArr%22%3A%7B%22alipay%22%3A%7B%22LoginCn%22%3A%22%u652F%u4ED8%u5B9D%22%7D%2C%22tenpay%22%3A%7B%22LoginCn%22%3A%22%u8D22%u4ED8%u901A%22%7D%2C%22qq%22%3A%7B%22LoginCn%22%3A%22QQ%u767B%u5F55%22%7D%2C%22weibo%22%3A%7B%22LoginCn%22%3A%22%u65B0%u6D6A%u5FAE%u535A%22%7D%2C%22renren%22%3A%7B%22LoginCn%22%3A%22%u4EBA%u4EBA%u7F51%22%7D%2C%22baidu%22%3A%7B%22LoginCn%22%3A%22%u767E%u5EA6%22%7D%2C%22weixin%22%3A%7B%22LoginCn%22%3A%22%u5FAE%u4FE1%u767B%u5F55%22%7D%2C%22snda%22%3A%7B%22LoginCn%22%3A%22%u76DB%u5927%u767B%u5F55%22%7D%7D%2C%22userlevel%22%3A%22%22%2C%22flog%22%3A%22hidden%22%2C%22UserInfo%22%3A%22%22%2C%22loginSession%22%3A%22___GlobalSession%22%7D");
		HttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();  
			content = EntityUtils.toString(entity, "gb2312");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;  
	}

	public String getContentSameOdds(String uri){
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Connection","keep-alive");
		httpGet.setHeader("Host","www.okooo.com");
		httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Cookie", "PHPSESSID=7c6a3000c6fc7408270b83e25d5be5ff07f81166; __utma=56961525.541307113.1474267195.1474944179.1474970082.12; __utmc=56961525; __utmz=56961525.1474608119.5.5.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; LastUrl=; Hm_lvt_5ffc07c2ca2eda4cc1c4d8e50804c94b=1474267201; Hm_lpvt_5ffc07c2ca2eda4cc1c4d8e50804c94b=1474944234; FirstURL=www.okooo.com/; FirstOKURL=http%3A//www.okooo.com/soccer/; First_Source=www.okooo.com; isInvitePurview=0; userCustomLottery=SportterySoccerMix; showCustomMenu=2; IMUserID=21114996; IMUserName=ayb0350; UWord=d461d8cd980f00b204e9800998ecf84527e; DRUPAL_LOGGED_IN=Y; __utmb=56961525.15.9.1474972257298");
		//httpGet.setHeader("Accept-Language","en-US,en;q=0.5");
		//httpGet.setHeader("Accept-Encoding","gzip, deflate");
		httpGet.setHeader("Referer","http://www.okooo.com/jingcai/shuju/peilv/2016-09-28/");
		//httpGet.setHeader("Upgrade-Insecure-Requests","1");
		//httpGet.setHeader("If-Modified-Since","Tue, 27 Sep 2016 10:31:01 GMT");
		//httpGet.setHeader("Cache-Control","max-age=0");
		HttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity, "gb2312");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
