package com.inch.rest.service;

import java.util.List;
import java.util.Map;
import com.inch.model.StatisticsModel;
import com.inch.model.StatisticsPersonModel;
import com.inch.rest.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticsService")
public class StatisticsService {

	public List<StatisticsModel> queryByPerson(Map<String,Object> map){
		return  getMapper().queryByPerson(map);
	}

	public List<StatisticsModel> queryByDayTotal(Map<String,Object> map){
		return getMapper().queryByDayTotal(map);
	}



	public List<StatisticsModel> queryByDayType(StatisticsModel model){
		return  getMapper().queryByDayType(model);
	}

	public StatisticsModel queryByToday(Map<String,Object> map){
		return getMapper().queryByToday(map);
	}

	public List<Map<String, Object>> queryWaitPerson(Integer orgid){
		return getMapper().queryWaitPerson(orgid);
	}
	public List<Map<String, Object>> getNotSatisfy(){
		return getMapper().getNotSatisfy();
	}

	public List<Map<String,Object>> getEvaluate(StatisticsPersonModel model){
		return  getMapper().getEvaluate(model);
	}



    public Map<String,Object> getWorkPerson(Map<String,String> map){
		return getMapper().getWorkPerson(map);
	}

	@Autowired
    private StatisticsMapper mapper;
	
		
	public StatisticsMapper getMapper() {
		return mapper;
	}

    public List<Map<String, Object>> queryStatisticsToday(StatisticsModel model) {
		return  getMapper().queryStatisticsToday(model);
    }

	public List<Map<String, Object>> queryStatisticsHistory(StatisticsModel model) {
		return getMapper().queryStatisticsHistory(model);
	}
}
