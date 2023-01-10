
package com.inch.action;

import com.alibaba.fastjson.JSONObject;
import com.inch.annotation.Auth;
import com.inch.model.SchoolModel;
import com.inch.model.SysDicCodeModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.FastJsonUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
 
@Controller
@RequestMapping("/sysDic") 
public class SysDicAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(SysDicAction.class);

	@RequestMapping("/dicCode")
	public ModelAndView  getDicCodeInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map<String,Object>  context = getRootMap();
		String json=BuidRequest.getRequestResult(request,response,"sysDic/getDic?isorg=1","get");
		JSONObject jsonObject = JSONObject.parseObject(json) ;
		List<SchoolModel> schoollist= FastJsonUtils.toList(jsonObject.getString("school"),SchoolModel.class);
		context.put("orglist", schoollist);
		return forword("sys/sysDicCode",context);

	}

	@RequestMapping("/getDicCode")
	public void getDicCode(HttpServletResponse response, HttpServletRequest request){
		BuidRequest.sendRequest(request,response,"sysDic/getDicCode","get");
	}

	@RequestMapping("/saveDicCode")
	public void saveDicCode(HttpServletResponse response, HttpServletRequest request){
		BuidRequest.sendRequest(request,response,"sysDic/addDicCode","get");
	}

	@RequestMapping("/saveDic")
	public void saveDic(HttpServletResponse response, HttpServletRequest request){
		BuidRequest.sendRequest(request,response,"sysDic/addDic","get");
	}

	@RequestMapping("/getById")
	public void getById(HttpServletResponse response, HttpServletRequest request){

		BuidRequest.sendRequest(request,response,"sysDic/getById","get");
	}

	@RequestMapping("/getByDicId")
	public void getByDicId(HttpServletResponse response, HttpServletRequest request){
		BuidRequest.sendRequest(request,response,"sysDic/getByDicId","get");
	}

	@RequestMapping("/dic")
	public ModelAndView  getRootInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		String json=BuidRequest.getRequestResult(request,response,"sysDic/getDic?isorg=1","get");
		JSONObject jsonObject = JSONObject.parseObject(json) ;
		List<SchoolModel> schoollist= FastJsonUtils.toList(jsonObject.getString("school"),SchoolModel.class);

		String json2=BuidRequest.getRequestResult(request,response,"sysDic/getDicCodeList","get");
		JSONObject jsonObject2 = JSONObject.parseObject(json2) ;
		List<SysDicCodeModel> rootlist = FastJsonUtils.toList(jsonObject2.getString("dicCode"),SysDicCodeModel.class);
		context.put("orglist", schoollist);
		context.put("rootlist", rootlist);
		return forword("sys/sysDic",context);
	}


	@Auth(verifyURL = false)
	@RequestMapping("/getDicLists")
	public void  getDicLists(HttpServletResponse response, HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"sysDic/getDicLists","get");
	}


	@Auth(verifyURL = false)
	@RequestMapping("/deleteCode")
	public void deleteCode(HttpServletResponse response, HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"sysDic/deleteCode","get");
	}

	@Auth(verifyURL = false)
	@RequestMapping("/delete")
	public void delete(HttpServletResponse response, HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"sysDic/delete","get");
	}

	@Auth(verifyURL = false)
	@RequestMapping("/getDic")
	public void  getDic(HttpServletResponse response, HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"sysDic/getDic","get");
	}

	@Auth(verifyURL = false)
	@RequestMapping("/getTwoDic")
	public void  getTwoDic(HttpServletResponse response, HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"sysDic/getTwoDic","get");
	}

	@Auth(verifyURL = false)
	@RequestMapping("/getThreeDic")
	public void  getThreeDic(HttpServletResponse response, HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"sysDic/getThreeDic","get");
	}
}
