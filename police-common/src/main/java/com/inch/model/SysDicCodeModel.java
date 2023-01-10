package com.inch.model;


public class SysDicCodeModel extends BaseModel{
	
	
	private String guid;
	private String name;
	private String rootid;
	private int isshow;
	private String addtime;
	private Integer paixu;
	private Integer orgid;
	private String orgname;



	public int getIsshow() {
		return isshow;
	}

	public void setIsshow(int isshow) {
		this.isshow = isshow;
	}

	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRootid() {
		return rootid;
	}
	public void setRootid(String rootid) {
		this.rootid = rootid;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtie(String addtime) {
		this.addtime = addtime;
	}

	public Integer getPaixu() {
		return paixu;
	}

	public void setPaixu(Integer paixu) {
		this.paixu = paixu;
	}

	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
}
