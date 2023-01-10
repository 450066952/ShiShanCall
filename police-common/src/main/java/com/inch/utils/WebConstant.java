package com.inch.utils;


public class WebConstant {
	
	///////////////////////////////////////////////////////////////////////////////
	
	public static final String REST_BASE_URL="http://localhost:8080/screen-rest/";
	
	public static final String WEBUSER="WEBUSER";
	public static final String SCREENUSER="SCREENUSER";
	public static final String SECRET = "aef2890665d884a3080971b4eca594d7";

	public static final int DISPLAY=1;
	public static final int PAD=2;
	public static final int PC=3;
	public static final int GETCODE=4;

	//-----------------------带城专属
	/**
	 * 小米推送 教师
	 */
	public static String TECH_SECRET_KEY="7Rrx1/wywCCLJ8DIArSfxg==";
	public static String TECH_PACKAGE_NAME="com.inch.school";
	/**
	 * 小米推送 家长
	 */
	public static String PARENT_SECRET_KEY="bwm00YqbrgQmYcU3diqs2w==";
	public static String PARENT_PACKAGE_NAME="com.inch.family";
	
	//---------------------公版
	/**
	 * 小米推送 教师
	 */
	public static String TECH_SECRET_KEY_PUB="51H7+ZeIMOtDGIKj+CDQKg==";
	public static String TECH_PACKAGE_NAME_PUB="com.inch.publicschool";
	/**
	 * 小米推送 家长
	 */
	public static String PARENT_SECRET_KEY_PUB="EueIR5bd/pRzzr2GBFciug==";
	public static String PARENT_PACKAGE_NAME_PUB="com.inch.publicfamily";
	

	/**
	 * 超级管理员常量
	 * @author lu
	 *
	 */
	public static enum SuperAdmin {
		NO(0, "否"), YES(1,"是");
		public int key;
		public String value;
		private SuperAdmin(int key, String value) {
			this.key = key;
			this.value = value;
		}
		public static SuperAdmin get(int key) {
			SuperAdmin[] values = SuperAdmin.values();
			for (SuperAdmin object : values) {
				if (object.key == key) {
					return object;
				}
			}
			return null;
		}
	}

}