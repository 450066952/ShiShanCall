
package com.inch.rest.action;

import com.inch.interceptor.Auth;
import com.inch.model.*;
import com.inch.rest.service.BasicInfoService;
import com.inch.rest.service.SchoolService;
import com.inch.rest.service.SysDicCodeService;
import com.inch.rest.service.SysDicService;
import com.inch.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/sysDic")
public class SysDicAction extends BaseAction{

	@Autowired
	private SysDicService<SysDicModel> sysDicService;
	@Autowired
	private SchoolService<SchoolModel> schoolService;
	@Autowired
	private SysDicCodeService<SysDicCodeModel> sysDicCodeService;

	@Autowired
	private BasicInfoService<BasicInfoModel> basicInfoService;

	@Auth(verifyLogin = false)
	@RequestMapping("/getDic")
	public void getDic(SysDicModel model, HttpServletResponse response, HttpServletRequest request) throws Exception{
		List<SysDicModel> list=sysDicService.getDicList(model);

		Map<String,Object> context = new HashMap<>();
		context.put(SUCCESS, true);
		context.put("data", list);
		context.put("msg", "");

		String isorg= StringUtils.trimToEmpty(request.getParameter("isorg"));

		if(StringUtils.isNotBlank(isorg)){
			SchoolModel smodel=new SchoolModel();
			smodel.setPid(0);
			List<SchoolModel> slist=schoolService.queryByListT(smodel);
			context.put("school",slist);
		}
		HtmlUtil.writerJson(response, context);
	}

	@RequestMapping("/getDicLists")
	public void getDicLists(SysDicModel model, HttpServletResponse response) throws Exception{

		HtmlUtil.writerJson(response, sysDicService.queryByList(model));
	}

	@RequestMapping("/getDicList")
	public void getDic(SysDicModel model, HttpServletResponse response)throws Exception{
		HtmlUtil.writerJson(response, sysDicService.queryByList(model));
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getDicInfo")
	public void getDicInfo(SysDicModel model, HttpServletResponse response) throws Exception{

		List<SysDicModel> codeList=sysDicService.getDicCode(model);
		List<SysDicModel> dicList=sysDicService.getDicList(model);

		BasicInfoModel c=basicInfoService.queryById("");

		Map<String,Object> context = new HashMap<>();
		context.put(SUCCESS, true);
		context.put("code", codeList);
		context.put("detial", dicList);
		context.put("basic", c);
		context.put("msg", "");
		HtmlUtil.writerJson(response, context);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getDicCode")
	public void getDicCodeInfo(SysDicCodeModel model, HttpServletResponse response) throws Exception{

		HtmlUtil.writerJson(response, sysDicCodeService.queryByList(model));
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/addDicCode")
	public void addDicCode(SysDicCodeModel model,HttpServletResponse response)throws Exception{
		Integer result = 0;
		if(org.apache.commons.lang.StringUtils.trimToEmpty(model.getGuid()).length() == 0 ){
			model.setGuid(UUID.randomUUID().toString());
			model.setAddtie(new Timestamp(System.currentTimeMillis()).toString());
			result = sysDicCodeService.add(model);
		}else{
			result = sysDicCodeService.update(model);
		}

		if(result > 0){
			this.sendSuccessMessage(response, "修改成功");
		}else{
			this.sendFailureMessage(response, "修改失败！");
		}

	}

	@RequestMapping("/addDic")
	public void addDic(SysDicModel model,HttpServletResponse response)throws Exception{
		List<SysDicModel> dicModels = sysDicService.getDic(model.getOrgid());
		Integer result = 0;
		if(org.apache.commons.lang.StringUtils.trimToEmpty(model.getGuid()).length() == 0 ){


			for (SysDicModel dicModel : dicModels){
				if(model.getPrefix().equals(dicModel.getPrefix())){
					this.sendFailureMessage(response, "getPrefix重复！");
					return;
				}

			}

			model.setGuid(UUID.randomUUID().toString());
			model.setAddtime(new Timestamp(System.currentTimeMillis()).toString());
			result = sysDicService.add(model);

		}else{

			for (SysDicModel dicModel : dicModels){
				if(model.getPrefix().equals(dicModel.getPrefix()) && !model.getGuid().equals(dicModel.getGuid())){
					this.sendFailureMessage(response, "getPrefix重复！");
					return;
				}
			}

			result = sysDicService.update(model);
		}

		if(result > 0){
			this.sendSuccessMessage(response, "修改成功");
		}else{
			this.sendFailureMessage(response, "修改失败！");
		}

	}
	@Auth(verifyLogin = false)
	@RequestMapping("/getById")
	public void getById(SysDicCodeModel model, HttpServletResponse response) throws Exception{

		Map<String,Object>  context = new HashMap<String,Object>();

		SysDicCodeModel bean = sysDicCodeService.queryById(model);

		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}

		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}

	@RequestMapping("/getByDicId")
	public void getByDicId(SysDicModel model, HttpServletResponse response) throws Exception{

		Map<String,Object>  context = new HashMap<String,Object>();

		SysDicModel bean = sysDicService.getByDicId(model);

		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}

		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}

	@RequestMapping("/deleteCode")
	public void deleteCode(String id, HttpServletResponse response) throws Exception{
		int result = sysDicCodeService.delete(id.split(","));
		if(result > 0){
			this.sendSuccessMessage(response, "删除成功");
		}else{
			this.sendFailureMessage(response, "删除失败！");
		}
	}

	@RequestMapping("/delete")
	public InchModel delete(String id,HttpServletResponse response) throws Exception{
		int ret =sysDicService.delete(id.split(","));

		if(ret > 0){
			this.sendSuccessMessage(response, "删除成功");
		}else{
			this.sendFailureMessage(response, "删除失败！");
		}

		if(ret>0){
			return this.successMsg("删除成功");
		}else{
			return this.failMsg("删除失败");
		}
	}

	@RequestMapping("/getDicCodeList")
	public void getDicCodeList(SysDicCodeModel model, HttpServletResponse response)throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();

		List<SysDicCodeModel> dicCodeModels = sysDicCodeService.queryByListT(model);

		context.put(SUCCESS, true);
		context.put("dicCode", dicCodeModels);
		context.put("msg", "");
		HtmlUtil.writerJson(response, context);
	}


	//二级菜单
	@RequestMapping("/getTwoDic")
	public void getTwoDic(SysDicModel model, HttpServletResponse response) throws Exception{

		Map<String,Object>  context = new HashMap<>();
		model.setFlag("2");

		List<SysDicModel> list = sysDicService.getDicList(model);
		context.put(SUCCESS, true);
		context.put("data", list);
		HtmlUtil.writerJson(response, context);
	}

	@RequestMapping("/getThreeDic")
	public void getThreeDic(SysDicModel model, HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<>();
		model.setFlag("3");
		List<SysDicModel> list = sysDicService.getDicList(model);
		context.put(SUCCESS, true);
		context.put("data", list);
		HtmlUtil.writerJson(response, context);
	}

}
