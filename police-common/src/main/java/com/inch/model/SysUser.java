package com.inch.model;

import java.io.Serializable;
import java.util.List;


public class SysUser implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String guid;
	private String username;
	private String password;
	private String name;
	private String addtime;
	private int state;
	private Integer isadmin;
	private int adduser;
	private String addusername;
	private int schoolid;
	private String[] school;
	private String schoolids;
	private Integer[] roleIds;
	private String roleStr;
	private String pic;
	private int star;
	private String no;
	private int member;
	
	private List<String> cList;
	private int type;
	private String winid;
	private String winname;
	private String wintype;
	private String typename;
	private String schoolname;
	private String course;
	private String idcard;

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public String getWintype() {
		return wintype;
	}

	public void setWintype(String wintype) {
		this.wintype = wintype;
	}

	public String getWinname() {
		return winname;
	}

	public void setWinname(String winname) {
		this.winname = winname;
	}

	public String getWinid() {
		return winid;
	}

	public void setWinid(String winid) {
		this.winid = winid;
	}



	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getcList() {
		return cList;
	}
	public void setcList(List<String> cList) {
		this.cList = cList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
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
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Integer getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
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
	public int getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}
	public String[] getSchool() {
		return school;
	}
	public void setSchool(String[] school) {
		this.school = school;
	}
	public String getSchoolids() {
		return schoolids;
	}
	public void setSchoolids(String schoolids) {
		this.schoolids = schoolids;
	}
	public Integer[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
	public String getRoleStr() {
		return roleStr;
	}
	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}
}
