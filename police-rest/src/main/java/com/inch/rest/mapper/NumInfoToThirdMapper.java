package com.inch.rest.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @Date 2022-01-18
 * @Note:
 */
public interface NumInfoToThirdMapper extends BaseMapper{
    List<Map> queryNumInfo(Map<String, Object> info);
}
