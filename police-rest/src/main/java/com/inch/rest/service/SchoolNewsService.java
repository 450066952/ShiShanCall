package com.inch.rest.service;

import com.inch.rest.mapper.SchoolNewsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("schoolNewsService")
public class SchoolNewsService<T> extends BaseService<T> {

	public void addSelUsers(String userId,List<Map<String,Object>> list) throws Exception{
		if(StringUtils.isBlank(userId)){
			return;
		}
		deleteSelUserByObjId(userId);
		if(list!=null&&list.size()>0){
			getMapper().insertSelUsersBatch(list);
		}
	}

	public int deleteSelUserByObjId(String userid){
		return getMapper().deleteSelUserByObjId(userid);
	}

	public List<String> getSelUsers(String pguid){
		return getMapper().getSelUsers(pguid);
	}

	@Override
	public int deleteBactch(Object[] ids) throws Exception {
		int ret=super.deleteBactch(ids);
		return ret;
	}

	@Autowired
    private SchoolNewsMapper<T> mapper;

	public SchoolNewsMapper<T> getMapper() {
		return mapper;
	}
	
}
