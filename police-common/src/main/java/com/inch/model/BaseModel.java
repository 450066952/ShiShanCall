package com.inch.model;

import java.io.Serializable;

public class BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9109708355395711237L;
	/**
	 * 
	 */
	
	private int draw;
	private int start;
	private int length;
	private int pageno;
	private int pageindex;
	private int isadmin;
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	public int getPageindex() {
		return pageindex;
	}
	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
//		if(start==0){
//			start=1;
//		}
		this.start = start;
	}
	public int getLength() {
		if(length==0){
			length=10;
		}
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getPageno() {
		
		if(pageno>0){
			return pageno;
		}else{
			pageno=(int) Math.ceil(((double)start)/length);
			return pageno+1;
		}
	}
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}



	
}
