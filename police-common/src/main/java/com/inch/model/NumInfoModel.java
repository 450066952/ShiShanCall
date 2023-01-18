package com.inch.model;

import java.util.List;
import java.util.Map;

public class NumInfoModel extends BaseModel  {
	
	private String guid;
	private String cid;
	private String  num;
	private String type;
	private int isused;
	private String idcard;
	private String addtime;
	private String typename;
	private int waitcnt;
	private String winname;
	private String prefix;
	private List<Map<String,String>> infoList;
	private List<String> wList;
	private String cname;
	private String cardno;
	private int orgid;
	private String childs;
	private String childname;
	private String userid;
	private String name;
	private String winid;
	private String msg;
	private int ordertype;
	private int areatype;
	private int level;
	private int status;
	private String starttime;
	private String endtime;
	private int foreigns;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getForeigns() {
		return foreigns;
	}

	public void setForeigns(int foreigns) {
		this.foreigns = foreigns;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}

	public int getAreatype() {
		return areatype;
	}

	public void setAreatype(int areatype) {
		this.areatype = areatype;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWinid() {
		return winid;
	}

	public void setWinid(String winid) {
		this.winid = winid;
	}

	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}

	public List<String> getwList() {
		return wList;
	}

	public List<Map<String, String>> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Map<String, String>> infoList) {
		this.infoList = infoList;
	}

	public void setwList(List<String> wList) {
		this.wList = wList;
	}

	public String getChilds() {
		return childs;
	}

	public void setChilds(String childs) {
		this.childs = childs;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getWinname() {
		return winname;
	}

	public void setWinname(String winname) {
		this.winname = winname;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getWaitcnt() {
		return waitcnt;
	}

	public void setWaitcnt(int waitcnt) {
		this.waitcnt = waitcnt;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsused() {
		return isused;
	}

	public void setIsused(int isused) {
		this.isused = isused;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
}
