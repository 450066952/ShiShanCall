
package com.inch.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.annotation.Auth;
import com.inch.model.StatisticsModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.CommonUtil;
import com.inch.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsAction {


	@Auth(verifyLogin = false)
	@RequestMapping("/queryByPerson")
	public void queryByPerson(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"statistics/queryByPerson","get");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByDayTotal")
	public void queryByDayTotal(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"statistics/queryByDayTotal","get");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByDayType")
	public void queryByDayType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"statistics/queryByDayType","get");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByToday")
	public void queryByToday(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"statistics/queryByToday","get");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryWaitPerson")
	public void queryWaitPerson(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"statistics/queryWaitPerson","get");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getEvaluate")
	public void getEvaluate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"statistics/getEvaluate","get");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryWorkState")
	public void queryWorkState(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"statistics/queryWorkState","get");
	}


}
