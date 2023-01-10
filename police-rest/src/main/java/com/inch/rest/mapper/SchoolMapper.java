package com.inch.rest.mapper;

import java.util.List;
import java.util.Map;

import com.inch.model.SchoolModel;

/**
 * SysUser Mapper
 * @author Administrator
 *
 */
public interface SchoolMapper<T> extends BaseMapper<T> {
	
	
/**
 * 查询年级
 * @param dto
 * @return
 */
	public List<T> queryBySchoolList(SchoolModel dto);
/**
 * 查询班级信息
 * @param dto
 * @return
 */
	public List<T> queryByGradeList(SchoolModel dto);
	
	/**
	 * 查看是否有子节点的信息
	 */
	
	public int isChildCnt(SchoolModel dto);
	
	public List<T> getDicList2(SchoolModel dto);


	
	/**
	 * 子节点上的所有父节点
	 * @param id
	 * @return
	 */
	public List<SchoolModel> getAllParent(int id);
	
	/**
	 * 获取根节点下的所有子节点
	 * @param id
	 * @return
	 */
	public List<SchoolModel> getAllChild(Map<String, Object> map);
	
	
	/**
	 * 插入一对一(谁添加了学校等相关信息就加入到对应关系中去)
	 * @param id
	 * @return
	 */
	public int insertUserSchoolRel(Map<String, Object> map);

	public int updateSchoolCourseTime(SchoolModel dto);

	int updateStartNumByOrg(int orgid);
	
}
