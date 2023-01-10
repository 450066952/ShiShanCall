
package com.inch.rest.action;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.inch.interceptor.Auth;
import com.inch.model.InchModel;
import com.inch.model.SysUser;
import com.inch.model.WindowModel;
import com.inch.rest.service.WindowService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.WebConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/window")
public class WindowAction extends BaseAction{

	@Autowired
	private WindowService<WindowModel> windowService;

	@RequestMapping("/getQueryList")
	public Map<String,Object> dataList(WindowModel model) throws Exception{
		return  windowService.queryByList(model);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getWindowList")
	public Map<String,Object> getWindowList(WindowModel model) throws Exception{
		return  windowService.queryByList2(model);
	}

	@RequestMapping("/opList")
	public List<WindowModel> opList(WindowModel model) throws Exception{
		return  windowService.queryByListT(model);
	}
	
	@RequestMapping("/save")
	public InchModel save(WindowModel bean,HttpServletRequest request) throws Exception{
		int ret=0;

		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));
		bean.setAdduser(suser.getId());
		
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			bean.setGuid(UUID.randomUUID().toString());
			ret=windowService.add(bean);
		}else{
			ret=windowService.update(bean);
		}
		if(ret>0){
			return this.successMsg("保存成功");
		}else{

			return this.failMsg("保存失败");
		}
	}
	
	@RequestMapping("/getId")
	public InchModel getId(String id) throws Exception{
		WindowModel bean  = windowService.queryById(id);
		if(bean  == null){
			return this.failMsg("没有找到对应的记录!");
		}
		return this.successData("ok",bean);
	}
	
	@RequestMapping("/delete")
	public InchModel delete(String id) throws Exception{
	    int ret =windowService.deleteBactch(id.split(","));

		if(ret>0){
			return this.successMsg("删除成功");
		}else{
			return this.failMsg("删除失败");
		}
	}
}
