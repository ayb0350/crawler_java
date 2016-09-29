package com.aspire.crawler.bean;

public class TeamInfo {

	public int id;
	public String week;
	//联赛
	public String league;
	//比赛场次
	public int matches;
	public String date;
	public String time;
	public String host_team;
	public String guest_team;
	public int host_result;
	public int guest_result;
	public int host_ranking;
	public int guest_ranking;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getLeague() {
		return league;
	}
	public void setLeague(String league) {
		this.league = league;
	}
	public int getMatches() {
		return matches;
	}
	public void setMatches(int matches) {
		this.matches = matches;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getHost_team() {
		return host_team;
	}
	public void setHost_team(String host_team) {
		this.host_team = host_team;
	}
	public String getGuest_team() {
		return guest_team;
	}
	public void setGuest_team(String guest_team) {
		this.guest_team = guest_team;
	}
	public int getHost_ranking() {
		return host_ranking;
	}
	public void setHost_ranking(int host_ranking) {
		this.host_ranking = host_ranking;
	}
	public int getGuest_ranking() {
		return guest_ranking;
	}
	public void setGuest_ranking(int guest_ranking) {
		this.guest_ranking = guest_ranking;
	}
	public int getHost_result() {
		return host_result;
	}
	public void setHost_result(int host_result) {
		this.host_result = host_result;
	}
	public int getGuest_result() {
		return guest_result;
	}
	public void setGuest_result(int guest_result) {
		this.guest_result = guest_result;
	}
}
