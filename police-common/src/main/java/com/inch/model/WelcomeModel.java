package com.inch.model;

import java.util.List;


public class WelcomeModel extends BaseModel  {

	private String guid;
	private String ticketno;
	private String exam;	private String content;
	private String bgcolor;
	private int timeweather;
	private String addtime;
	private String edittime;
	private int adduser;
	private String addusername;
	private String classid;
	private String[] classids;
	private List<SchoolModel> classList;
	private String begintime;
	private String endtime;
    private int unshared;
    private String oldclassid;
	
	public String getOldclassid() {
		return oldclassid;
	}
	public void setOldclassid(String oldclassid) {
		this.oldclassid = oldclassid;
	}
	public int getUnshared() {
		return unshared;
	}
	public void setUnshared(int unshared) {
		this.unshared = unshared;
	}
	
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getTicketno() {
		return ticketno;
	}
	public void setTicketno(String ticketno) {
		this.ticketno = ticketno;
	}
	public String getExam() {
		return exam;
	}
	public void setExam(String exam) {
		this.exam = exam;
	}
	public List<SchoolModel> getClassList() {
		return classList;
	}
	public void setClassList(List<SchoolModel> classList) {
		this.classList = classList;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBgcolor() {
		return bgcolor;
	}
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	public int getTimeweather() {
		return timeweather;
	}
	public void setTimeweather(int timeweather) {
		this.timeweather = timeweather;
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
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String[] getClassids() {
		return classids;
	}
	public void setClassids(String[] classids) {
		this.classids = classids;
	}
}
