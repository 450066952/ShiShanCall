package com.inch.model;

import java.util.List;


public class SubClassModel {
	
	private int classid;
	private int  gradeid;
	private int schoolid;
	private String classname;
	private String gradename;
	private String schoolname;
	private String gradeClassname;
	private List<GalleryPhotoModel> glist;
	
	public List<GalleryPhotoModel> getGlist() {
		return glist;
	}
	public void setGlist(List<GalleryPhotoModel> glist) {
		this.glist = glist;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getGradeid() {
		return gradeid;
	}
	public void setGradeid(int gradeid) {
		this.gradeid = gradeid;
	}
	public int getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	public String getGradeClassname() {
		return gradeClassname;
	}
	public void setGradeClassname(String gradeClassname) {
		this.gradeClassname = gradeClassname;
	}
}
