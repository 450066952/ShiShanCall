package com.inch.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 自定义的返回结果转换工具类
 * @Title: TonyResult.java
 * @Package com.tony.utils
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年6月2日
 */
public class TonyResult {


    // 响应业务状态
    private Boolean success;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static TonyResult build(Boolean success, String msg, Object data) {
        return new TonyResult(success, msg, data);
    }

    public static TonyResult ok(Object data) {
        return new TonyResult(data);
    }

    public static TonyResult ok() {
        return new TonyResult(null);
    }

    public TonyResult() {

    }

    public static TonyResult build(Boolean status, String msg) {
        return new TonyResult(status, msg, null);
    }

    public TonyResult(Boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public TonyResult(Object data) {
        this.success = true;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
    
    
    public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

    public String getMsg() {
        return msg;
    }

	public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为TaotaoResult对象
     * 
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static TonyResult formatToPojo(String jsonData, Class<?> clazz) {
    	
    	 JSONObject jsonObject = JSONObject.parseObject(jsonData) ;
    	
    	 Boolean success=jsonObject.getBoolean("success");
    	 
    	 if(success){
    		 return build(success, jsonObject.getString("msg"), FastJsonUtils.toObject(jsonObject.getString("data"), clazz));
    	 }else{
    		 return build(success, jsonObject.getString("msg"), null);
    	 }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static TonyResult format(String json) {
        try {
        	
        	return FastJsonUtils.toObject(json, TonyResult.class);
//            return MAPPER.readValue(json, TonyResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static TonyResult formatToList(String jsonData, Class<?> clazz) {
    	

	   	 JSONObject jsonObject = JSONObject.parseObject(jsonData) ;
	   	
	   	 Boolean success=jsonObject.getBoolean("success");
	   	 
	   	 if(success){
	   		 return build(success, jsonObject.getString("msg"), FastJsonUtils.toList(jsonObject.getString("data"), clazz));
	   	 }else{
	   		 return build(success, jsonObject.getString("msg"), null);
	   	 }
    	
    }
    
    /**
     * 将json结果集转化为TaotaoResult对象
     * 
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
//    public static Object formatToSchool(String jsonData, Class<?> clazz) {
//    	
//    	JSONObject jsonObject = JSONObject.parseObject(jsonData) ;
//    	
//	   	 Boolean success=jsonObject.getBoolean("success");
//	   	 
//	   	 if(success){
//	   		 return build(success, jsonObject.getString("msg"), FastJsonUtils.toObject(jsonObject.getString("school"), clazz));
//	   	 }else{
//	   		 return build(success, jsonObject.getString("msg"), null);
//	   	 }
//    	/*
//        try {
//            if (clazz == null) {
//                return MAPPER.readValue(jsonData, TonyResult.class);
//            }
//            JsonNode jsonNode = MAPPER.readTree(jsonData);
//            JsonNode data = jsonNode.get("school");
//            Object obj = null;
//            if (clazz != null) {
//                if (data.isObject()) {
//                    obj = MAPPER.readValue(data.traverse(), clazz);
//                } else if (data.isTextual()) {
//                    obj = MAPPER.readValue(data.asText(), clazz);
//                }
//            }
//            return obj;
////            return build(jsonNode.get("success").booleanValue(), jsonNode.get("msg").asText(), obj);
//        } catch (Exception e) {
//            return null;
//        }*/
//    }

}
