package com.inch.rest.utils;

import com.alibaba.fastjson.JSON;
import com.inch.model.SendReqModel;
import com.inch.model.SendResModel;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class MessageUtils {

    /**
     * 用户名
     * */
    private static String apId="shinch";
    /**
     * 密码
    * */
    private static String secretKey="Shiht@2023";
    /**
     * 集团名称
    * */
    private static String ecName = "苏州高新区（虎丘区）狮山街道、横塘街道综合执法中心";
    /**
     * 签名编码
     * */
    private static String sign = "1r0CJ5dV1";
    /**
     * 拓展码 填空
     * */
    private static String addSerial = "";
    /**
     * 请求url
     * */
    public static String url = "http://112.35.10.201:5992/sms/tmpsubmit";

    /**
     * 请求url
     * */
    public static String norUrl = "http://112.35.1.155:1992/sms/norsubmit";

    /**
     * 服务代码
     * */
    private static String code = "1065097110100659";

    /**
     * 多用户发送短信信息
     * @param mobiles 手机号码逗号分隔
     * @param templateId 短信模板id
     * @return 返回1表示成功，0表示失败
     * @throws IOException
     */
    public static int sendMsg(String mobiles,String templateId,String[] paramss,String addSerial) throws IOException {

        SendReqModel sendReq = new SendReqModel();

        sendReq.setApId(apId);
        sendReq.setEcName(ecName);
        sendReq.setSecretKey(secretKey);
        sendReq.setParams(JSON.toJSONString(paramss));
        sendReq.setMobiles(mobiles);
        sendReq.setAddSerial(addSerial);
        sendReq.setSign(sign);
        sendReq.setTemplateId(templateId);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sendReq.getEcName());
        stringBuffer.append(sendReq.getApId());
        stringBuffer.append(sendReq.getSecretKey());
        stringBuffer.append(sendReq.getTemplateId());
        stringBuffer.append(sendReq.getMobiles());
        stringBuffer.append(sendReq.getParams());
        stringBuffer.append(sendReq.getSign());
        stringBuffer.append(sendReq.getAddSerial());
        sendReq.setMac(Md5Util.MD5(stringBuffer.toString()));
        String reqText = JSON.toJSONString(sendReq);

        String encode = Base64.encodeBase64String(reqText.getBytes("UTF-8"));
        String resStr = sendPost(url,encode);

        System.out.println("发送短信结果："+resStr);

        SendResModel sendRes = JSON.parseObject(resStr,SendResModel.class);
        if(sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * 用户发送短信信息
     * @param mobiles 手机号码逗号分隔
     * @param content 发送普通短信内容
     * @return 返回1表示成功，0表示失败
     * @throws IOException
     */
    public static int sendNorMsg(String mobiles,String content,String addSerial) throws IOException {

        SendReqModel sendReq = new SendReqModel();
        System.out.println("addSerial = " + code + addSerial);
        sendReq.setApId(apId);
        sendReq.setEcName(ecName);
        sendReq.setSecretKey(secretKey);
        sendReq.setContent(content);
        sendReq.setMobiles(mobiles);
        sendReq.setAddSerial(addSerial);
        sendReq.setSign(sign);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sendReq.getEcName());
        stringBuffer.append(sendReq.getApId());
        stringBuffer.append(sendReq.getSecretKey());
        stringBuffer.append(sendReq.getMobiles());
        stringBuffer.append(sendReq.getContent());
        stringBuffer.append(sendReq.getSign());
        stringBuffer.append(sendReq.getAddSerial());
        sendReq.setMac(Md5Util.MD5(stringBuffer.toString()));
        String reqText = JSON.toJSONString(sendReq);

        String encode = Base64.encodeBase64String(reqText.getBytes("UTF-8"));
        String resStr = sendPost(norUrl,encode);

        System.out.println("发送短信结果："+resStr);

        SendResModel sendRes = JSON.parseObject(resStr,SendResModel.class);
        if(sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())){
            return 1;
        }else{
             return 0;
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    private static String sendPost(String url, String param) {

        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("contentType","utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(param);
            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
