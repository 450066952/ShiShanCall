package com.inch.action.weixin;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.inch.annotation.Auth;


/**
 * 
 * 
 * <p>
 * Project:mryl_phone_v2
 * </p>
 * 
 * <p>
 * Package:com.wiimedia.controller
 * </p>
 * 
 * <p>
 * Description:微信分享Controller
 * </p>
 *
 * <p>
 * Company:Wiimedia
 * </p>
 *
 * @Athor:SongJia
 *
 * @Date:2016-7-15 上午09:34:10
 *
 */

@Controller
@RequestMapping("/weixin")
public class WeixinshareController {
//	@Autowired
//	private TicketRepositorySolr ticketRepositorySolr;

	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/getSignature")
	public String getSignature(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		// 获取签名页面链接
		String url = "http://www.incich.com/share/?from=singlemessage&isappinstalled=0";//request.getParameter("url");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 从数据库中获取标签，并检查标签是否过期
		Ticket oldticket =null; //ticketRepositorySolr.getTicketById("20160114wiimediamrylsong1152");
		
		if (oldticket == null) {// 第一次访问，标签不存在。
			executeTicket(response, "1", url, format);
			return null;
		} else {// 标签存在，判断标签是否超时
			String oldAcquiretime = oldticket.getAcquiretime();
			long difference = format.parse(format.format(new Date())).getTime()
					- format.parse(oldAcquiretime).getTime();
			if (difference > 7100000) {// 标签超时,重新到微信服务器请求标签超时时间为7200秒（7200000毫秒）
				executeTicket(response, "2", url, format);
				return null;
			} else {// 标签未超时
				/**
				 * 注意事项
				 * 1.签名用的noncestr和timestamp必须与wx.config中的nonceStr和timestamp相同。
				 * 2.签名用的url必须是调用JS接口页面的完整URL。 3.出于安全考虑，开发者必须在服务器端实现签名的逻辑。
				 * 
				 **** 根据第1点要求 signature 配置的时候很容易出错，需要把生成 Ticket的 noncestr和
				 * timestamp传给客户端***
				 */
				String signature = signature(oldticket.getTicket(),oldticket.getTimestamp(), oldticket.getNoncestr(), url);
				SignatureBean signatureBean = new SignatureBean();
				signatureBean.setNoncestr(oldticket.getNoncestr());
				signatureBean.setSignature(signature);
				signatureBean.setTimestamp(oldticket.getTimestamp());
				signatureBean.setUrl(url);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(new Gson().toJson(signatureBean));
				
				return null;
			}
		}

	}

