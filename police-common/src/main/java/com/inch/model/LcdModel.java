package com.inch.model;

import java.util.List;

public class LcdModel extends BaseModel  {
	
	private String guid;
	private String name;
	private int classid;
	private int schoolid;
	private int gradeid;
	private String classname;
	private String gradename;
	private String schoolname;
	private String addtime;
	private String edittime;
	private int adduser;
	private String state;
	private String version;
	private int model;
	private int isvoice;
	private String winguid;
	private String[] winguids;
	private List<String> winList;

	public List<String> getWinList() {
		return winList;
	}

	public void setWinList(List<String> winList) {
		this.winList = winList;
	}

	public int getIsvoice() {
		return isvoice;
	}

	public void setIsvoice(int isvoice) {
		this.isvoice = isvoice;
	}

	public String[] getWinguids() {
		return winguids;
	}
	public void setWinguids(String[] winguids) {
		this.winguids = winguids;
	}
	public String getWinguid() {
		return winguid;
	}
	public void setWinguid(String winguid) {
		this.winguid = winguid;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getAdduser() {
		return adduser;
	}
	public void setAdduser(int adduser) {
		this.adduser = adduser;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getEdittime() {
		return edittime;
	}
	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}
	public int getGradeid() {
		return gradeid;
	}
	public void setGradeid(int gradeid) {
		this.gradeid = gradeid;
	}
	
}
