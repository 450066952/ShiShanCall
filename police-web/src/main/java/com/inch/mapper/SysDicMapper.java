package com.inch.mapper;

import java.util.List;

import com.inch.model.SysDicModel;

/**
 * SysUser Mapper
 * @author Administrator
 *
 */
public interface SysDicMapper<T> extends BaseMapper<T> {
	
	/**
	   获取字典表 信息主表
	 * @param email
	 * @param pwd
	 * @return
	 */
	public List<T> getDicCode();
	
	
	/**
	 * 获取父节点下的所有子节点--树形结构
	 * @param email
	 * @return
	 */
	public List<SysDicModel> getDicList(SysDicModel dto);
	
	/**
	 * 查看是否有子节点的信息
	 */
	
	public int isChildCnt(SysDicModel dto);
}
