package com.inch.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inch.model.LcdModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.rest.mapper.LcdMapper;

@Service("lcdService")
public class LcdService<T> extends BaseService<T> {

	public int regestClass(T t){
		
		return getMapper().regestClass(t);
	}

	public int addLcdWindow(String lcdguid,String windowids){

		Map<String,Object> map=new HashMap<>();
		map.put("lcdguid",lcdguid);
		map.put("windowids", StringUtils.trimToEmpty(windowids).split(","));
		return  getMapper().addLcdWindow(map);
	}

	@Autowired
    private LcdMapper<T> mapper;

		
	public LcdMapper<T> getMapper() {
		return mapper;
	}

    public List<LcdModel> queryByListByModel(int model) {
		return getMapper().queryByListByModel(model);
    }
}
