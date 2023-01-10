package com.inch.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.inch.model.SubClassModel;


public class FastJsonUtils {

	/**
	 * 把JavaBean转换为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {

		return JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 把JavaBean转换为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonWithNUll(Object object) {

		return JSON.toJSONString(object,SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 过滤某些字段
	 * @param object
	 * @param map
	 * @return
	 */
	public static String toJsonFiter(Object object, Map<Class<?>, String[]> map) {
		
		ComplexPropertyPreFilter filter = new ComplexPropertyPreFilter();
        
        filter.setExcludes(map);

		return JSON.toJSONString(object, filter);
	}
	
	
	/**
	 * 转对象
	 * @param jsonStr
	 * @param valueType
	 * @return
	 */
	public static <T> T toObject(String jsonStr, Class<T> valueType) {
		return JSON.parseObject(jsonStr, valueType);  
	}
	
	/**
	 * 转对象
	 * @param jsonStr
	 * @param valueType
	 * @return
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> valueType) {
		return JSON.parseArray(jsonStr, valueType);  
	}
	
	
	public static void main(String[] args) {
		
		
		
		
SubClassModel ssClassModel=new SubClassModel();
		
		
		ssClassModel.setClassname("名称");
		
		String ss=toJson(ssClassModel);
		
	System.out.println(ss);
		
		SubClassModel aaaClassModel=toObject(ss, SubClassModel.class);
		
		System.out.println(11111);
		
		
		List<SubClassModel> sss=new ArrayList<SubClassModel>();
		sss.add(ssClassModel);
		
		String bbb=toJson(sss);
		
		System.out.println(bbb);
		
		
		List<SubClassModel> ddd=JSON.parseArray(bbb, SubClassModel.class);
		
		System.out.println(33333);
		
	}
		
		
//		
//		
//
//		List<SubClassModel> dataList =new ArrayList<SubClassModel>();
//		SubClassModel ssClassModel=new SubClassModel();
//		
//		
//		ssClassModel.setClassname("名称");
//		
//		/////////////////////////////////////
//			List<HomeWorkModel> list=new ArrayList<HomeWorkModel>();
//			
//			HomeWorkModel  ssHomeWorkModel=new HomeWorkModel();
//			
//			ssHomeWorkModel.setNotice("我的通知");
//			
//			list.add(ssHomeWorkModel);
//		////////////////////////////////////////
//		
//		ssClassModel.setList(list);
//		
//		dataList.add(ssClassModel);
//		
//		
//		ComplexPropertyPreFilter filter = new ComplexPropertyPreFilter();
//		               
//		         filter.setExcludes(new HashMap<Class<?>, String[]>() {
//		                   
//		             private static final long serialVersionUID = -8411128674046835592L;
//		                   
//		             {
//		                // put(SubClassModel.class, new String[] { "aid" });
//		               //  put(HomeWorkModel.class, new String[] { "classList","classids" });
////		                 put(C.class, new String[] { "a", "b" });
//		             }
//		         });
//		                   
//		         System.out.println(JSON.toJSONString(dataList, filter));
//		
//		
//	}
}
