
package com.inch.rest.action;

import com.inch.model.QueryModel;
import com.inch.model.SysUser;
import com.inch.rest.service.WebStaticsService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.WebConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webstatistics")
public class WebStatisticsAction extends BaseAction{

	@Autowired
	private WebStaticsService<QueryModel> windowService;

	@RequestMapping("/queryByWindow")
	public Map<String,Object> queryByWindow(QueryModel model) throws Exception{
		SysUser suser =(SysUser) RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		if(model.getOrgid()==0){
			if(WebConstant.SuperAdmin.YES.key ==  suser.getIsadmin()){
				model.setIsadmin(WebConstant.SuperAdmin.YES.key);
			}else{
				model.setIsadmin(WebConstant.SuperAdmin.NO.key);
				model.setOrgid(suser.getSchoolid());
			}
		}

		if (StringUtils.isEmpty(model.getStarttime())){
			model.setStarttime(model.getStarttime() + " 00:00:00");
		}

		if (StringUtils.isNotBlank(model.getEndtime())){
			model.setEndtime(model.getEndtime() + " 23:59:59");
		}

		return  windowService.queryByWindow(model);
	}

	@RequestMapping("/queryByWindowDetial")
	public Map<String,Object> queryByWindowDetial(QueryModel model) throws Exception{
		return  windowService.queryByWindowDetial(model);
	}

	@RequestMapping("/queryByPerson")
	public Map<String,Object> queryByPerson(QueryModel model) throws Exception{
		SysUser suser =(SysUser) RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		if(model.getOrgid()==0){
			if(WebConstant.SuperAdmin.YES.key ==  suser.getIsadmin()){
				model.setIsadmin(WebConstant.SuperAdmin.YES.key);
			}else{
				model.setIsadmin(WebConstant.SuperAdmin.NO.key);
				model.setOrgid(suser.getSchoolid());
			}
		}

		if (StringUtils.isEmpty(model.getStarttime())){
			model.setStarttime(model.getStarttime() + " 00:00:00");
		}

		if (StringUtils.isNotBlank(model.getEndtime())){
			model.setEndtime(model.getEndtime() + " 23:59:59");
		}

		return  windowService.queryByPerson(model);
	}

	@RequestMapping("/queryByPersonDetial")
	public Map<String,Object> queryByPersonDetial(QueryModel model) throws Exception{
		return  windowService.queryByPersonDetial(model);
	}

	@RequestMapping("/queryByType")
	public Map<String,Object> queryByType(QueryModel model) throws Exception{
		SysUser suser =(SysUser) RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		if(model.getOrgid()==0){
			if(WebConstant.SuperAdmin.YES.key ==  suser.getIsadmin()){
				model.setIsadmin(WebConstant.SuperAdmin.YES.key);
			}else{
				model.setIsadmin(WebConstant.SuperAdmin.NO.key);
				model.setOrgid(suser.getSchoolid());
			}
		}

		if (StringUtils.isEmpty(model.getStarttime())){
			model.setStarttime(model.getStarttime() + " 00:00:00");
		}

		if (StringUtils.isNotBlank(model.getEndtime())){
			model.setEndtime(model.getEndtime() + " 23:59:59");
		}

		return  windowService.queryByType(model);
	}

	@RequestMapping("/queryByTypeDetial")
	public Map<String,Object> queryByTypeDetial(QueryModel model) throws Exception{
		return  windowService.queryByTypeDetial(model);
	}

}
