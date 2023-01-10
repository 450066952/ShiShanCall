package com.socket.server.command.dto;

import java.io.Serializable;

import org.jboss.netty.channel.Channel;

public class ReauestLoginDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7617642273430095973L;
	private String guid;
	private String version;
	private Channel channel;
	private int model;
	private int type;
	private String winguid;
	private int isvoice;
	private int schoolid;

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}

	public int getIsvoice() {
		return isvoice;
	}

	public void setIsvoice(int isvoice) {
		this.isvoice = isvoice;
	}

	public String getWinguid() {
		return winguid;
	}

	public void setWinguid(String winguid) {
		this.winguid = winguid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}
