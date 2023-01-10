package com.inch.model;

import java.io.Serializable;

public class InchModel implements Serializable {
	public InchModel(Boolean success, String msg, Object data){
		this.success=success;
		this.data=data;
		this.msg=msg;
	}

	private Boolean success;
	private String msg;
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
