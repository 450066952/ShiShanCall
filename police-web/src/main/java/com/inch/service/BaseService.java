package com.inch.service;

import java.util.List;

import com.inch.mapper.BaseMapper;
import com.inch.model.BaseModel;

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
	
	public void delete(Object... ids) throws Exception{
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			getMapper().delete(id);
		}
	}
	
	public int queryByCount(BaseModel model)throws Exception{
		return getMapper().queryByCount(model);
	}
	
	public List<T> queryByList(BaseModel model) throws Exception{
//		Integer rowCount = queryByCount(model);
//		model.getPager().setRowCount(rowCount);
		return getMapper().queryByList(model);
	}
	
	public List<T> queryByListCalendar(Object model) throws Exception{
		return getMapper().queryByListCalendar(model);
	}
	
	

	public T queryById(Object id) throws Exception{
		return getMapper().queryById(id);
	}
}
