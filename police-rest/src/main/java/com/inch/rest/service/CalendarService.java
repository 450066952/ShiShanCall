package com.inch.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.rest.mapper.CalendarMapper;

/**
 * 
 * @Title: LcdService.java
 * @Package com.tony.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月11日
 */
@Service("calendarService")
public class CalendarService<T> extends BaseService<T> {

	@Autowired
    private CalendarMapper<T> mapper;

		
	public CalendarMapper<T> getMapper() {
		return mapper;
	}

}
