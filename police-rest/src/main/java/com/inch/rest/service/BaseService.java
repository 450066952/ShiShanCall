package com.inch.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.model.BaseModel;
import com.inch.rest.mapper.BaseMapper;

public abstract class BaseService<T>{
	
	public abstract BaseMapper<T> getMapper();

	
	public int add(T t)  throws Exception{
		return getMapper().add(t);
	}
	
	public int update(T t)  throws Exception{
		return getMapper().update(t);
	}
	
	
	public void updateBySelective(T t){
		getMapper().updateBySelective(t);
	}
	
	public int delete(Object[] ids) throws Exception{
		int ret=0;
		if(ids == null || ids.length < 1){
			return 0;
		}
		for(Object id : ids ){
			ret+=getMapper().delete(id);
		}
		
		return ret;
	}
	
	public int deleteBactch(Object[] ids) throws Exception{
			
			if(ids == null || ids.length < 1){
				return 0;
			}
			
			return getMapper().deleteBactch(ids);
	}
	
	public int queryByCount(BaseModel model)throws Exception{
		return getMapper().queryByCount(model);
	}
	
	public Map<String,Object> queryByList(BaseModel model) throws Exception{

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}
			
		List<T> dataList=getMapper().queryByList(model);
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		
		PageInfo page = new PageInfo(dataList);
		
		jsonMap.put("success", true);
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		
		
		return jsonMap;
	}
	
	public List<T> queryByListT(BaseModel model) throws Exception{

		return getMapper().queryByList(model);
	}
	
	public List<T> queryByListCalendar(Object model) throws Exception{
		return getMapper().queryByListCalendar(model);
	}

	public T queryById(Object id){
		return getMapper().queryById(id);
	}
}
