package com.inch.model;

import java.util.List;


public class GalleryModel extends BaseModel  {
	
	private String guid;	private String classid;
	private String pic;
	private String addtime;
	private int adduser;
	private String addusername;
	private String title;
	private String begintime;
	private String endtime;
	private int cnt;
	private String oldclassid;
	private String[] classids;
	private List<SchoolModel> classList;
	
	public String getOldclassid() {
		return oldclassid;
	}
	public void setOldclassid(String oldclassid) {
		this.oldclassid = oldclassid;
	}
	public List<SchoolModel> getClassList() {
		return classList;
	}
	public void setClassList(List<SchoolModel> classList) {
		this.classList = classList;
	}
	public String[] getClassids() {
		return classids;
	}
	public void setClassids(String[] classids) {
		this.classids = classids;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
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
