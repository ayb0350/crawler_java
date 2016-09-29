package com.aspire.crawler.function;

import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.aspire.crawler.bean.BocaiInfo;
import com.aspire.crawler.dao.FootballMapperDao;
import com.aspire.util.ParamComm;

/**
 * 扫描菠菜，加载菠菜信息到memcached
 * @author yangbin
 *
 */
public class PutbocaiMemcached {
	
	public static Logger log4j = Logger.getLogger(PutbocaiMemcached.class.getSimpleName());
	
	
	static FootballMapperDao dao = new FootballMapperDao();
	
	public static boolean putbocai(SqlSession sqlSession){
		
		try {
			MemcachedClient client = ParamComm.getInstance().getMemcached();
			List<BocaiInfo> list = dao.getBocaiList(sqlSession);
			for(BocaiInfo info : list){
//				log4j.info(info.bocai_name+"---"+info.id+"\n");
				String key = info.bocai_name.replaceAll("\\p{Cntrl}]|\\p{Space}", "_");
				client.add(key, 0, info.id);
			}  
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
