package com.aspire.crawler.bean;

public class NewOddsInfo {

	
	public int id;
	public int team_id;
	public int bocai_id;
	public double new_odds_sheng;
	public double new_odds_ping;
	public double new_odds_fu;
	public double loss_ration;
	public String create_time;
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
	public double getNew_odds_sheng() {
		return new_odds_sheng;
	}
	public void setNew_odds_sheng(double new_odds_sheng) {
		this.new_odds_sheng = new_odds_sheng;
	}
	public double getNew_odds_ping() {
		return new_odds_ping;
	}
	public void setNew_odds_ping(double new_odds_ping) {
		this.new_odds_ping = new_odds_ping;
	}
	public double getNew_odds_fu() {
		return new_odds_fu;
	}
	public void setNew_odds_fu(double new_odds_fu) {
		this.new_odds_fu = new_odds_fu;
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
