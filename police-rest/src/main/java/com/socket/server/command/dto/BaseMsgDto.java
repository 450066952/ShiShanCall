package com.socket.server.command.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseMsgDto implements Serializable {
	/**  */
	private static final long serialVersionUID = 2811473017885476973L;

	private String sendname;
	private String receivename;
	private String guid;
	private Date rectime;
	private byte[] msgContent;//消息内容
	
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getReceivename() {
		return receivename;
	}
	public void setReceivename(String receivename) {
		this.receivename = receivename;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Date getRectime() {
		return rectime;
	}
	public void setRectime(Date rectime) {
		this.rectime = rectime;
	}
	public byte[] getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(byte[] msgContent) {
		this.msgContent = msgContent;
	}
}
