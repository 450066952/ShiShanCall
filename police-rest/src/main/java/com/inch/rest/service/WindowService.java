package com.inch.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageInfo;
import com.inch.model.WindowModel;
import com.inch.rest.mapper.WindowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("windowService")
public class WindowService<T> extends BaseService<T> {


	public Map<String,Object> queryByList2(WindowModel model){

		List<T> dataList=getMapper().queryByList2(model);
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
    private WindowMapper<T> mapper;
	
		
	public WindowMapper<T> getMapper() {
		return mapper;
	}
	
}
