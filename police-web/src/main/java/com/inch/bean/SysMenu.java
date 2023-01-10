package com.inch.bean;

import java.util.List;


public class SysMenu extends BaseBean {
	
		private String id;	private String name;
	private String url;	private Integer parentId;	private Integer deleted;	private String createTime;
	private String updateTime;	private Integer rank;
	private String actions;
	private String icon;
	private Boolean open;
	
	private int subCount;
	private String pname;
	
	private List<SysMenu> childList;
	//菜单按钮
	private List<SysMenuBtn> btns;

	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public List<SysMenu> getChildList() {
		return childList;
	}
	public void setChildList(List<SysMenu> childList) {
		this.childList = childList;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public List<SysMenuBtn> getBtns() {
		return btns;
	}
	public void setBtns(List<SysMenuBtn> btns) {
		this.btns = btns;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public int getSubCount() {
		return subCount;
	}
	public void setSubCount(int subCount) {
		this.subCount = subCount;
	}	
}
