package com.inch.rest.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.model.SchoolModel;
import com.inch.rest.mapper.SchoolMapper;

/**
 * 
 * @Title: SchoolService.java
 * @Package com.tony.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月11日
 */
@Service("schoolService")
public class SchoolService<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(SchoolService.class);
	
	/**
	 * 获取年级
	 * @param model
	 * @return
	 */
	public List<T> queryBySchoolList(SchoolModel model){
		return getMapper().queryBySchoolList(model);
	}
	
	/**
	 * 获取班级
	 * @param model
	 * @return
	 */
	public List<T> queryByGradeList(SchoolModel model){
		return getMapper().queryBySchoolList(model);
	}
	
	/**
	 * 获取当前父节点下的子节点
	 * @param model
	 * @return
	 */
	public List<T> getDicList2(SchoolModel model){
		return getMapper().getDicList2(model);
	}
	
	
	
	
	/**
	 * 树形菜单
	 * @param rootid
	 * @param pid
	 * @param id
	 * @param paramList
	 * @return
	 */
	public List<SchoolModel> getDicTreeGrid(String rootid,int pid,int id,String[] ids,Boolean isfirst){
		SchoolModel querydto=new SchoolModel();
		
		if(isfirst){//第一次进来
			querydto.setRootid(rootid);
			
			if(ids.length>0){
				querydto.setPid(-1);
			}else{
				querydto.setPid(0);
			}
		}else{
			querydto.setRootid("");
			querydto.setPid(pid);
		}
		
		
		querydto.setId(id);
		querydto.setIds(ids);
		
		List<SchoolModel> list=(List<SchoolModel>)getDicList2(querydto);
		
		SchoolModel model=new SchoolModel();
		
		if(list.size()>0){
			
			for(int i=0;i<list.size();i++){
				
				model=list.get(i);
				querydto.setId(0);//2016-03-21  修改为 只管顶级节点
				querydto.setRootid("");//2016-02-23  修改为 只管顶级节点
				querydto.setPid(model.getId());//检索条件
				
				int childcnt=getMapper().isChildCnt(querydto);
				
				if(childcnt>0){//存在子节点  继续循环
					
					model.setChildren(getDicTreeGrid(rootid,model.getId(),0,ids,false));
					
					
//					isfirst=false;
					
					
				}else{
					
				}
			}
		}
		return list;
	}
	
	
	public List<SchoolModel> getAllParent(int id){
		return getMapper().getAllParent(id);
	}
	
	public List<SchoolModel> getAllChild(Map<String, Object> map){
		return getMapper().getAllChild(map);
	}

	/**
	 * 插入学校对应关系
	 * @param map
	 * @throws Exception 
	 */
	public int  insertUserSchoolRel(Map<String, Object> map) throws Exception{
		return getMapper().insertUserSchoolRel(map);
	}
	
	@Autowired
    private SchoolMapper<T> mapper;

		
	public SchoolMapper<T> getMapper() {
		return mapper;
	}


	public int  updateSchoolCourseTime(SchoolModel model) throws Exception{
		return getMapper().updateSchoolCourseTime(model);
	}


	public int updateStartNumByOrg(int orgid){
		return getMapper().updateStartNumByOrg(orgid);
	}


}
