package com.aspire.util.httpclient;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;

public class Singleton{
	private static DefaultHttpClient httpclient;
	private static Singleton instance = null;  
	public static String content;
	public String uri= null;
	private static class SingletonInstance{
		private static Singleton param = new Singleton();
	}
	   
    private Singleton() {  
       if(httpclient == null){
    	   httpclient =  new DefaultHttpClient();
       }
    }  
   
    public static Singleton getInstance() {  
      return SingletonInstance.param;
    }  
    
    
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
}
