package com.inch.rest.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inch.model.SysMenu;
import com.inch.model.SysUser;
import com.inch.rest.service.SysMenuService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;

@Controller
@RequestMapping("/sysPermission")
public class SysPermissionAction extends BaseAction {

	private final static Logger log = Logger.getLogger(SysPermissionAction.class);

	@Autowired
	private SysMenuService<SysMenu> sysMenuService;

	/**
	 * 顶级菜单 json
	 * 
	 * @param menuId
	 *            此菜单id不查询，可以为空
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/menu",method=RequestMethod.GET)
	public void menu(HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		SysUser user = (SysUser) RedisUtil.getObjByKey(WebConstant.WEBUSER
				+ StringUtils.trimToEmpty(request.getParameter("username")));
		
		Map<String,Object>  context = new HashMap<String,Object>();
		
		List<SysMenu> childMenus = null;

		// 超级管理员
		if (SuperAdmin.YES.key == user.getIsadmin()) {
			childMenus = sysMenuService.getChildMenu();// 查询所有子节点

		} else {
			childMenus = sysMenuService.getChildMenuByUser(user.getId());// 子节点
		}
		
		context.put(SUCCESS, true);
		context.put("data", childMenus);
		HtmlUtil.writerWithOutJson(response, context);
	}
}
