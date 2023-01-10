package com.inch.mapper;

import java.util.List;
import java.util.Map;

import com.inch.bean.SysMenu;

/**
 * SysMenu Mapper
 * @author Administrator
 *
 */
public interface SysMenuMapper<T> extends BaseMapper<T> {
	
	public List<SysMenu> getMenuTree(Map<String, Object> map);
	
	public int getChildCnt(Map<String, Object> map);
	
	
	/**
	 * 查询所有系统菜单列表
	 * @return
	 */
	public List<T> queryByAll();
	
	
	/**
	 * 获取顶级菜单
	 * @return
	 */
	public List<T> getRootMenu(Map map);
	
	/**
	 * 获取子菜单
	 * @return
	 */
	public List<T> getChildMenu();
	

	/**
	 * 根据权限id查询菜单
	 * @param roleId
	 * @return
	 */
	public List<T> getMenuByRoleId(Integer roleId);
	
	
	/**
	 * 根据用户id查询父菜单菜单
	 * @param userId
	 * @return
	 */
	public List<T> getRootMenuByUser(Integer userId);
	
	/**
	 * 根据用户id查询子菜单菜单
	 * @param userId
	 * @return
	 */
	public List<T> getChildMenuByUser(Integer userId);
	
}
