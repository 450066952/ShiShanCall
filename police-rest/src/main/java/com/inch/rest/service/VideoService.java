package com.inch.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.rest.mapper.VideoMapper;

/**
 * @Title: VideoService.java
 * @Package com.tony.rest.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年6月23日
 */
@Service("videoService")
public class VideoService<T> extends BaseService<T> {
	
	@Autowired
    private VideoMapper<T> mapper;

		
	public VideoMapper<T> getMapper() {
		return mapper;
	}
	
}
