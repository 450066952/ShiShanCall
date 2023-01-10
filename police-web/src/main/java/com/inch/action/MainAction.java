package com.inch.action;

import com.inch.annotation.Auth;
import com.inch.bean.SysMenu;
import com.inch.bean.SysMenuBtn;
import com.inch.model.SysUser;
import com.inch.service.SysMenuBtnService;
import com.inch.service.SysMenuService;
import com.inch.utils.*;
import com.inch.utils.WebConstant.SuperAdmin;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainAction extends BaseAction {

	
	private final static Logger log= Logger.getLogger(MainAction.class);
	
	@Autowired
	private SysMenuService<SysMenu> sysMenuService;
	@Autowired
	private SysMenuBtnService<SysMenuBtn> sysMenuBtnService;
	
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/login")
	public ModelAndView  login() throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("login", context);
	}
	
	
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/toLogin")
	public void  toLogin(String username,String password,HttpServletRequest request,HttpServletResponse response) throws Exception{

		SessionUtils.removeSession(request);
		
		if(StringUtils.isBlank(username)){
			sendFailureMessage(response, "账号不能为空.");
			return;
		}
		if(StringUtils.isBlank(password)){
			sendFailureMessage(response, "密码不能为空.");
			return;
		}
		String msg = "用户登录日志:";
		
		String json=BuidRequest.getRequestResult(request,response,"sys/toLogin.json","post");
		TonyResult taotaoResult = TonyResult.formatToPojo(json, SysUser.class);
		
		if(taotaoResult.getSuccess()){
			SysUser user =(SysUser)taotaoResult.getData();
			SessionUtils.setUser(request,user);
			
		}else{
			sendFailureMessage(response, taotaoResult.getMsg());
			return;
		}
		
		log.debug(msg+"["+username+"]"+"登录成功");
		sendSuccessMessage(response, "登录成功.");
		
		request.getSession().setAttribute("ctx", request.getContextPath());
	}
	
	
	
	
	
	@Auth(verifyURL=false)
	@RequestMapping("/main") 
	public ModelAndView  main(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object>  context = getRootMap();
		SysUser user = SessionUtils.getUser(request);
		List<SysMenu> rootMenus  ;
		List<SysMenuBtn> childBtns ;
//		
		List<SysMenu> mList=sysMenuService.getMenuTreeInfo("0",user.getIsadmin(),user.getId());
		
		if(mList==null||mList.size()==0){
			SessionUtils.removeUser(request);
			return forword("login", context);
		}
		
		if(user != null && SuperAdmin.YES.key ==  user.getIsadmin()){
			
		}else{
			rootMenus = sysMenuService.getRootMenuByUser(user.getId() );//根节点
			childBtns = sysMenuBtnService.getMenuBtnByUser(user.getId());//按钮操作
			buildData(rootMenus,childBtns,request); //构建必要的数据

			
		}
		
		context.put("user", user);
		//request.getSession().setAttribute("menuList", treeMenu(rootMenus,childMenus));
		request.getSession().setAttribute("menuList", mList);
		
		return forword("main/main",context); 
	}
	
	private void buildData(List<SysMenu> childMenus,List<SysMenuBtn> childBtns,HttpServletRequest request){
		
		List<String> accessUrls  = new ArrayList<String>();
		Map<String,List> menuBtnMap = new HashMap<String,List>();
		for(SysMenu menu: childMenus){
			if(StringUtils.isNotBlank(menu.getUrl())){
				List<String> btnTypes = new ArrayList<String>();
				for(SysMenuBtn btn  : childBtns){
					if(NumberUtils.toInt(menu.getId())==btn.getMenuid()){
						btnTypes.add(btn.getBtnType());
						
						URLUtils.getBtnAccessUrls(menu.getUrl(), btn.getActionUrls(),accessUrls);  //  
					}
				}
				menuBtnMap.put(menu.getUrl(), btnTypes);
				URLUtils.getBtnAccessUrls(menu.getUrl(), menu.getActions(),accessUrls);//  
				accessUrls.add(menu.getUrl());
			}
		}
		SessionUtils.setAccessUrl(request, accessUrls);//设置可访问的URL
		SessionUtils.setMemuBtnMap(request, menuBtnMap); //设置可用的按钮
		
	}
	
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/logout")
	public void  logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		SessionUtils.removeSession(request);
		response.sendRedirect("login.shtml");
	}
	
	
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/modifypwd")
	public void  modifypwd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String json=BuidRequest.getRequestResult(request,response,"sys/modifypwd.json","post");
		TonyResult taotaoResult = TonyResult.format(json);
		
		if(taotaoResult.getSuccess()){
			this.sendSuccessMessage(response, "修改成功");
			
			SysUser user1=SessionUtils.getUser(request);
			user1.setPassword(MD5.getMD5String(StringUtils.trimToEmpty(request.getParameter("newpwd"))));
			
			SessionUtils.setUser(request,user1);
		}else{
			this.sendFailureMessage(response, taotaoResult.getMsg());
		}
	}
}
