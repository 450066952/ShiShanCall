/**
 * 
 */
package com.socket.server.socket.pub;

import java.io.File;
import java.io.FilenameFilter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.socket.server.command.dto.OkDto;
import com.socket.server.command.dto.PcLoginModel;
import com.socket.server.command.dto.ReauestLoginDto;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * FileName:CharUtil.java
 * 
 * @author Tony
 */
public class CharacterHelper {
	private static final Logger logger = Logger.getLogger(CharacterHelper.class);
	/**  !符号*/
	public static final String SIGH="!";
	/**  #符号 */
	public static final String WELL="#";
	/**  @符号*/
	public static final String AT="@";
	/**  '符号*/
	public static final String POINT="'";
	/**  ,符号*/
	public static final String COMMA=",";
	/**  日期格式*/
	public static final String datePattern = "yyyy-MM-dd HH:mm:ss";
	/**  (符号*/
	public static final String LEFT_BRACKET="(";
	/**  )符号*/
	public static final String RIGHT_BRACKET=")";
	/**  |符号*/
	public static final String ERECT_LINE="|";
	
	/**系统消息用户名*/
	public static final String SYSMSG="sysmsg";
	
	/** set timeout time */
	public static final long DEFAULT_CONNECTION_TIMEOUT=0xf;//0x1e;
	/*pad和TV */
	public static final Map<String,ReauestLoginDto> ALL_LOGIN_USER = new ConcurrentHashMap<>();
	/*PC登录*/
	public static final Map<String,PcLoginModel> ALL_PC_USER = new ConcurrentHashMap<>();
	/*大数据pad登录*/
	public static final Map<String,ReauestLoginDto> ALL_BIG_DATA_USER = new ConcurrentHashMap<>();

	public static final String HISTORY="HISTORY";

	public static String getCurrentTime() {
		   	return DateFormatUtils.format(new Date(), "yyyy年MM月dd日");
	}
	public static String getCurrentLongTime() {
	   	return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
}
	public static int checkListSize(List<?> list){
		return (null!=list)?list.size():0;
	}
	// 整数到字节数组的转换
	public static byte[] intToByte(int number) {
		int temp = number;
		byte[] b = new byte[4];
		int bleng=b.length;
		for (int i = bleng - 1; i > -1; i--) {
			b[i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	// 字节数组到整数的转换
	public static int byteToInt(byte[] b) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[i] >= 0)
				s = s + b[i];
			else
				s = s + 256 + b[i];
			s = s * 256;
		}
		if (b[3] >= 0) // 最后一个之所以不乘，是因为可能会溢出
			s = s + b[3];
		else
			s = s + 256 + b[3];
		return s;
	}

	// 整数到字节数组转换
	public static byte[] int2bytes2(int n) {
		byte[] ab = new byte[4];
		ab[0] = (byte) (0xff & n);
		ab[1] = (byte) ((0xff00 & n) >> 8);
		ab[2] = (byte) ((0xff0000 & n) >> 16);
		ab[3] = (byte) ((0xff000000 & n) >> 24);
		return ab;
	}

	/**
	 * 整数到字节数组转换
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] int2bytes(int num) {
		byte[] b = new byte[4];
//		int mask = 0xff;
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - i * 8));
		}
		return b;
	}

	// 字节到整数的转换
	public static int byte2int(byte b) {
		int s = 0;
		s = b;
		return s;
	}

	// 字节数组到整数的转换
	public static int bytes2int(byte b[]) {
		int s = 0;
		s = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8
				| (b[3] & 0xff);
		return s;
	}

	// 字节转换到字符
	public static char byte2char(byte b) {
		return (char) b;
	}

	private final static byte[] hex = "0123456789ABCDEF".getBytes();

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	// 从字节数组到十六进制字符串转换
	public static String Bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		int bleng=b.length;
		for (int i = 0; i < bleng; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	// 从十六进制字符串到字节数组转换
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		int bleng=b.length;
		for (int i = 0; i < bleng; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}
	/**从LoginName中提取出loginid,e.g:(321025656) return 321025656,substring "(" and ")"
	 * @param loginName
	 * @return
	 */
	public static String getLoginID(String loginName){
		if(StringUtils.isNotBlank(loginName)){
			return StringUtils.substringBetween(loginName, CharacterHelper.LEFT_BRACKET, CharacterHelper.RIGHT_BRACKET);
		}
		return "";
	}
	/**把STRING转换成INT
	 * @param str
	 * @return 如果转换失败,返回-1
	 */
	public static int String2Int(String str){
		if(StringUtils.isNotBlank(str)&&StringUtils.isNumeric(str)){
			return Integer.valueOf(str).intValue();
		}
		return 0;
	}
	public static List<String > split_Key(String key){
		List<String > keys= new ArrayList<String>();
		if(!"".equals(StringUtils.trimToEmpty(key))){
			if(key.contains("_")){
			 String[] strs=key.split("_");
			 for(int i =0;i<strs.length;i++){
				 keys.add(strs[i]);
			 }
			}
		}
		return keys;
	}
	
	
	/**返回此目录下面的所有XLF文件
	 * @param path
	 * @return
	 */
	public static String[] getTemplateFolderXLF(String path){
		if(StringUtils.isNotBlank(path)){
			path=path.replaceAll("file:/", "");
			if(!SystemUtils.IS_OS_WINDOWS){
				path="/"+path;
			}
			logger.debug("XLF文件地址为:"+path);
			File file= new File(path);
			String[] xlfarry=file.list(new XlfFilter());
			return xlfarry;
		}
		return null;
	}
	/**只返回长度为2的一个数组,每个数组里面字符串长度小于256,only define arry leng set two
	 * @param path
	 * @return
	 */
	public static String[] getTemplateFolderXLFWithSplitNumber(String path){
		String[] arry=getTemplateFolderXLF(path);
		String rs="";
		String[] rsarry= new String[2];
		if(null!=arry){
			int arrleng=arry.length;
			for(int i =0;i<arrleng;i++){
				rs+=CharacterHelper.ERECT_LINE+(arry[i]);
			}
			rs=rs+CharacterHelper.ERECT_LINE;
			String temp="";
			int len=rs.length();
			if(len>255){
				int d=0;
				for(int i=0;i<len;){
					d++;
					temp=StringUtils.substring(rs, i,i+255);
					if(d==1){
						rsarry[0]=temp;
					}else if(d==2){
						rsarry[1]=temp;	
					}
					i+=temp.length();
				}
			}else{
				rsarry[0]=rs;
			}
		}
		return rsarry;
	}

	public static String getMD5(String source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
										// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
											// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
											// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
															// >>>
															// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	//获取当前时间(年月日 时分秒)
	public static Date now() {
		Calendar c1 = Calendar.getInstance();
		return c1.getTime();
	}
	

}
/**
 */
class XlfFilter  implements FilenameFilter{    
	  public boolean isXlf(String file) {    
	    if (file.toLowerCase().endsWith(".xlf")){    
	      return true;    
	     }else{    
	      return false;    
	     }    
	   }
	@Override
	public boolean accept(File dir, String name) {
		    return isXlf(name);    
	}   
	
	
	
}
