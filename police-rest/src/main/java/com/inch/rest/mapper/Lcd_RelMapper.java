package com.inch.rest.mapper;

import java.util.List;
import java.util.Map;



/**
 * SysUser Mapper
 * @author Administrator
 *
 */
public interface Lcd_RelMapper<T> extends BaseMapper<T> {
	
	/**
	 * 清除屏显关系
	 * @param guid
	 */
	public void deleteLcdRel(String guid);
	
	public void addLcdRelBactch(Map<String,Object> map);
	
	/**
	 * 通过班级id获取屏显设备
	 * @param classids
	 * @return
	 */
	public List<String> getLcdSn(String classids);
	
	/**
	 * 通过校园id去获取设备
	 * @param classids
	 * @return
	 */
	public List<String> getLcdSnBySchool(String schoolid);
}
