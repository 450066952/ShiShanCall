
package com.inch.action;

import com.inch.annotation.Auth;
import com.inch.model.SchoolNewsModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.TonyResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class SchoolNewsViewAction extends BaseAction {

	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/detial")
	public ModelAndView detial(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String json= BuidRequest.getRequestResult(request,response,"schoolnews/getId.json","get");
		TonyResult t = TonyResult.formatToPojo(json,SchoolNewsModel.class);

		Map<String,Object>  context = getRootMap();
		context.put(SUCCESS, true);
		context.put("data", t.getData());

		return forword("frontPage/content",context);
	}

}
