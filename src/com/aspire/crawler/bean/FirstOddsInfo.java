package com.aspire.crawler.bean;

public class FirstOddsInfo {

	
	public int id;
	public int team_id;
	public int bocai_id;
	public double first_odds_sheng;
	public double first_odds_ping;
	public double first_odds_fu;
	public double loss_ration;
	public String create_time;
	public String same_odds_url;

	public String getSame_odds_url() {
		return same_odds_url;
	}

	public void setSame_odds_url(String same_odds_url) {
		this.same_odds_url = same_odds_url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public int getBocai_id() {
		return bocai_id;
	}
	public void setBocai_id(int bocai_id) {
		this.bocai_id = bocai_id;
	}
	public double getFirst_odds_sheng() {
		return first_odds_sheng;
	}
	public void setFirst_odds_sheng(double first_odds_sheng) {
		this.first_odds_sheng = first_odds_sheng;
	}
	public double getFirst_odds_ping() {
		return first_odds_ping;
	}
	public void setFirst_odds_ping(double first_odds_ping) {
		this.first_odds_ping = first_odds_ping;
	}
	public double getFirst_odds_fu() {
		return first_odds_fu;
	}
	public void setFirst_odds_fu(double first_odds_fu) {
		this.first_odds_fu = first_odds_fu;
	}
	public double getLoss_ration() {
		return loss_ration;
	}
	public void setLoss_ration(double loss_ration) {
		this.loss_ration = loss_ration;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}
