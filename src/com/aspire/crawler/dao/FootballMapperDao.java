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

	public void addSameOdds(SqlSession session,SameOdds sameOdds){
		session.insert("addSameOdds",sameOdds);
	}

	public Integer getTeamInfoId(SqlSession session,String week,int matches,String date,String time){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("week", week);
		params.put("matches", matches);
		params.put("date", date);
		params.put("time", time);
		
		Integer obj = session.selectOne("getTeamInfoId", params);
		
		return obj;
	}
	
	public void addFirstOdds(SqlSession session,FirstOddsInfo firstOdds){
		session.insert("addFirstOdds",firstOdds);
	}
	
	public void addNewOdds(SqlSession session,NewOddsInfo newOdds){
		session.insert("addNewOdds",newOdds);
	}
	
	public void addKailiCha(SqlSession session,KailiChaInfo kailiCha){
		session.insert("addKailiCha",kailiCha);
	}

	public List<FirstOddsInfo> getFirstOdds(SqlSession session){
		List<FirstOddsInfo> list = session.selectList("com.aspire.crawler.dao.FootballMapperDao.getFirstOdds");
		return list;
	}


}
