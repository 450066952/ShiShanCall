package com.inch.model;

import java.util.List;

public class NoticeModel extends BaseModel  {
	
	private String guid;	private String notice;
	private String begintime;
	private String endtime;
	private String addtime;
	private String edittime;
	private int adduser;
	private String addusername;
	private String pic; 
	private String picsmall;
	private String classid;
	private String[] classids;
	private int isscreen;
	private int isparent;
	private List<SchoolModel> classList;
	private String video;
	private String videoimg;
	private String videosmallimg;
	private String voice;
	private Integer volicelen;
	private String oldclassid;
	private String teachpic;
	
	
	public String getTeachpic() {
		return teachpic;
	}
	public void setTeachpic(String teachpic) {
		this.teachpic = teachpic;
	}
	
	public String getOldclassid() {
		return oldclassid;
	}
	public void setOldclassid(String oldclassid) {
		this.oldclassid = oldclassid;
	}
	public String getVideosmallimg() {
		return videosmallimg;
	}
	public void setVideosmallimg(String videosmallimg) {
		this.videosmallimg = videosmallimg;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getVideoimg() {
		return videoimg;
	}
	public void setVideoimg(String videoimg) {
		this.videoimg = videoimg;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public Integer getVolicelen() {
		return volicelen;
	}
	public void setVolicelen(Integer volicelen) {
		this.volicelen = volicelen;
	}
	
	
	public int getIsscreen() {
		return isscreen;
	}
	public void setIsscreen(int isscreen) {
		this.isscreen = isscreen;
	}
	public int getIsparent() {
		return isparent;
	}
	public void setIsparent(int isparent) {
		this.isparent = isparent;
	}
	public String getPicsmall() {
		return picsmall;
	}
	public void setPicsmall(String picsmall) {
		this.picsmall = picsmall;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	private int iscurrent;
	
	public int getIscurrent() {
		return iscurrent;
	}
	public void setIscurrent(int iscurrent) {
		this.iscurrent = iscurrent;
	}
	
	public List<SchoolModel> getClassList() {
		return classList;
	}
	public void setClassList(List<SchoolModel> classList) {
		this.classList = classList;
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
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
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
}
