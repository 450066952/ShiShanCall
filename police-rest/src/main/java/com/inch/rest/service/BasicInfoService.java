package com.inch.rest.service;

import com.inch.rest.mapper.BasicInfoMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @Title: SchoolService.java
 * @Package com.tony.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月11日
 */
@Service("basicInfoService")
public class BasicInfoService<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(BasicInfoService.class);
	
	@Autowired
    private BasicInfoMapper<T> mapper;


	public BasicInfoMapper<T> getMapper() {
		return mapper;
	}
}
