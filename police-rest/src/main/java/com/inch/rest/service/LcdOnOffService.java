package com.inch.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.rest.mapper.LcdOnOffMapper;

/**
 * 
 * @Title: LcdOnOffService.java
 * @Package com.tony.rest.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年6月21日
 */
@Service("lcdOnOffService")
public class LcdOnOffService<T> extends BaseService<T> {

	public int saveLcdOnOff(int schoolid,T t){
		getMapper().delete(schoolid);
		return getMapper().add(t);
	}
	
	@Autowired
    private LcdOnOffMapper<T> mapper;

		
	public LcdOnOffMapper<T> getMapper() {
		return mapper;
	}

}
