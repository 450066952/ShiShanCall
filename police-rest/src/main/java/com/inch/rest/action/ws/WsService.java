package com.inch.rest.action.ws;

import com.inch.utils.CommonUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @program: TaiCang
 * @description: 111
 * @author: tony.inch
 * @create: 2020-01-15 11:06
 **/
public class WsService {

    private final static Logger log= Logger.getLogger(WsService.class);

    private static String url="";
    static {
        try {
            PropertiesConfiguration jconfig=new PropertiesConfiguration("config.properties");
            url=jconfig.getString("wsurl");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void  GetNumber(Offer offer){

        try {
            final String wsdlURL = "http://50.73.67.151:8090/CallQueue.asmx?wsdl";
            final String contentType = "text/xml;charset=utf8";
            StringBuffer xMLcontent = new StringBuffer("");
            xMLcontent.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                    "  <SOAP-ENV:Header>\n" +
                    "    <AuthenticationSoapHeader xmlns=\"http://www.taiji.com.cn/\">\n" +
                    "      <LoginName>TaiCang</LoginName>\n" +
                    "      <Password>320585</Password>\n" +
                    "      <OrgID>1FEFA99800CB44C78B8738834C8BD103</OrgID>\n" +
                    "    </AuthenticationSoapHeader>\n" +
                    "  </SOAP-ENV:Header>\n" +
                    "  <SOAP-ENV:Body>\n" +
                    "    <GetNumber xmlns=\"http://www.taiji.com.cn/\">\n" +
                    "      <data>\n" +
                    "        <Date>"+offer.getDate()+"</Date>\n" +
                    "        <DateTimer>"+offer.getDateTimer()+"</DateTimer>\n" +
                    "        <QueueNO>"+offer.getQueueNO()+"</QueueNO>\n" +
                    "        <QueueType>"+offer.getQueueType()+"</QueueType>\n" +
                    "        <Number>"+offer.getNumber()+"</Number>\n" +
                    "        <BusinessType/>\n" +
                    "        <CredentialNO/>\n" +
                    "        <CredentialName/>\n" +
                    "        <QueuePeopleNum>"+offer.getQueuePeopleNum()+"</QueuePeopleNum>\n" +
                    "      </data>\n" +
                    "    </GetNumber>\n" +
                    "  </SOAP-ENV:Body>\n" +
                    "</SOAP-ENV:Envelope>");

            String responseXML = doHttpPostByHttpClient(wsdlURL,contentType, xMLcontent.toString());

            log.info("result====="+responseXML);

        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("error====="+e.getMessage());
        }

    }

    public static void CallNumber(Caller caller){

        try {
            final String wsdlURL = "http://50.73.67.151:8090/CallQueue.asmx?wsdl";
            // ???????????????(?????????????????????xml,?????????????????????text/xml;charset=utf8)
            final String contentType = "text/xml;charset=utf8";

            StringBuffer xMLcontent = new StringBuffer("");
            xMLcontent.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:taij=\"http://www.taiji.com.cn/\">\n" +
                    "   <soapenv:Header>\n" +
                    "      <taij:AuthenticationSoapHeader>\n" +
                    "         <taij:LoginName>TaiCang</taij:LoginName>\n" +
                    "         <taij:Password>320585</taij:Password>\n" +
                    "         <taij:OrgID>1FEFA99800CB44C78B8738834C8BD103</taij:OrgID>\n" +
                    "      </taij:AuthenticationSoapHeader>\n" +
                    "   </soapenv:Header>\n" +
                    "   <soapenv:Body>\n" +
                    "      <taij:CallNumber>\n" +
                    "         <taij:data>\n" +
                    "            <taij:DateTimer>"+ CommonUtil.now()+"</taij:DateTimer>\n" +
                    "            <taij:WindowNO>"+caller.getWindowNO()+"</taij:WindowNO>\n" +
                    "            <taij:UserNO>1001</taij:UserNO>\n" +
                    "            <taij:Number>"+caller.getNumber()+"</taij:Number>\n" +
                    "            <taij:QueueNO>"+ caller.getQueueNO()+"</taij:QueueNO>\n" +
                    "         </taij:data>\n" +
                    "         <taij:source>1</taij:source>\n" +
                    "      </taij:CallNumber>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>");

            // ???????????????????????????http??????
            String responseXML = doHttpPostByHttpClient(wsdlURL,contentType, xMLcontent.toString());
            log.info("result====="+responseXML);

        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("error====="+e.getMessage());
        }
    }

    public static void Complete(Caller caller){

        try {
            final String wsdlURL = "http://50.73.67.151:8090/CallQueue.asmx?wsdl";
            final String contentType = "text/xml;charset=utf8";

            StringBuffer xMLcontent = new StringBuffer("");
            xMLcontent.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:taij=\"http://www.taiji.com.cn/\">\n" +
                    "   <soapenv:Header>\n" +
                    "      <taij:AuthenticationSoapHeader>\n" +
                    "         <taij:LoginName>TaiCang</taij:LoginName>\n" +
                    "         <taij:Password>320585</taij:Password>\n" +
                    "         <taij:OrgID>1FEFA99800CB44C78B8738834C8BD103</taij:OrgID>\n" +
                    "      </taij:AuthenticationSoapHeader>\n" +
                    "   </soapenv:Header>\n" +
                    "   <soapenv:Body>\n" +
                    "      <taij:Complete>\n" +
                    "         <taij:data>\n" +
                    "            <taij:DateTimer>"+caller.getDateTimer()+"</taij:DateTimer>\n" +
                    "            <taij:WindowNO>"+caller.getWindowNO()+"</taij:WindowNO>\n" +
                    "            <taij:UserNO>1001</taij:UserNO>\n" +
                    "            <taij:Number>"+caller.getNumber()+"</taij:Number>\n" +
                    "            <taij:QueueNO>"+caller.getQueueNO()+"</taij:QueueNO>\n" +
                    "         </taij:data>\n" +
                    "         <taij:source>1</taij:source>\n" +
                    "      </taij:Complete>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>");

            // ???????????????????????????http??????
            String responseXML = doHttpPostByHttpClient(wsdlURL,contentType, xMLcontent.toString());
            log.info("result====="+responseXML);

        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("error====="+e.getMessage());
        }
    }


    static String doHttpPostByHttpClient(final String wsdlURL, final String contentType, final String content)
            throws IOException {
        // ??????Http?????????(???????????????:???????????????????????????;??????:?????????HttpClient???????????????????????????)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // ??????Post??????
        HttpPost httpPost = new HttpPost(wsdlURL);
        StringEntity entity = new StringEntity(content.toString(), "UTF-8");
        // ???????????????entity???
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", contentType);
        // ????????????
        CloseableHttpResponse response = null;
        String result = null;
        try {
            // ??????????????????(??????)Post??????
            response = httpClient.execute(httpPost);
            // ????????????????????????????????????
            // ??????:???doHttpPostByRestTemplate???????????????????????????HttpEntity
            org.apache.http.HttpEntity responseEntity = response.getEntity();
            System.out.println("??????ContentType???:" + responseEntity.getContentType());
            System.out.println("???????????????:" + response.getStatusLine());
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
                System.out.println("???????????????:" + result);
            }
        } finally {
            // ????????????
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        }
        return result;
    }
}
