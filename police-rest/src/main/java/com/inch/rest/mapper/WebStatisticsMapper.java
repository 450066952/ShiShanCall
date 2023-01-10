package com.inch.rest.mapper;


import java.util.List;
import java.util.Map;

public interface WebStatisticsMapper<T> extends BaseMapper<T> {

    List<Map<String, Object>> queryByWindow(T t);
    List<Map<String, Object>> queryByWindowDetial(T t);

    List<Map<String, Object>> queryByPerson(T t);
    List<Map<String, Object>> queryByPersonDetial(T t);

    List<Map<String, Object>> queryByType(T t);
    List<Map<String, Object>> queryByTypeDetial(T t);
}
