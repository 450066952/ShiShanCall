package com.inch.rest.mapper;


import java.util.List;
import java.util.Map;

public interface MapStatisticsMapper {

    List<Map> queryByORG(Map<String, Object> map);
}
