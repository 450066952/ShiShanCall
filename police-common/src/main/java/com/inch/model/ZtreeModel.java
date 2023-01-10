package com.inch.model;

import java.io.Serializable;

public class ZtreeModel implements Serializable{
	/**
	 * { id:1, pId:0, name:"随意勾选 1", open:true},
		 			{ id:11, pId:1, name:"随意勾选 1-1", open:true},
		 			{ id:111, pId:11, name:"随意勾选 1-1-1"},
		 			{ id:112, pId:11, name:"随意勾选 1-1-2"},
		 			{ id:12, pId:1, name:"随意勾选 1-2", open:true},
		 			{ id:121, pId:12, name:"随意勾选 1-2-1"},
		 			{ id:122, pId:12, name:"随意勾选 1-2-2"},
		 			{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
		 			{ id:21, pId:2, name:"随意勾选 2-1"},
		 			{ id:22, pId:2, name:"随意勾选 2-2", open:true},
		 			{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
		 			{ id:222, pId:22, name:"随意勾选 2-2-2"},
		 			{ id:23, pId:2, name:"随意勾选 2-3"}
	 */
	private static final long serialVersionUID = 6562619880938964950L;
	private String guid;
	private String sn;
	private String flag;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
}
