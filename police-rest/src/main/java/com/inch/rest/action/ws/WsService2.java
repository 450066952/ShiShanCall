//package com.inch.rest.action.ws;
//
//import org.apache.axis.client.Call;
//import org.apache.axis.client.Service;
//import org.apache.axis.encoding.ser.BeanDeserializerFactory;
//import org.apache.axis.encoding.ser.BeanSerializerFactory;
//import org.apache.axis.message.SOAPHeaderElement;
//import org.apache.commons.configuration.PropertiesConfiguration;
//import org.apache.log4j.Logger;
//
//import javax.xml.namespace.QName;
//import javax.xml.rpc.ParameterMode;
//import javax.xml.rpc.encoding.XMLType;
//import javax.xml.soap.SOAPException;
//
///**
// * @program: TaiCang
// * @description: 111
// * @author: tony.inch
// * @create: 2020-01-15 11:06
// **/
//public class WsService2 {
//
//    private final static Logger log= Logger.getLogger(WsService2.class);
//
//    private static String url="";
//    static {
//        try {
//            PropertiesConfiguration jconfig=new PropertiesConfiguration("config.properties");
//            url=jconfig.getString("wsurl");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void  GetNumber(Offer offer){
//
//        try {
//            String endpoint =url;
//            String targetNamespace="http://www.taiji.com.cn/";
//
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(endpoint);
//            call.setOperationName(new QName(targetNamespace, "GetNumber"));
//            call.setUseSOAPAction(true);
//            call.setSOAPActionURI("http://www.taiji.com.cn/GetNumber");
//
//
//            SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(targetNamespace, "AuthenticationSoapHeader");
////            soapHeaderElement.setNamespaceURI("http://www.taiji.com.cn/");
//            try{
//                soapHeaderElement.addChildElement("LoginName").setValue("test");
//                soapHeaderElement.addChildElement("Password").setValue("123");
//                soapHeaderElement.addChildElement("OrgID").setValue("1FEFA99800CB44C78B8738834C8BD103");
//                call.addHeader(soapHeaderElement);
//            }catch (SOAPException e) {
//                e.printStackTrace();
//            }
//
//
//            ///////////////////////////////////////////////////////////////////////////////////////////
//            // 注册序列化/反序列化器
//            QName qn_request = new QName(targetNamespace, "data");
//            call.registerTypeMapping(Offer.class, qn_request,
//                    new BeanSerializerFactory(Offer.class, qn_request),
//                    new BeanDeserializerFactory(Offer.class,qn_request));
//
//            // 设置参数名
//            call.addParameter("data", qn_request, Offer.class, ParameterMode.IN);
//            ///////////////////////////////////////////////////////////////////////////////////
//
//            call.addParameter(new QName(targetNamespace,"source"), XMLType.XSD_STRING, ParameterMode.IN);
//            call.setReturnType(XMLType.XSD_STRING);
//
//
//            String result = (String)call.invoke(new Object[]{offer, "1"});
//
//            log.info("result====="+result);
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            log.error("error====="+e.getMessage());
//        }
//
//    }
//
//    public static void CallNumber(Caller caller){
//
//        try {
//            String endpoint = url;
//            String targetNamespace="http://www.taiji.com.cn/";
//
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(endpoint);
//            call.setOperationName(new QName(targetNamespace, "CallNumber"));
//            call.setUseSOAPAction(true);
//            call.setSOAPActionURI("http://www.taiji.com.cn/CallNumber");
//
//
//            SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(targetNamespace, "AuthenticationSoapHeader");
////            soapHeaderElement.setNamespaceURI("http://www.taiji.com.cn/");
//            try{
//                soapHeaderElement.addChildElement("LoginName").setValue("test");
//                soapHeaderElement.addChildElement("Password").setValue("123");
//                soapHeaderElement.addChildElement("OrgID").setValue("1FEFA99800CB44C78B8738834C8BD103");
//                call.addHeader(soapHeaderElement);
//            }catch (SOAPException e) {
//                e.printStackTrace();
//            }
//
//            ///////////////////////////////////////////////////////////////////////////////////////////
//            // 注册序列化/反序列化器
//            QName qn_request = new QName(targetNamespace, "data");
//            call.registerTypeMapping(Caller.class, qn_request,
//                    new BeanSerializerFactory(Caller.class, qn_request),
//                    new BeanDeserializerFactory(Caller.class,qn_request));
//
//            // 设置参数名
//            call.addParameter("data", qn_request, Caller.class, ParameterMode.IN);
//            ///////////////////////////////////////////////////////////////////////////////////
//
//            call.addParameter(new QName(targetNamespace,"source"), XMLType.XSD_STRING, ParameterMode.IN);
//            call.setReturnType(XMLType.XSD_STRING);
//
//            String result = (String)call.invoke(new Object[]{caller, "1"});
//            log.info("result====="+result);
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            log.error("error====="+e.getMessage());
//        }
//    }
//
//    public static void Complete(Caller caller){
//
//        try {
//            String endpoint = url;
//            String targetNamespace="http://www.taiji.com.cn/";
//
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(endpoint);
//            call.setOperationName(new QName(targetNamespace, "Complete"));
//            call.setUseSOAPAction(true);
//            call.setSOAPActionURI("http://www.taiji.com.cn/Complete");
//
//
//            SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(targetNamespace, "AuthenticationSoapHeader");
////            soapHeaderElement.setNamespaceURI("http://www.taiji.com.cn/");
//            try{
//                soapHeaderElement.addChildElement("LoginName").setValue("test");
//                soapHeaderElement.addChildElement("Password").setValue("123");
//                soapHeaderElement.addChildElement("OrgID").setValue("1FEFA99800CB44C78B8738834C8BD103");
//                call.addHeader(soapHeaderElement);
//            }catch (SOAPException e) {
//                e.printStackTrace();
//            }
//
//            ///////////////////////////////////////////////////////////////////////////////////////////
//            // 注册序列化/反序列化器
//            QName qn_request = new QName(targetNamespace, "data");
//            call.registerTypeMapping(Caller.class, qn_request,
//                    new BeanSerializerFactory(Caller.class, qn_request),
//                    new BeanDeserializerFactory(Caller.class,qn_request));
//
//            // 设置参数名
//            call.addParameter("data", qn_request, Caller.class, ParameterMode.IN);
//            ///////////////////////////////////////////////////////////////////////////////////
//
//            call.addParameter(new QName(targetNamespace,"source"), XMLType.XSD_STRING, ParameterMode.IN);
//            call.setReturnType(XMLType.XSD_STRING);
//
//            String result = (String)call.invoke(new Object[]{caller, "1"});
//            log.info("result====="+result);
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            log.error("error====="+e.getMessage());
//        }
//    }
//}
