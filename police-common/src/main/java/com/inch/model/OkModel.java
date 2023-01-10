package com.inch.model;

import java.io.Serializable;

public class OkModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6562619880938964950L;
	private String guid;
	private String sn;
	private String flag;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
}
