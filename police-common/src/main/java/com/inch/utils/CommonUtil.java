/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inch.utils;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtil {
	// kssmkweb\\Webjs\\discover\\index.html
	public static final String weblastmodified = "D:\\zhwjServer\\kssmkweb\\Webjs\\discover\\index.html";

	


	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年

	/**
	 * 返回文字描述的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText(String time) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (date == null) {
			return "";
		}

		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "个月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "个小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}

	// 获取当前时间(年月日 时分秒)
	public static String now() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return sdf.format(new java.util.Date()).toString();
	}

	// 获取当前时间(时分秒)
	public static String nowHM() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"HH:mm");
		return sdf.format(new java.util.Date()).toString();
	}
	
	// 获取当前时间(年月日 时分秒)
	public static String nowDay() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
		return sdf.format(new java.util.Date()).toString();
	}

	public static boolean checkTime(String startTime,String endTime){
		boolean flag = false;
		try {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date b = df.parse(startTime);
			Date e = df.parse(endTime);

			// Calendar
			Calendar dateC = Calendar.getInstance();
			dateC.setTime(date);
			Calendar begin = Calendar.getInstance();
			begin.setTime(b);
			Calendar end = Calendar.getInstance();
			end.setTime(e);
			if (dateC.after(begin) && dateC.before(end)) {
//				System.out.println("在区间里");
				flag = true;
			}else{
//				System.out.println("不在区间里");
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean checkTime2(String startTime,String endTime){
		boolean flag = false;
		try {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date b = df.parse(startTime);
			Date e = df.parse(endTime);

			// Calendar
			Calendar dateC = Calendar.getInstance();
			dateC.setTime(date);
			Calendar begin = Calendar.getInstance();
			begin.setTime(b);
			//开始时间减去15分钟
			begin.add(Calendar.MINUTE,-15);
			Calendar end = Calendar.getInstance();
			end.setTime(e);
			if (dateC.after(begin) && dateC.before(end)) {
//				System.out.println("在区间里");
				flag = true;
			}else{
//				System.out.println("不在区间里");
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 判断开始时间和结束时间，是否在上午开始、结束时间或下午开始、结束时间范围内
	 * */
	public static boolean checkTime3(String startTime,String endTime){
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(new Date());
		String amBeginTime = nowDate + " 08:00";
		String amEndTime = nowDate + " 11:30";
		String pmBeginTime = nowDate + " 13:00";
		String pmEndTime = nowDate + " 17:30";

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date start = sdf2.parse(startTime);
			Date end = sdf2.parse(endTime);
			Calendar startTime2 = Calendar.getInstance();
			startTime2.setTime(start);
			Calendar endTime2 = Calendar.getInstance();
			endTime2.setTime(end);

			int index = new GregorianCalendar().get(GregorianCalendar.AM_PM);
			if(index==0){
				//上午
				//上午开始时间
				Calendar amBegin = Calendar.getInstance();
				amBegin.setTime(sdf2.parse(amBeginTime));
				//上午结束时间
				Calendar amEnd = Calendar.getInstance();
				amEnd.setTime(sdf2.parse(amEndTime));

				if (startTime2.after(amBegin) && endTime2.before(amEnd)){
					flag = true;
				}
			}else{
				//下午
				Calendar pmBegin = Calendar.getInstance();
				pmBegin.setTime(sdf2.parse(pmBeginTime));
				//上午结束时间
				Calendar pmEnd = Calendar.getInstance();
				pmEnd.setTime(sdf2.parse(pmEndTime));

				if (startTime2.after(pmBegin) && endTime2.before(pmEnd)){
					flag = true;
				}
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return flag;
	}


	public static Boolean checkIsWeeked() {
		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		} else{
			return false;
		}
	}

	// encord转码
	public static String strEncode(String str) {
		String encord = "";
		try {
			encord = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			encord = str;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encord;
	}

	// //decord转码
	// public static String strDecode(String str) {
	//
	// if(str==null){return "";}
	//
	// String encord="";
	// try {
	// encord=URLDecoder.decode(str,"UTF-8");
	//
	// encord=HtmlUtil.toHtml(encord);
	//
	//
	// } catch (UnsupportedEncodingException e) {
	// encord=str;
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return encord;
	// }
	//

	public static Boolean checkIsEnd(String endtime) {

		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date d1 = format.parse(endtime);

			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			Calendar c2 = Calendar.getInstance();

			if (c1.after(c2)) {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获取指定时间对应的毫秒数
	 * 
	 * @param time
	 *            "HH:mm:ss"
	 * @return
	 */
	public static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " "
					+ time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 用来存验证码的
	 */
	public static final Map<String, Long> YZM_MAP = new ConcurrentHashMap<String, Long>();
	public static final String FORMATTIME = "1900-01-01 00:00:00.0";

	// 转换成汉字内码
	public static String toChinese(String s) {
		if (s != null) {
			return s;
		}
		return "";
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// 转换成汉字内码
	public static String toChinese1(String s) {
		if (s != null) {
			try {
				s = s.trim();
				s = s.replaceAll("'", "''");
				s = new String(s.getBytes("iso8859_1"), "UTF-8");
				return s;
			} catch (Exception e) {
			}
		}
		return "";
	}

	/**
	 * 替换特殊字符
	 * 
	 * @param s
	 * @return
	 */
	public static String htmlEncode(String s) {
		if ((s != null) && (!"".equals(s))) {
			StringBuilder stringbuffer = new StringBuilder();
			int j = s.length();
			for (int i = 0; i < j; i++) {
				char c = s.charAt(i);
				switch (c) {
				case 60:
					stringbuffer.append("&lt;");
					break;
				case 62:
					stringbuffer.append("&gt;");
					break;

				case 34:
					stringbuffer.append("&quot;");
					break;

				default:
					stringbuffer.append(c);
					break;
				}
			}
			return stringbuffer.toString();
		} else {
			return "";
		}
	}

	/**
	 * 替换单引号
	 * 
	 * @param s
	 * @return
	 */
	public static String repalceStr(String s) {
		if (s != null)
			return s.replaceAll("'", "''");
		else
			return "";
	}

	/**
	 * 排序
	 * 
	 * @param field
	 * @param orderBy
	 * @return
	 */
	public static BeanComparator sortList(String field, String orderBy) {
		Comparator mycmp = ComparableComparator.getInstance();
		mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
		if (StringUtils.isNotEmpty(orderBy) && orderBy.equalsIgnoreCase("desc")) {
			mycmp = ComparatorUtils.reversedComparator(mycmp); // desc
		}
		return new BeanComparator(field, mycmp);
	}
	


	/**
	 * str转化为数字
	 * 
	 * @param str
	 * @return
	 */
	public static int parseInt(String str) {
		// int i = 0;
		// if (str == null || str.equals("")) {
		//
		// } else {
		// i = NumberUtils.stringToInt(str);
		// }
		return NumberUtils.stringToInt(str);
	}

	/**
	 * str转化为数字
	 * 
	 * @param str
	 * @return
	 */
	public static int parseInt1(String str) {
		int i = -1;
		if (str == null || str.equals("")) {

		} else {
			i = Integer.parseInt(str);
		}
		return i;
	}

	/**
	 * 数字检查
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {
		if (s == null || s == "") {
			return false;
		}

		char ch0 = '0';
		char ch9 = '9';
		char[] ss = s.toCharArray();
		for (int i = 0; i < ss.length; i++) {
			if (ss[i] < ch0 || ss[i] > ch9) {
				if (ss[i] != '.' && ss[i] != '%') {
					return false;
				} else {
				}
			}
		}
		return true;
	}

	/**
	 * 过滤单引号
	 */
	public static String replaceInvertedComma(String str) {
		return (str.trim().replace("'", "").replace(";", "").replace("--", ""));
	}

	public static String htmlencode(String str) {
		if (str == null || str == "") {
			return "";
		}
		str = str.replace(">", "&gt;");
		str = str.replace("<", "&lt;");
		str = str.replace(" ", "&nbsp;");
		str = str.replace(" ", " &nbsp;");
		str = str.replace("\"", "&quot;");
		str = str.replace("\'", "&#39;");
		str = str.replace("\n", " <br/> "); // htmlencode最少没这个
		return str;
	}

	// 输出数据时替换回车和空格
	public static String getHtmlString(String strSrc) {
		return strSrc.replace("\r\n", "<br/>").replace(" ", "&nbsp;");
	}

	/**
	 * 过滤待转换成json格式字符中的特殊符号
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToJson(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 格式化时间 取出 yyyy-mm-dd
	 * 
	 * @param times
	 * @return
	 */
	public static String formatTime(String times) {
		String result = "";
		if (times != null && !times.equals("")) {
			if (times.indexOf(".") > -1) {
				System.out.println("times:" + times);

				String[] b = times.split("\\.");
				result = b[0];
			} else {
				result = times;
			}

		}
		return result;
	}

	public static String getWebLastModifiedTime(String filepath) {
		File f = new File(filepath);
		String s = "-1";
		if (f.exists()) {
			Calendar cal = Calendar.getInstance();
			long time = f.lastModified();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			cal.setTimeInMillis(time);
			s = formatter.format(cal.getTime());
			System.out.println("修改时间 " + s);
		}
		return s;
	}

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890"
				: "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	/**
	 * 用于直接请求医院JSON ,超时时间10秒
	 * 
	 * @param urlStr
	 * @param content
	 * @return
	 */
	public static String getResult(String urlStr, String content, int miseconds) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(miseconds);// 毫秒 10秒时间
			connection.setReadTimeout(miseconds);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.write(content.getBytes("utf-8"));
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	public static String getOrderNo() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmssSSS");
		return sdf.format(new java.util.Date()).toString();
	}

	public static PropertiesConfiguration getConfig(String name) {
		PropertiesConfiguration jconfig = null;
		try {
			jconfig = new PropertiesConfiguration(name);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jconfig;
	}
	
	public static String arrayToString(String[] args){
		String str="";
		if(args==null||args.length<1){
			return str;
		}else{
			for (int i = 0; i < args.length; i++) {
				
				str+=args[i]+",";
				
			}
		}
		
		if(str.length()>0){
			str=StringUtils.substringBeforeLast(str, ",");
		}
		
		return str ;
	}
	
	/**
	 * 获取min和max之间的随机数
	 * @param max
	 * @param min
	 * @return
	 */
	public static String getRandom(int min,int max){
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        
        return s+"";
	}
	
	public static String compare(String classid,String oldclassid){
		
		String[] nowClass=StringUtils.trimToEmpty(classid).split(",");
		String[] oldClass=StringUtils.trimToEmpty(oldclassid).split(",");

		String delStr="";
		Boolean isIN=false;
		for(int i=0;i<oldClass.length;i++){
			isIN=false;
			for(String s: nowClass){
				if(s.equals(oldClass[i])){
					isIN=true;
					break;
				}
			}
			
			if(!isIN){
				delStr+=oldClass[i]+",";
			}
		}
		
		return StringUtils.substringBeforeLast(delStr, ",");
	}
	
	public static String compareAdd(String classid,String oldclassid){
		
		String[] nowClass=oldclassid.split(",");
		String[] oldClass=classid.split(",");

		String delStr="";
		Boolean isIN=false;
		for(int i=0;i<oldClass.length;i++){
			isIN=false;
			for(String s: nowClass){
				if(s.equals(oldClass[i])){
					isIN=true;
					break;
				}
			}
			
			if(!isIN){
				delStr+=oldClass[i]+",";
			}
		}
		
		return StringUtils.substringBeforeLast(delStr, ",");
	}
	
	public static String getFieldValue(Object m,String itemname){
		
		try {
		       Class userCla = (Class) m.getClass(); 
		       
		       Field f = userCla.getDeclaredField(itemname);
		       f.setAccessible(true); //设置些属性是可以访问的  
		       Object val = f.get(m);//得到此属性的值     
		       
		       return String.valueOf(val);
		} catch (Exception e) {

			return "";
		}
		
	}
	
	
	public static String nowM() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		return sdf.format(new java.util.Date()).toString();
	}

	//1 周日 2 周一 3周二 4 周三
	public static int getDayofweek(){
		Date today = new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(today);
		int weekday=c.get(Calendar.DAY_OF_WEEK);
		return  weekday-1;
	}
	
	
	public static void main(String[] args) {
		String aaString="1,2,3";
		for(int i=0;i<5;i++){
			if(i==3){
				break;
			}
			
		}
		
		String old="1,3,5";
//		
//		System.out.println(ss.toString());
		
		System.out.println(compare(aaString,old));
		System.out.println(nowHM());
	}
	

}
