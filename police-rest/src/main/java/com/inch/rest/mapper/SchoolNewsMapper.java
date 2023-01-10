package com.inch.rest.mapper;

import java.util.List;
import java.util.Map;

public interface SchoolNewsMapper<T> extends BaseMapper<T> {

    int insertSelUsersBatch(List<Map<String, Object>> list);

    int deleteSelUserByObjId(String userid);

    List<String> getSelUsers(String pguid);
}
