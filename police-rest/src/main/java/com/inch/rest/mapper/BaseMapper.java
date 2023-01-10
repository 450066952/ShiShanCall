package com.inch.rest.mapper;

import java.util.List;

import com.inch.model.BaseModel;

public interface BaseMapper<T> {

	
	public int add(T t);
	
	
	public int update(T t);
	
	
	public void updateBySelective(T t); 	
	
	public int delete(Object id);
	

	public int queryByCount(BaseModel model);
	
	public List<T> queryByList(BaseModel model);
	
	public List<T> queryByListCalendar(Object model);
	
	public T queryById(Object id);
	//批量删除
	public int deleteBactch(Object[] ids);
}
