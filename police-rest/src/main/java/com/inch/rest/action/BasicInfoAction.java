
package com.inch.rest.action;

import com.inch.model.BasicInfoModel;
import com.inch.rest.service.BasicInfoService;
import com.inch.utils.HtmlUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/basic")
public class BasicInfoAction extends BaseAction {
	
	private final static Logger log= Logger.getLogger(BasicInfoAction.class);
	
	@Autowired
	private BasicInfoService<BasicInfoModel> basicInfoService;
	
	@RequestMapping("/list.json")
	public void  list(HttpServletResponse response, HttpServletRequest request) throws Exception{
		BasicInfoModel c=basicInfoService.queryById("");

		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put(SUCCESS, true);
		jsonMap.put(MSG, "OK");
		jsonMap.put("data",c);

		HtmlUtil.writerJson(response, jsonMap);
	}

	@RequestMapping("/save.json")
	public void save(BasicInfoModel bean,  HttpServletResponse response) throws Exception{
		
		int ret=0;
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			bean.setGuid(UUID.randomUUID().toString());
			ret=basicInfoService.add(bean);
		}else{
			ret=basicInfoService.update(bean);
		}
		
		if(ret>0){
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put(SUCCESS, true);
			result.put(MSG, "保存成功~");
			result.put("guid", bean.getGuid());
			
			HtmlUtil.writerJson(response, result);
		}else{
			sendFailureMessage(response, "保存失败");
		}
	}
}
