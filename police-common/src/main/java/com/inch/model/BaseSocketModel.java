package com.inch.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class BaseSocketModel implements Serializable  {
	
	/**
	 * 1
	 */
	private static final long serialVersionUID = 1L;
	
	private String guid;
	private Object extend;
	private Date receivetime;	private List list;
	
	public Date getReceivetime() {
		return receivetime;
	}
	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}
	public Object getExtend() {
		return extend;
	}
	public void setExtend(Object extend) {
		this.extend = extend;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
