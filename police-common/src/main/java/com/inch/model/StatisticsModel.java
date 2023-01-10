package com.inch.model;

import java.io.Serializable;

public class StatisticsModel implements Serializable {

	private static final long serialVersionUID = 3456076045987322457L;
	private String satisfy;
	private String satisfy1;
	private String satisfy2;
	private String basic;
	private String notsatisfy;
	private String no;
	private String flag;
	private String cname;
	private String typename;
	private String type;
	private String pic;
	private int cnt;
	private String starttime;
	private String endtime;
	private int orgid;
	private int score;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getNotsatisfy() {
		return notsatisfy;
	}

	public void setNotsatisfy(String notsatisfy) {
		this.notsatisfy = notsatisfy;
	}

	public String getSatisfy2() {
		return satisfy2;
	}

	public void setSatisfy2(String satisfy2) {
		this.satisfy2 = satisfy2;
	}

	public String getSatisfy1() {

		return satisfy1;
	}

	public void setSatisfy1(String satisfy1) {
		this.satisfy1 = satisfy1;
	}

	public String getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(String satisfy) {
		this.satisfy = satisfy;
	}

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
}
