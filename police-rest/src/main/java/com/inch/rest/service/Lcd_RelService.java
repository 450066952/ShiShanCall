package com.inch.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.rest.mapper.Lcd_RelMapper;

/**
 * 
 * @Title: NoticeService.java
 * @Package com.tony.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月12日
 */
@Service("lcd_RelService")
public class Lcd_RelService<T> extends BaseService<T> {

	/**
	 * 根据关联对象id,关联类型删除 
	 * @param objId
	 * @param relType
	 */
	public void deleteLcdRel(String guid){
		getMapper().deleteLcdRel(guid);
	}
	
	/**
	 * 添加用户权限
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	public void addLcdRel(String guid,String classids) throws Exception{
		
		if(StringUtils.trimToEmpty(guid).length()==0){ 
			return;
		}
		
		
		Map<String,Object> map=new HashMap<String, Object>();
		
		map.put("guid", guid);
		if(classids!=null){
			map.put("classids", classids.split(","));
		}else{
			map.put("classids", "");
		}
		
		//清除关联关系
		deleteLcdRel(guid);
		//插入软连关系
		getMapper().addLcdRelBactch(map);
	}
	
	
	/**
	 * 根据班级id获取屏显guid
	 * @param objId
	 * @param relType
	 */
	public List<String> getLcdSn(String classids){
		return getMapper().getLcdSn(classids);
	}
	
	/**
	 * 根据校园id获取屏显guid
	 * @param objId
	 * @param relType
	 */
	public List<String> getLcdSnBySchool(String schoolid){
		return getMapper().getLcdSnBySchool(schoolid);
	}
	
	
	
	
	@Autowired
    private Lcd_RelMapper<T> mapper;

		
	public Lcd_RelMapper<T> getMapper() {
		return mapper;
	}
	
}
