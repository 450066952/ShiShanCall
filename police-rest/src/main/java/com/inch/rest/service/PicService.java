package com.inch.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.rest.mapper.PicMapper;

/**
 * @Title: GalleryService.java
 * @Package com.tony.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月17日
 */
@Service("picService")
public class PicService<T> extends BaseService<T> {

	
	public List<String> getChildPhoto(String pguids){
		
		return getMapper().getChildPhoto(pguids);
	}
	
	@Autowired
    private PicMapper<T> mapper;

		
	public PicMapper<T> getMapper() {
		return mapper;
	}
	
}
