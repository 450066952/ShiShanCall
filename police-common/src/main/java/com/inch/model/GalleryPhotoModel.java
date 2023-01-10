package com.inch.model;


public class GalleryPhotoModel extends BaseModel  {
	
	private String guid;	private String pguid;
	private String pic;
	private String picsmall;
	private String picpress;
	private String describ;
	private String addtime;
	private int adduser;
	private String addusername;
	private String title;
	private String classid;
	private String begintime;
	private String endtime;
	
	
	public String getPicpress() {
		return picpress;
	}
	public void setPicpress(String picpress) {
		this.picpress = picpress;
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
	public String getPicsmall() {
		return picsmall;
	}
	public void setPicsmall(String picsmall) {
		this.picsmall = picsmall;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
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
	public String getPguid() {
		return pguid;
	}
	public void setPguid(String pguid) {
		this.pguid = pguid;
	}
	public String getDescrib() {
		return describ;
	}
	public void setDescrib(String describ) {
		this.describ = describ;
	}
	
}
