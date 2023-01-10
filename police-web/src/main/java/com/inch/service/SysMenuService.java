package com.inch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.bean.SysMenu;
import com.inch.bean.SysMenuBtn;
import com.inch.bean.SysRoleRel;
import com.inch.bean.SysRoleRel.RelType;
import com.inch.mapper.SysMenuMapper;
import com.inch.model.SysDicModel;

/**
 * 
 * <br>
 * <b>功能：</b>SysMenuService<br>
 * <b>作者：</b>Tony<br>
 * <b>日期：</b> Dec 9, 2011 <br>
 * <b>版权所有：<b>版权所有(C) 2014 Tony<br>
 */
@Service("sysMenuService")
public class SysMenuService<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(SysMenuService.class);


	@Autowired
	private SysRoleRelService<SysRoleRel> sysRoleRelService;
	
	@Autowired
	private SysMenuBtnService<SysMenuBtn> sysMenuBtnService;
	
	@Autowired
    private SysMenuMapper<T> mapper;
	
	/**
	 * 保存菜单btn
	 * @param btns
	 * @throws Exception 
	 */
	public void saveBtns(String menuid,List<SysMenuBtn> btns) throws Exception{
		if(btns == null || btns.isEmpty()){
			return;
		}
		for (SysMenuBtn btn : btns) {
			if(btn.getId()!= null && "1".equals(btn.getDeleteFlag())){
				sysMenuBtnService.delete(btn.getId());
				continue;
			}
			btn.setMenuid(NumberUtils.toInt(menuid));
			if(btn.getId() == null){
				sysMenuBtnService.add(btn);
			}else{
				sysMenuBtnService.update(btn);
			}
		}
		
	}


	public int add(T t) throws Exception {
		int ret=super.add(t);
		SysMenu menu =(SysMenu)t;
		saveBtns(menu.getId(),menu.getBtns());
		return ret;
	}

	public int update(T t) throws Exception {
		int ret=super.update(t);
		SysMenu menu =(SysMenu)t;
		saveBtns(menu.getId(),menu.getBtns());
		return ret;
	}




	/**
	 * 查询所有系统菜单列表
	 * @return
	 */
	public List<T> queryByAll(){
		return mapper.queryByAll();
	}
	
	
	//-------
	public List<SysMenu> getMenuTree(String parentId,int isadmin,int userid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentId);
		map.put("isadmin", isadmin);
		map.put("userid", userid);
		
		return mapper.getMenuTree(map);
	}
	
	public int getChildCnt(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		
		return mapper.getChildCnt(map);
	}
	
	public List<SysMenu> getMenuTreeInfo(String pid,int isadmin,int userid){
		
		List<SysMenu> list=getMenuTree(pid,isadmin,userid);
		SysMenu model=new SysMenu();
		if(list.size()>0){
			
			for(int i=0;i<list.size();i++){
				
				model=list.get(i);
				
				int childcnt=getChildCnt(model.getId());
				
				if(childcnt>0){
					
					model.setChildList(getMenuTreeInfo(model.getId(),isadmin,userid));
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 获取顶级菜单
	 * @return
	 */
	public List<T> getRootMenu(Integer menuId){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("menuId", menuId);
		return mapper.getRootMenu(map);
	}
	
	/**
	 * 获取子菜单
	 * @return
	 */
	public List<T> getChildMenu(){
		return mapper.getChildMenu();
	}
	/**
	 * 根据用户id查询父菜单
	 * @param roleId
	 * @return
	 */
	public List<T> getRootMenuByUser(Integer userId){
		return getMapper().getRootMenuByUser(userId);
	}
	
	
	/**
	 * 根据用户id查询子菜单
	 * @param roleId
	 * @return
	 */
	public List<T> getChildMenuByUser(Integer userId){
		return getMapper().getChildMenuByUser(userId);
	}
	
	
	/**
	 * 根据权限id查询菜单
	 * @param roleId
	 * @return
	 */
	public List<T> getMenuByRoleId(Integer roleId){
		return getMapper().getMenuByRoleId(roleId);
	}
	
	
	
	@Override
	public void delete(Object[] ids) throws Exception {
		super.delete(ids);
		//删除关联关系
		for(Object id : ids){
			sysRoleRelService.deleteByObjId((Integer)id, RelType.MENU.key);
			sysMenuBtnService.deleteByMenuid((Integer)id);
		}
	}

	
	
	
	public SysMenuMapper<T> getMapper() {
		return mapper;
	}

}
