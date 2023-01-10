package com.inch.rest.service;

import com.inch.rest.mapper.MapStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("mapStatisticsService")
public class MapStatisticsService {

	public List<Map> queryByORG(Map<String,Object> map){
		return  getMapper().queryByORG(map);
	}


	@Autowired
    private MapStatisticsMapper mapper;
	
		
	public MapStatisticsMapper getMapper() {
		return mapper;
	}
	
}
