package com.inch.model;

import java.io.Serializable;

public class EvaModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6562619880938964950L;
	private String userid;
	private int score;
	private String num;
	private int id;
	private String winid;
	private String winname;
	private String addtime;
	private int orgid;
	private String name;

	public String getWinname() {
		return winname;
	}

	public void setWinname(String winname) {
		this.winname = winname;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getWinid() {
		return winid;
	}

	public void setWinid(String winid) {
		this.winid = winid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
