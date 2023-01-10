package com.inch.model;


public class ClassInfoModel extends BaseModel  {
	
	private String guid;	private String teacher;
	private String monitor;
	private String convention;
	private String goal;
	private String classid;
	private String classname;
	private String addtime;
	private int adduser;
	private String addusername;
	private String pic;
	private String classpic;
	private String schoolids;
	
	public String getClasspic() {
		return classpic;
	}
	public void setClasspic(String classpic) {
		this.classpic = classpic;
	}
	public String getSchoolids() {
		return schoolids;
	}
	public void setSchoolids(String schoolids) {
		this.schoolids = schoolids;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	public String getConvention() {
		return convention;
	}
	public void setConvention(String convention) {
		this.convention = convention;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
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
