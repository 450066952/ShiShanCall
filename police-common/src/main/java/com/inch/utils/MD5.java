package com.inch.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static final String logTag = "MD5Utils";
	private static final String ALGORITHM = "MD5";

	private MD5() {

	}

	// 默认的消息摘要字符串组合
	private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	// 初始化mMessageDigest对象，MessageDigest类为应用程序提供消息摘要算法的功能，如MD5和SHA算法。
	private static MessageDigest mMessageDigest = null;
	static {
		try {
			mMessageDigest = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			System.err.println(logTag + "初始化失败，MessageDigest不支持MD5Util");
			e.printStackTrace();
		}
	};

	/**
	 * 返回传入的字符串的MD5校验值
	 * 
	 * @param s
	 * @return
	 */
	public static String getMD5String(String s) {
		String s2="";
			
		try {
			s2=getMD5String(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return s2;
	}


	/**
	 * 返回传入的字符数组的MD5校验值
	 * 
	 * @param b
	 * @return
	 */
	public static String getMD5String(byte[] b) {
		mMessageDigest.update(b);
		return toHex(mMessageDigest.digest());
	}

	/**
	 * 传入一个字符串，和一组消息摘要比较，判断它们的消息摘要是否相同
	 * 
	 * @param toDigest
	 *            传入的字符串
	 * @param digest
	 *            传入的已知的消息摘要
	 * @return
	 */
	public static boolean checkTwoDigest(String toDigest, String digest) {
		String s = getMD5String(toDigest);
		return s.equals(digest);
	}

	/**
	 * 返回一个文件的MD5摘要
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		//读取到流里，其他一样
		return null;
	}
	/**
	 * 转换为16进制字符串
	 * @param bytes
	 * @return
	 */
	private static String toHex(byte[] bytes){
		if (bytes == null || bytes.length<=0) {
			return "";
		}
		int index = 0;
		int size = bytes.length;
		char[] c = new char[size*2];
		byte b;
		for (int i = 0; i < size; i++) {
			b = bytes[i];
			c[index++] = hexDigits[b >>> 4 & 0xf];
			c[index++] = hexDigits[b & 0xf];
		}
		return new String(c);
	}

	public static void main(String[] args) {
		
		
		String str = "JYSHIP";
		System.out.println(getMD5String(str));
	}
}

