package com.aspire.crawler.dao;

import java.util.HashMap;
import java.util.List;

import com.aspire.crawler.bean.*;
import org.apache.ibatis.session.SqlSession;


public class FootballMapperDao {

	public void addContent(SqlSession session,List<String> list) {
		session.insert("addBocai", list);
	}
	
	public void addTeamInfo(SqlSession session,TeamInfo teamInfo){
		session.insert("addTeamInfo",teamInfo);
	}
	
	public List<BocaiInfo> getBocaiList(SqlSession session){
		List<BocaiInfo> list = session.selectList("getBocaiList");
		return list;
	}

	/**
	 * 检查初始赔率数据是否存在
	 * @param session
	 * @param week
	 * @param matches
	 * @param date
	 * @param time
	 * @param bocai_id
	 * @param team_id
	 * @return
	 */
	public boolean checkFirstOdds(SqlSession session,String week,int matches,
			String date,String time,int bocai_id,Integer team_id){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("week", week);
		params.put("matches", matches);
		params.put("date", date);
		params.put("time", time);
		params.put("bocaiId", bocai_id);
		params.put("teamId", team_id);
		Integer obj = session.selectOne("checkFirstOdds", params);
		
		if (obj == 0) {
			return false;
		}
		return true;
	}

	/**
	 *通过bocaiId,teamId检查相同赔率的数据是否存在
	 * @param session
	 * @param team_id
	 * @param bocai_id
	 * @return
	 */
	public boolean checkSameOdds(SqlSession session,Integer team_id,Integer bocai_id){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("bocaiId", bocai_id);
		params.put("teamId", team_id);
		Integer obj = session.selectOne("checkSameOdds", params);
		if (obj == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 新增相同赔率 数据
	 * @param session
	 * @param sameOdds
	 */
	public void addSameOdds(SqlSession session,SameOdds sameOdds){
		session.insert("addSameOdds",sameOdds);
	}

	/**
	 * 通过week,matches,date,time查询t_team_info 是否已存在该球队
	 * @param session
	 * @param week
	 * @param matches
	 * @param date
	 * @param time
	 * @return
	 */
	public Integer getTeamInfoId(SqlSession session,String week,int matches,String date,String time){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("week", week);
		params.put("matches", matches);
		params.put("date", date);
		params.put("time", time);
		
		Integer obj = session.selectOne("getTeamInfoId", params);
		
		return obj;
	}

	/**
	 * 新增-初始赔率
	 * @param session
	 * @param firstOdds
	 */
	public void addFirstOdds(SqlSession session,FirstOddsInfo firstOdds){
		session.insert("addFirstOdds",firstOdds);
	}

	/**
	 * 新增-最新赔率
	 * @param session
	 * @param newOdds
	 */
	public void addNewOdds(SqlSession session,NewOddsInfo newOdds){
		session.insert("addNewOdds",newOdds);
	}

	/**
	 * 新增凯利差
	 * @param session
	 * @param kailiCha
	 */
	public void addKailiCha(SqlSession session,KailiChaInfo kailiCha){
		session.insert("addKailiCha",kailiCha);
	}

	/**
	 * 查询t_first_odds信息
	 * @param session
	 * @return
	 */
	public List<FirstOddsInfo> getFirstOdds(SqlSession session){
		List<FirstOddsInfo> list = session.selectList("com.aspire.crawler.dao.FootballMapperDao.getFirstOdds");
		return list;
	}

	/**
	 * 获取没有比分结果的信息
	 * @param session
	 * @return
	 */
	public List<String> getUnresultList(SqlSession session){
		List<String> list = session.selectList("getUnresultList");
		return list;
	}

	public void deleteUnresultData(SqlSession session,String datedata){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("datedata", datedata);
		session.delete("deleteUnresultData",params);
	}

	public void updateUnresultData(SqlSession session,TeamInfo teamInfo){
		session.update("updateUnresultData", teamInfo);
	}
}
