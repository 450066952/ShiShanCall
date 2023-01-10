package com.inch.model;

import java.util.List;


public class VideoModel extends BaseModel  {
	
	private String guid;
	private String title;
	private String videourl;
	private String addtime;
	private int adduser;
	
	private String addusername;
	private String classid;
	private String[] classids;
	private List<SchoolModel> classList;
	private String begintime;
	private String endtime;
	private int unshared;
	private String oldclassid;
	private int sort;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

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
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
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
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVideourl() {
		return videourl;
	}
	public void setVideourl(String videourl) {
		this.videourl = videourl;
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
	public List<SchoolModel> getClassList() {
		return classList;
	}
	public void setClassList(List<SchoolModel> classList) {
		this.classList = classList;
	}
}
