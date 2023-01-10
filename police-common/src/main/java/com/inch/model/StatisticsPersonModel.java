package com.inch.model;

import java.io.Serializable;

public class StatisticsPersonModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 3456076045987322457L;
	private String starttime;
	private String endtime;
	private int score;
	private int orgid;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}
}
