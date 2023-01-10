package com.inch.model;

import java.io.Serializable;
import java.util.Date;


public class SortModel implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	
	private Date rectime;
	private int cmd;
	private String json;
	public Date getRectime() {
		return rectime;
	}
	public void setRectime(Date rectime) {
		this.rectime = rectime;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	
}
