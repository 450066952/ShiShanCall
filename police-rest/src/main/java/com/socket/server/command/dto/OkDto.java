package com.socket.server.command.dto;

import java.io.Serializable;

public class OkDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6562619880938964950L;
	private String guid;
	private String username;
	private String flag;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
