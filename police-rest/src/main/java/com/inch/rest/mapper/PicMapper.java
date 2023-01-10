package com.inch.rest.mapper;

import java.util.List;

/**
 * @Title: GalleryMapper.java
 * @Package com.tony.mapper
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月17日
 */
public interface PicMapper<T> extends BaseMapper<T> {
	
	public List<String> getChildPhoto(String pguid);
}
