package com.inch.rest.mapper;


import java.util.List;
import java.util.Map;
import com.inch.model.StatisticsModel;
import com.inch.model.StatisticsPersonModel;
import com.inch.model.WindowModel;
import com.sun.xml.internal.xsom.impl.scd.Iterators;

public interface StatisticsMapper {

    List<StatisticsModel> queryByPerson(Map<String,Object> map);
    List<StatisticsModel> queryByDayTotal(Map<String,Object> map);
    List<StatisticsModel> queryByDayType(StatisticsModel model);
    StatisticsModel queryByToday(Map<String,Object> map);
    List<Map<String, Object>> queryWaitPerson(Integer orgid);
    List<Map<String, Object>> getNotSatisfy();
    Map<String,Object> getWorkPerson(Map<String,String> map);
    List<Map<String,Object>> getEvaluate(StatisticsPersonModel model);
}
