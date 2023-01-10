package com.inch.rest.service;

import com.inch.rest.mapper.NumInfoToThirdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @Date 2022-01-18
 * @Note:
 */
@Service("NumInfoToThirdService")
public class NumInfoToThirdService extends BaseService {

    @Autowired
    private NumInfoToThirdMapper mapper;

    @Override
    public NumInfoToThirdMapper getMapper() {
        return mapper;
    }

    public List<Map> queryNumInfo(int orgid, String startTime, String endTime) {
        Map<String,Object> info = new HashMap<>();
        info.put("orgid", orgid);
        info.put("startTime", startTime);
        info.put("endTime", endTime);
        return getMapper().queryNumInfo(info);
    }
}
