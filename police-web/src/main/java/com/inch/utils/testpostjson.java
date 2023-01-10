package com.inch.utils;

import com.inch.model.SysUser;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.HttpClientUtil;

public class testpostjson {

	public static void main(String[] args) {

		SysUser sysUser = new SysUser();

		sysUser.setName("testjson");

		HttpClientUtil.doPostJson(
				"http://127.0.0.1:8080/screen-rest/sys/toLogin.json",
				FastJsonUtils.toJson(sysUser));

	}
}
