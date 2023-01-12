
package com.inch.rest.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.interceptor.Auth;
import com.inch.model.BaseModel;
import com.inch.model.StatisticsModel;
import com.inch.model.StatisticsPersonModel;
import com.inch.model.WindowModel;
import com.inch.rest.service.StatisticsService;
import com.inch.rest.service.WindowService;
import com.inch.utils.CommonUtil;
import com.inch.utils.HtmlUtil;
import com.socket.server.socket.pub.CharacterHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsAction {

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private WindowService<WindowModel> windowService;

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByPerson")
	public void queryByPerson(StatisticsPersonModel model,HttpServletRequest request, HttpServletResponse response) throws Exception{

		if(StringUtils.isBlank(model.getStarttime())){
			model.setStarttime(CommonUtil.nowDay());
		}

		if(StringUtils.isBlank(model.getEndtime())){
			model.setEndtime(CommonUtil.nowDay());
		}

		Map<String,Object> map=new HashMap<>();
		map.put("starttime",model.getStarttime() + " 00:00:00");
		map.put("endtime",model.getEndtime() + " 23:59:59");
		map.put("orgid",NumberUtils.toInt(request.getParameter("orgid")));

		if(model.getLength() > -1){
			PageHelper.startPage(model.getPageno(),model.getLength());
		}

		List<StatisticsModel> list =statisticsService.queryByPerson(map);
		PageInfo page = new PageInfo(list);
		Map<String,Object> jsonMap=new HashMap<>();

		jsonMap.put("success", true);
		jsonMap.put("data",list);
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		HtmlUtil.writerJson(response, jsonMap);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByDayTotal")
	public List<StatisticsModel> queryByDayTotal(String starttime,String endtime,HttpServletRequest request) throws Exception{
		if(StringUtils.isBlank(starttime)){
			starttime= CommonUtil.nowDay();
		}

		if(StringUtils.isBlank(endtime)){
			endtime=CommonUtil.nowDay();
		}
		Map<String,Object> map=new HashMap<>();
		map.put("starttime",starttime+" 00:00:00");
		map.put("endtime",endtime+" 23:59:59");
		map.put("orgid",NumberUtils.toInt(request.getParameter("orgid")));

		return  statisticsService.queryByDayTotal(map);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByDayType")
	public List<StatisticsModel> queryByDayType(StatisticsModel model) throws Exception{
		if(StringUtils.isBlank(model.getStarttime())){
			model.setStarttime(CommonUtil.nowDay());
		}

		if(StringUtils.isBlank(model.getEndtime())){
			model.setEndtime(CommonUtil.nowDay());
		}

		model.setStarttime(model.getStarttime()+" 00:00:00");
		model.setEndtime(model.getEndtime()+" 23:59:59");

		List<StatisticsModel> models = statisticsService.queryByDayType(model);

		return  models;
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByToday")
	public StatisticsModel queryByToday(HttpServletRequest request) throws Exception{
		Map<String,Object> map=new HashMap<>();
		map.put("orgid",NumberUtils.toInt(request.getParameter("orgid")));
		return  statisticsService.queryByToday(map);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryWaitPerson")
	public List<Map<String,Object>> queryWaitPerson(Integer orgid) throws Exception{
		return  statisticsService.queryWaitPerson(orgid);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getEvaluate")
	public void getEvaluate(StatisticsPersonModel model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String starttime="";String endtime="";
		if(StringUtils.isBlank(model.getStarttime())){
			starttime= CommonUtil.nowDay();
		}

		if(StringUtils.isBlank(model.getEndtime())){
			endtime=CommonUtil.nowDay();
		}

		starttime=model.getStarttime() + " 00:00:00";
		endtime=model.getEndtime() + " 23:59:59";
		model.setStarttime(starttime);
		model.setEndtime(endtime);

		if(model.getLength()>-1){
			PageHelper.startPage(model.getPageno(), model.getLength());
		}

		List<Map<String,Object>> list =statisticsService.getEvaluate(model);
		PageInfo page = new PageInfo(list);

		Map<String,Object> jsonMap=new HashMap<>();

		jsonMap.put("success", true);
		jsonMap.put("data",list);
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());

		HtmlUtil.writerJson(response, jsonMap);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryWorkState")
	public List<Map<String,Object>> queryWorkState(WindowModel model) throws Exception{
		List<WindowModel> list=windowService.queryByListT(model);
		List<Map<String,Object>> wlist=new ArrayList<>();
		for(WindowModel mode1:list){
			Map<String,Object> map=getWinState(mode1.getGuid(),mode1.getName());
			if(map!=null){
				wlist.add(map);
			}else{
				map=new HashMap<>();
				map.put("winguid",mode1.getGuid());
				map.put("winname",mode1.getName());
				wlist.add(map);
			}
		}
		return  wlist;
	}

	private Map<String,Object> getWinState(String winguid,String winname){
		Map<String,Object> map=new HashMap<>();

		if(CharacterHelper.ALL_PC_USER!=null){
			//遍历MAP
			for(String m: CharacterHelper.ALL_PC_USER.keySet()){
				if(CharacterHelper.ALL_PC_USER.get(m).getWinid()==winguid){
					map.put("winguid",winguid);
					map.put("winname",winname);
					map.put("model",CharacterHelper.ALL_PC_USER.get(m).getState());
					map.put("name",CharacterHelper.ALL_PC_USER.get(m).getName());
					return map;
				}
			}
		}
		return null ;
	}

	/**
	 * 大厅大数据屏显示返回数据到第三方
	 * 获取当天取号数据
	 * */
	@Auth(verifyLogin = false)
	@RequestMapping("/queryStatisticsToday")
	public List<Map<String,Object>> queryStatisticsToday(StatisticsModel model){
		return statisticsService.queryStatisticsToday(model);
	}

	/**
	 * 大厅大数据屏显示返回数据到第三方
	 * 获取历史取号数据
	 * */
	@Auth(verifyLogin = false)
	@RequestMapping("/queryStatisticsHistory")
	public List<Map<String,Object>> queryStatisticsHistory(StatisticsModel model){
		if (StringUtils.isNotBlank(model.getStarttime())){
			model.setStarttime(model.getStarttime() + " 00:00:00");
		}

		if (StringUtils.isNotBlank(model.getEndtime())){
			model.setEndtime(model.getEndtime() + " 23:59:59");
		}
		return statisticsService.queryStatisticsHistory(model);
	}

}
