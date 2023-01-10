
package com.inch.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import com.inch.model.BasicInfoModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.TonyResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/basic")
public class BasicInfoAction extends BaseAction {
	
	private final static Logger log= Logger.getLogger(BasicInfoAction.class);
	
	@RequestMapping("/list")
	public ModelAndView  list(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		Map<String,Object>  context = getRootMap();
		String json= BuidRequest.getRequestResult(request,response,"basic/list.json","get");
		TonyResult taotaoResult = TonyResult.formatToPojo(json, BasicInfoModel.class);
		context.put("info", taotaoResult.getData());
		
		return forword("basic/basic",context);
	}
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"basic/save.json","post");
	}
}
