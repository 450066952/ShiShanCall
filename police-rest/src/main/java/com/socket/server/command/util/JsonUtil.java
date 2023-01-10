//package com.socket.server.command.util;
//
//import com.google.gson.Gson;
//
//
///**
// * 将JSON数据转换为具体的对象
// * 
// * @author jqp
// * 
// */
//public class JsonUtil {
//
//	public static <T> T convertToObj(String json, Class<T> cla) {
//		
//		T t;
//		
//		Gson gson = new Gson();
//        t = gson.fromJson(json,cla);
//		
//        return t;
//	}
//
////	public static <T> List<T> convertToList(JSONArray jsonArray, Class<T> cla) {
////		List<T> list = new ArrayList<T>();
////		if (jsonArray == null)
////			return list;
////		try {
////			for (int i = 0; i < jsonArray.length(); i++) {
////				JSONObject jsonObject = jsonArray.getJSONObject(i);
////				T t = JsonUtil.convertToObj(jsonObject, cla);
////				list.add(t);
////			}
////
////		} catch (SecurityException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (IllegalArgumentException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (JSONException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		return list;
////	}
//	
//	public static void main(String[] args){
//String str=
//         	
//         	"{'code':'0','addresscode':'成功','info':[{'customerid11111':'1','customerid':'1'}]}";
//
////TVDto dto=new TVDto();
////
////dto=convertToObj("",TVDto.class);
//
//
////
////System.out.println(dto.getAddresscode());
//
////Gson gson = new Gson();
////Type type = new TypeToken<Map<String, String>>(){}.getType();
////Map<String,String> aa=gson.fromJson(str, type);
////
////System.out.println(aa.get("code"));
//
//	}
//
//}