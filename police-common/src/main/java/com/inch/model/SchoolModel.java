package com.inch.model;

import java.util.List;


public class SchoolModel extends BaseModel{
	
	
	private Integer id;//   id主键
	private String rootid;//   名称
	private int pid;//   字典代码
	private String text;
	private Integer sortby;
	private String name;
	private Integer childcnt;
	private Integer adduser;
	private String[] ids;
	private List<SchoolModel> children;
	private String pic;
	private String bgpic;
	private String invitecode;
	private String morning;
	private String siesta;
	private int morning_show;
	private int siesta_show;

	public int getMorning_show() {
		return morning_show;
	}

	public void setMorning_show(int morning_show) {
		this.morning_show = morning_show;
	}

	public int getSiesta_show() {
		return siesta_show;
	}

	public void setSiesta_show(int siesta_show) {
		this.siesta_show = siesta_show;
	}

	public String getMorning() {
		return morning;
	}

	public void setMorning(String morning) {
		this.morning = morning;
	}

	public String getSiesta() {
		return siesta;
	}

	public void setSiesta(String siesta) {
		this.siesta = siesta;
	}

	public String getInvitecode() {
		return invitecode;
	}
	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRootid() {
		return rootid;
	}
	public void setRootid(String rootid) {
		this.rootid = rootid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getSortby() {
		return sortby;
	}
	public void setSortby(Integer sortby) {
		this.sortby = sortby;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getChildcnt() {
		return childcnt;
	}
	public void setChildcnt(Integer childcnt) {
		this.childcnt = childcnt;
	}
	public Integer getAdduser() {
		return adduser;
	}
	public void setAdduser(Integer adduser) {
		this.adduser = adduser;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public List<SchoolModel> getChildren() {
		return children;
	}
	public void setChildren(List<SchoolModel> children) {
		this.children = children;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getBgpic() {
		return bgpic;
	}
	public void setBgpic(String bgpic) {
		this.bgpic = bgpic;
	}
}
