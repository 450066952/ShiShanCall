
package com.inch.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.annotation.Auth;
import com.inch.bean.SysMenu;
import com.inch.bean.SysRole;
import com.inch.bean.SysRoleRel;
import com.inch.bean.SysRoleRel.RelType;
import com.inch.model.SysRoleModel;
import com.inch.service.SysMenuService;
import com.inch.service.SysRoleRelService;
import com.inch.service.SysRoleService;
import com.inch.utils.HtmlUtil;
 
@Controller
@RequestMapping("/sysRole") 
public class SysRoleAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(SysRoleAction.class);
	
	// Servrice start
	@Autowired
	private SysRoleService<SysRole> sysRoleService; 
	
	// Servrice start
	@Autowired
	private SysMenuService<SysMenu> sysMenuService; 
	@Autowired
	private SysRoleRelService<SysRoleRel> sysRoleRelService;
	/**
	 * 
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/role")
	public ModelAndView  list(SysRoleModel model,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("sys/sysRole",context); 
	}
	
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataList") 
	public void  datalist(SysRoleModel model,HttpServletResponse response) throws Exception{
		
		//是否分页
		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}
		List<SysRole> dataList = sysRoleService.queryByList(model);
		
		PageInfo page = new PageInfo(dataList);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(SysRole bean,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		String menubtn=StringUtils.trimToEmpty(request.getParameter("menubtn"));
		
		String[] mbtns=menubtn.split(",");
		
		List<Integer> mlist=new ArrayList<Integer>();
		List<Integer> blist=new ArrayList<Integer>();
		
		if(mbtns!=null){
			
			for(String s :mbtns){
				
				if(s.indexOf("btn")>0){
					blist.add(NumberUtils.toInt(s.replace("_btn", "")));
				}else {
					mlist.add(NumberUtils.toInt(s));
				}
				
			}
		}
		
		if(bean.getId() == null){
			sysRoleService.add(bean,mlist,blist);
		}else{
			sysRoleService.update(bean,mlist,blist);
		}
		sendSuccessMessage(response, "保存成功~");
	}
	
	
	@RequestMapping("/getId")
	public void getId(Integer id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		SysRole bean  = sysRoleService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		//获取权限关联的菜单
		Integer[] menuIds = null;
		List<SysMenu> menuList =  sysMenuService.getMenuByRoleId(id);
		
		//获取权限下关联的按钮
		Integer[] btnIds = null;
		List<SysRoleRel>  btnList =sysRoleRelService.queryByRoleId(id, RelType.BTN.key);
		
		
		if(menuList != null){
			menuIds = new Integer[menuList.size()];
			int i = 0;
			for(SysMenu item : menuList){
				menuIds[i] = NumberUtils.toInt(item.getId());
				i++;
			}
		}
	
		if(btnList != null){
			btnIds = new Integer[btnList.size()];
			int i = 0;
			for(SysRoleRel item : btnList){
				btnIds[i] = item.getObjId();
				i++;
			}
		}

		Map<String,Object> data = BeanUtils.describe(bean);
		data.put("menuIds", menuIds);
		data.put("btnIds", btnIds);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	
	
	@RequestMapping("/delete")
	public void delete(Integer[] id,HttpServletResponse response) throws Exception{
		sysRoleService.delete(id);
		sendSuccessMessage(response, "删除成功");
	}
	
	
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/loadRoleList")
	public void loadRoleList(HttpServletResponse response) throws Exception{
		List<SysRole>  roloList = sysRoleService.queryAllList();
		HtmlUtil.writerJson(response, roloList);
	}


	
	
}
