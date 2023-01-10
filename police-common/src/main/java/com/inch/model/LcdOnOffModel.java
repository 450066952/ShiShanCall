package com.inch.model;



public class LcdOnOffModel extends BaseModel  {

	private String guid;
	private int schoolid;
	private String startup;
	private String shutdown;
	private int adduser;
	private String addusername;
	private String addtime;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public int getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}
	public String getStartup() {
		return startup;
	}
	public void setStartup(String startup) {
		this.startup = startup;
	}
	public String getShutdown() {
		return shutdown;
	}
	public void setShutdown(String shutdown) {
		this.shutdown = shutdown;
	}
	public int getAdduser() {
		return adduser;
	}
	public void setAdduser(int adduser) {
		this.adduser = adduser;
	}
	public String getAddusername() {
		return addusername;
	}
	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}
}