	/**
	 * 
	 * <p>
	 * Project:mryl_phone_v2
	 * </p>
	 * 
	 * <p>
	 * :mryl_phone_v2
	 * </p>
	 * 
	 * <p>
	 * Description:更新和获取ticket的方法，因为用的solr所以更新和新增是一样的ID无则添加，有责更新
	 * </p>
	 *
	 * <p>
	 * Company:Wiimedia
	 * </p>
	 *
	 * @Athor:SongJia
	 *
	 * @Date:2016-7-15 上午09:45:00
	 *
	 */
	public void executeTicket(HttpServletResponse response, String flag,
			String url, SimpleDateFormat format) throws IOException {

		// 获取签名随即字符串
		GetRandomStr randomStr = new GetRandomStr();
		String noncestr = randomStr.getRandomString(15);
		// 获取签名时间戳
		String timestamp = Long.toString(System.currentTimeMillis());
		
		timestamp=StringUtils.substring(timestamp, 0,10);
		
		// 请求accessToken
//		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=您的APPID&secret=您的密匙";
		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf087cb20b5c398c5&secret=a22e231ad4bfe6afe726a834fa671bdc";

		//		String tokenJson = WeixinUtil.httpRequest(accessTokenUrl, "GET", null);
		
		String tokenJson =//"{\"access_token\":\"LNQ1geew1sDiT4_nbs9AEd8bM-i-nKMgd8MriEnXGE9lPvowqB0WA0khQADbfniiXMvCRmAdSKBS_4jc21Y5BzfC2I_pnZjZJ_hCt-KCpcXIAVcl3MXcno-BwI_T8oJTAOLaADAFTQ\",\"expires_in\":7200}"; //
		WXUtils.httpsRequest(accessTokenUrl, "GET", null);
		
		
		Gson gson = new Gson();
		ShareAccess_Token token = gson.fromJson(tokenJson,
				ShareAccess_Token.class);
		String to = token.getAccess_token();
		// 获取标签
		String urlTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ to + "&type=jsapi";
		String ticketJson = WXUtils.httpsRequest(urlTicket, "GET", null);
		Ticket ticket = gson.fromJson(ticketJson, Ticket.class);
		String t = ticket.getTicket();
		// String uuid = UUID.randomUUID().toString().trim().replaceAll("-",
		// "");
		// 我的Ticket ID是写死的
		String acquiretime = format.format(new Date());
		ticket.setTid("20160114wiimediamrylsong1152");
		ticket.setAcquiretime(acquiretime);
		ticket.setTimestamp(timestamp);
		ticket.setNoncestr(noncestr);
		
		// 因为用的SOLR所以更新和添加的方法是一样的，可以根据自己具体需求进行修改，本文不再贴出代码.
//		if (flag.equals("2")) {
//			ticketRepositorySolr.addTicketToSolr(ticket);
//		} else {
//			ticketRepositorySolr.addTicketToSolr(ticket);
//		}
		
		/**
		 * 注意事项 1.签名用的noncestr和timestamp必须与wx.config中的nonceStr和timestamp相同。
		 * 2.签名用的url必须是调用JS接口页面的完整URL。 3.出于安全考虑，开发者必须在服务器端实现签名的逻辑。
		 * 
		 * 根据第1点要求 signature 配置的时候很容易出错，需要把生成 Ticket的 noncestr和 timestamp传给客户端*
		 */
		String signature = signature(t, timestamp, noncestr, url);
		SignatureBean signatureBean = new SignatureBean();
		signatureBean.setNoncestr(noncestr);
		signatureBean.setSignature(signature);
		signatureBean.setTimestamp(timestamp);
		signatureBean.setUrl(url);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(new Gson().toJson(signatureBean));
	}

	/**
	 * 
	 * <p>
	 * Project:mryl_phone_v2
	 * </p>
	 * 
	 * <p>
	 * :mryl_phone_v2
	 * </p>
	 * 
	 * <p>
	 * Description:根据标签,时间戳,密匙,URL进行签名
	 * </p>
	 *
	 * <p>
	 * Company:Wiimedia
	 * </p>
	 *
	 * @Athor:SongJia
	 *
	 * @Date:2016-7-15 上午09:37:13
	 *
	 */
	private String signature(String jsapi_ticket, String timestamp,
			String noncestr, String url) {
		jsapi_ticket = "jsapi_ticket=" + jsapi_ticket;
		timestamp = "timestamp=" + timestamp;
		noncestr = "noncestr=" + noncestr;
		url = "url=" + url;
		String[] arr = new String[] { jsapi_ticket, timestamp, noncestr, url };
		// 将token、timestamp、nonce,url参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
			if (i != arr.length - 1) {
				content.append("&");
			}
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		return tmpStr;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	class ShareAccess_Token {
		private String access_token;
		private String expires_in;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String accessToken) {
			access_token = accessToken;
		}

		public String getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(String expiresIn) {
			expires_in = expiresIn;
		}

	}
	
	public static void main(String[] args) {
		
		String timestamp = Long.toString(System.currentTimeMillis());
		
		timestamp=StringUtils.substring(timestamp, 0,10);
		
		System.out.println(timestamp);
	}
}