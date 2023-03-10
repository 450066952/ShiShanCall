package com.inch.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.annotation.Auth;
import com.inch.bean.SysMenu;
import com.inch.bean.SysMenuBtn;
import com.inch.bean.TreeNode;
import com.inch.bean.BaseBean.DELETED;
import com.inch.model.SysMenuModel;
import com.inch.service.SysMenuBtnService;
import com.inch.service.SysMenuService;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.HtmlUtil;
import com.inch.utils.TreeUtil;
 
@Controller
@RequestMapping("/sysMenu") 
public class SysMenuAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(SysMenuAction.class);
	
	@Autowired 
	private SysMenuService<SysMenu> sysMenuService; 
	
	@Autowired
	private SysMenuBtnService<SysMenuBtn> sysMenuBtnService;
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@RequestMapping("/menu")
	public ModelAndView  menu(SysMenuModel model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		model.setDeleted(DELETED.NO.key);
		
		Map<String, Object> result =getBtnByUrl("/sysMenu/menu.shtml", request, response);//获取按钮权限
		request.setAttribute("tbar",FastJsonUtils.toJsonWithNUll(result));
		
		return forword("sys/sysMenu",context); 
	}
	
	/**
	 * 顶级菜单 json 
	 * @param menuId 此菜单id不查询，可以为空
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/rootMenuJson") 
	public void  rootMenu(Integer menuId,HttpServletResponse response) throws Exception{
		List<SysMenu> dataList = sysMenuService.getRootMenu(menuId);
		if(dataList==null){
			dataList = new ArrayList<SysMenu>();
		}
		HtmlUtil.writerJson(response, dataList);
	}
	
	
	/**
	 * json 列表页面
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataList") 
	public void  dataList(SysMenuModel model,HttpServletResponse response) throws Exception{
		
		//分页
		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}
				
		List<SysMenu> dataList = sysMenuService.queryByList(model);
		
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
	public void save(SysMenu bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//设置菜单按钮数据
		List<SysMenuBtn> btns = getReqBtns(request);
		bean.setBtns(btns);
		if(bean.getId() == null){
			bean.setDeleted(DELETED.NO.key);
			sysMenuService.add(bean);
		}else{
			sysMenuService.update(bean);
		}
		sendSuccessMessage(response, "保存成功~");
	}
	
	@RequestMapping("/getId")
	public void getId(Integer id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		SysMenu bean  = sysMenuService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		List<SysMenuBtn> btns = sysMenuBtnService.queryByMenuid(id);
		bean.setBtns(btns);
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/delete")
	public void delete(Integer[] id,HttpServletResponse response) throws Exception{
		if(id != null && id.length > 0){
			sysMenuService.delete(id);
			sendSuccessMessage(response, "删除成功");
		}else{
			sendFailureMessage(response, "未选中记录");
		}
	}
	
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/getMenuTree")
	public void getMenuTree(Integer id,HttpServletResponse response) throws Exception{
		
		List<SysMenu> mList=sysMenuService.queryByAll();

		HtmlUtil.writerJson(response, mList);
	}
	
	/**
	 * 构建树形菜单
	 * @return
	 */
	public List<TreeNode> treeMenu(){
		List<SysMenu> rootMenus = sysMenuService.getRootMenu(null);//根节点
		List<SysMenu> childMenus = sysMenuService.getChildMenu();//子节点
		List<SysMenuBtn> childBtns = sysMenuBtnService.queryByAll();
		TreeUtil util = new TreeUtil(rootMenus,childMenus,childBtns);
		return util.getTreeNode();
	}
	
	/**
	 * 获取请求的菜单按钮数据
	 * @param request
	 * @return
	 */
	public List<SysMenuBtn> getReqBtns(HttpServletRequest request){
		List<SysMenuBtn> btnList= new ArrayList<SysMenuBtn>();
		String[] btnId  = request.getParameterValues("btnId");
		String[] btnName  = request.getParameterValues("btnName");
		String[] btnType  = request.getParameterValues("btnType");
		String[] actionUrls  = request.getParameterValues("actionUrls");
		String[] deleteFlag  = request.getParameterValues("deleteFlag");
		
		if(btnId!=null){
			for (int i = 0; i < btnId.length; i++) {
				if(StringUtils.isNotBlank(btnName[i]) && StringUtils.isNotBlank(btnType[i])){
					SysMenuBtn btn = new SysMenuBtn();
					if(StringUtils.isNotBlank(btnId[i]) && NumberUtils.isNumber(btnId[i])){
						btn.setId(NumberUtils.toInt(btnId[i]));
					}
					btn.setBtnName(btnName[i]);
					btn.setBtnType(btnType[i]);
					btn.setActionUrls(actionUrls[i]);
					btn.setDeleteFlag(deleteFlag[i]);
					btnList.add(btn);
				}
			}
		}
		return btnList;
	}
}
