
package com.inch.action.webStatics;

import com.inch.action.BaseAction;
import com.inch.model.SchoolModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.CommonUtil;
import com.inch.utils.TonyResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/windowStatics")
public class WindowStaticsAction extends BaseAction {

	@RequestMapping("/list")
	public ModelAndView  list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();

		context.put("starttime", CommonUtil.nowDay());
		context.put("endtime", CommonUtil.nowDay());
		String json=BuidRequest.getRequestResult(request,response,"school/getOrgLists","get");
		TonyResult t = TonyResult.formatToList(json,SchoolModel.class);
		context.put("orglist", t.getData());
		
		return forword("webStatics/window",context);
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"webstatistics/queryByWindow","get");
	}

	@RequestMapping("/queryByWindowDetial")
	public void  queryByWindowDetial(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"webstatistics/queryByWindowDetial","get");
	}
}
