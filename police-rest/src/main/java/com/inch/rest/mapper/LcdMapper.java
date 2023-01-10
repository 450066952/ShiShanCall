package com.inch.rest.mapper;


import com.inch.model.LcdModel;

import java.util.List;
import java.util.Map;

/**
 * SysUser Mapper
 * @author Administrator
 *
 */
public interface LcdMapper<T> extends BaseMapper<T> {
	
	 int regestClass(T t);

	 int addLcdWindow(Map<String,Object> map);

    List<LcdModel> queryByListByModel(int model);
}

