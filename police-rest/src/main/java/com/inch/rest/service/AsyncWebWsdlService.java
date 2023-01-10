package com.inch.rest.service;

import com.inch.model.NumInfoModel;
import org.apache.axis.client.Call;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.*;
import javax.xml.ws.Dispatch;

@Service("AsyncWebWsdlService")
public class AsyncWebWsdlService {

    private final static String loginName = "TaiCang";//用户名
    private final static String password = "320585";//密码
    private final static String orgID = "1FEFA99800CB44C78B8738834C8BD103";//单位ID
    private final static String url = "http://124.205.50.45:8090";//发送url


    public void toWebService(NumInfoModel retmodel){

        SOAPMessage message = null;
        try {
            message = MessageFactory.newInstance().createMessage();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            SOAPBody body = envelope.getBody();

            //3.2、处理header信息
            SOAPHeader header = envelope.getHeader();
            if(header==null){
                header = envelope.addHeader();
            }
            QName hname = new QName(url,"loginName","TaiCang");
            header.addHeaderElement(hname).setValue("administrator");

        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }
}
