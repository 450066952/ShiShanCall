package com.socket.server.command.dto;

import java.io.Serializable;

import org.jboss.netty.channel.Channel;

public class LoginResultDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String success;
	private String isNeedUpdate;
	private String guid;
	private String msg;
	private String url;
	private int model;
	private int isvoice;
	private String name;
	private String name_cn;
	private String name_en;
	private String welcome;
	private String tel;
	private String qrcode;
	private String schoolname;
	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getName_cn() {
		return name_cn;
	}

	public void setName_cn(String name_cn) {
		this.name_cn = name_cn;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsvoice() {
		return isvoice;
	}

	public void setIsvoice(int isvoice) {
		this.isvoice = isvoice;
	}

	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getIsNeedUpdate() {
		return isNeedUpdate;
	}
	public void setIsNeedUpdate(String isNeedUpdate) {
		this.isNeedUpdate = isNeedUpdate;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
