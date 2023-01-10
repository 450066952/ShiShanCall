
package com.inch.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import com.inch.annotation.Auth;
import com.inch.model.SysUser;
import com.inch.utils.BuidRequest;
import com.inch.utils.HtmlUtil;
import com.inch.utils.SessionUtils;
import com.inch.utils.TonyResult;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
@RequestMapping("/school") 
public class SchoolAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(SchoolAction.class);
	
	@RequestMapping("/list")
	public ModelAndView  getRootInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		
		return forword("school/schoolinfo",context);
	}
	
	
	
	@RequestMapping("/getId")
	public void getId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"school/getId.json","get");
	}
	
	@RequestMapping("/save")
	public void save(HttpServletResponse response,HttpServletRequest request) throws Exception{

		request.getSession().removeAttribute("schoolpower");
		request.getSession().removeAttribute("schooltreepower");

		String json=BuidRequest.getRequestResult(request,response,"school/save.json","post");
		
		TonyResult tonyResult = TonyResult.formatToPojo(json, SysUser.class);
		
		if(tonyResult.getSuccess()){
			if("insert".equals(tonyResult.getMsg())){
				SessionUtils.setUser(request, (SysUser)tonyResult.getData());
			}
			
			this.sendSuccessMessage(response, "保存成功");
		}else{
			HtmlUtil.writerStr(response, json);
		}
	}
	
	
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.getSession().removeAttribute("schoolpower");
		request.getSession().removeAttribute("schooltreepower");
		BuidRequest.sendRequest(request,response,"school/delete.json","post");
	}
	
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/schooltreeList") 
	public void  schooltreeList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//从rest服务去获取数据
		String json=(String)request.getSession().getAttribute("schooltreepower");
		
		if(StringUtils.isBlank(json)){
			json=BuidRequest.getRequestResult(request,response,"school/schooltreeList.json","post");
			request.getSession().setAttribute("schooltreepower", json);
		}
		
		HtmlUtil.writerStr(response, json);
	}
	
	@Auth(verifyURL=false)
	@RequestMapping("/schooltree") 
	public void  schooltree(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String json=(String)request.getSession().getAttribute("schoolpower");
		
		if(StringUtils.isBlank(json)){
			json=BuidRequest.getRequestResult(request,response,"school/schooltree.json","post");
			request.getSession().setAttribute("schoolpower", json);
		}
		
		HtmlUtil.writerStr(response, json);
	}

	@Auth(verifyURL=false)
	@RequestMapping("/initNum")
	public void initNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"school/initNum.json","post");
	}

	@Auth(verifyURL=false)
	@RequestMapping("/getMyOrgLists")
	public void getOrgLists(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"school/getMyOrgLists.json","post");
	}
}
