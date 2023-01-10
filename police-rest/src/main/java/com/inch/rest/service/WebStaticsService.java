package com.inch.rest.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.model.QueryModel;
import com.inch.rest.mapper.WebStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("webStaticsService")
public class WebStaticsService<T> extends BaseService<T> {


	public Map<String,Object> queryByWindow(QueryModel model){

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}

		List<Map<String,Object>> dataList=getMapper().queryByWindow((T)model);
		Map<String,Object> jsonMap = new HashMap<>();

		PageInfo page = new PageInfo(dataList);

		jsonMap.put("success", true);
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		return jsonMap;
	}

	public Map<String,Object> queryByWindowDetial(QueryModel model){

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}

		List<Map<String,Object>> dataList=getMapper().queryByWindowDetial((T)model);
		Map<String,Object> jsonMap = new HashMap<>();

		PageInfo page = new PageInfo(dataList);

		jsonMap.put("success", true);
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		return jsonMap;
	}


	public Map<String,Object> queryByPerson(QueryModel model){

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}

		List<Map<String,Object>> dataList=getMapper().queryByPerson((T)model);
		Map<String,Object> jsonMap = new HashMap<>();

		PageInfo page = new PageInfo(dataList);

		jsonMap.put("success", true);
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		return jsonMap;
	}

	public Map<String,Object> queryByPersonDetial(QueryModel model){

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}

		List<Map<String,Object>> dataList=getMapper().queryByPersonDetial((T)model);
		Map<String,Object> jsonMap = new HashMap<>();

		PageInfo page = new PageInfo(dataList);

		jsonMap.put("success", true);
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		return jsonMap;
	}


	public Map<String,Object> queryByType(QueryModel model){

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}

		List<Map<String,Object>> dataList=getMapper().queryByType((T)model);
		Map<String,Object> jsonMap = new HashMap<>();

		PageInfo page = new PageInfo(dataList);

		jsonMap.put("success", true);
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		return jsonMap;
	}

	public Map<String,Object> queryByTypeDetial(QueryModel model){

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}

		List<Map<String,Object>> dataList=getMapper().queryByTypeDetial((T)model);
		Map<String,Object> jsonMap = new HashMap<String,Object>();

		PageInfo page = new PageInfo(dataList);

		jsonMap.put("success", true);
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		return jsonMap;
	}


	@Autowired
    private WebStatisticsMapper<T> mapper;
	
		
	public WebStatisticsMapper<T> getMapper() {
		return mapper;
	}
	
}
