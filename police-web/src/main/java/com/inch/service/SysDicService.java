//package com.inch.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.inch.mapper.SysDicMapper;
//import com.inch.model.SysDicModel;
//
//@Service("sysDicService")
//public class SysDicService<T> extends BaseService<T> {
//	private final static Logger log= Logger.getLogger(SysDicService.class);
//
//	/**
//	 * 根据用户id查询父菜单
//	 * @param roleId
//	 * @return
//	 */
//	public List<T> getDicCode(){
//		return getMapper().getDicCode();
//	}
//
//
//
////
////	/**
////	 * 创建一颗树，以json字符串形式返回
////	 * @param list 原始数据列表
////	 * @return 树
////	 */
////	private String createTreeJson(List<Resource> list) {
////		JSONArray rootArray = new JSONArray();
////		for (int i=0; i<list.size(); i++) {
////			Resource resource = list.get(i);
////			if (resource.getParentid() == null) {
////				JSONObject rootObj = createBranch(list, resource);
////				rootArray.add(rootObj);
////			}
////		}
////
////		return rootArray.toString();
////	}
////
////	/**
////	 * 递归创建分支节点Json对象
////	 * @param list 创建树的原始数据
////	 * @param currentNode 当前节点
////	 * @return 当前节点与该节点的子节点json对象
////	 */
////	private JSONObject createBranch(List<Resource> list, Resource currentNode) {
////		/*
////		 * 将javabean对象解析成为JSON对象
////		 */
////		JSONObject currentObj = JSONObject.fromObject(currentNode);
////		JSONArray childArray = new JSONArray();
////		/*
////		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
////		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该
////		 * 节点放入当前节点的子节点列表中
////		 */
////		for (int i=0; i<list.size(); i++) {
////			Resource newNode = list.get(i);
////			if (newNode.getParentid()!=null && newNode.getParentid().compareTo(currentNode.getId()) == 0) {
////				JSONObject childObj = createBranch(list, newNode);
////				childArray.add(childObj);
////			}
////		}
////
////		/*
////		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
////		 */
////		if (!childArray.isEmpty()) {
////			currentObj.put("children", childArray);
////		}
////
////		return currentObj;
////	}
////
////
//
//
//
//
////	List<SysDicModel> list;
//	/**
//	 *
//	 */
//	public List<SysDicModel> getDicTreeGrid(String rootid,int pid,List<SysDicModel> paramList){
//
//		if(paramList==null){
//			paramList=new ArrayList<SysDicModel>();
//		}
//
//		SysDicModel querydto=new SysDicModel();
//		querydto.setRootid(rootid);//
//		querydto.setPid(pid);//检索条件
//
//		List<SysDicModel> list=getMapper().getDicList(querydto);
//
//		SysDicModel model=new SysDicModel();
//
//		if(list.size()>0){
//
//			for(int i=0;i<list.size();i++){
//
//				model=list.get(i);
//
//				querydto.setRootid(rootid);//
//				querydto.setPid(model.getId());//检索条件
//
//				int childcnt=getMapper().isChildCnt(querydto);
//
//				if(childcnt>0){//存在子节点  继续循环
//
//					model.setChildren(getDicTreeGrid(rootid,model.getId(),paramList));
//
//					paramList.add(model);
//
//				}else{
//					paramList.add(model);
//				}
//
//			}
//
//		}
//
//		return list;
//	}
//
//
//
//
//
//
//
//	@Autowired
//    private SysDicMapper<T> mapper;
//
//
//	public SysDicMapper<T> getMapper() {
//		return mapper;
//	}
//
//}
