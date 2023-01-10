package com.inch.model;

public class SysUserModel extends BaseModel {
	

		private Integer id;//   id主键	private String username;//   邮箱也是登录帐号	private String password;//   登录密码	private String name;//   昵称
	private int state;
	private String addtime;
	private String schoolids;
	private String guid;
	private String headerClassName;
	private int schoolid;
	
	
	public int getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}
	public String getHeaderClassName() {
		return headerClassName;
	}
	public void setHeaderClassName(String headerClassName) {
		this.headerClassName = headerClassName;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getSchoolids() {
		return schoolids;
	}
	public void setSchoolids(String schoolids) {
		this.schoolids = schoolids;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	} 
}
