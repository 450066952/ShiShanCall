
package com.inch.rest.action.map;

import com.inch.interceptor.Auth;
import com.inch.model.StatisticsPersonModel;
import com.inch.rest.service.MapStatisticsService;
import com.inch.utils.CommonUtil;
import com.inch.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mapStatistics")
public class MapAction {

	@Autowired
	private MapStatisticsService statisticsService;

	@Auth(verifyLogin = false)
	@RequestMapping("/queryByOrg")
	public void queryByOrg(StatisticsPersonModel model,HttpServletRequest request, HttpServletResponse response) throws Exception{

		if(StringUtils.isBlank(model.getStarttime())){
			model.setStarttime(getMonthFDay());
		}

		if(StringUtils.isBlank(model.getEndtime())){
			model.setEndtime(CommonUtil.nowDay());
		}

		int bdays=caculateTotalTime(model.getStarttime(),model.getEndtime());

		//计算同比日期天数
		String pSdate=getDateByCnt(model.getStarttime(),bdays);
		String pEdate=getDateByCnt(model.getEndtime(),bdays);

		Map<String,Object> map=new HashMap<>();
		map.put("starttime",model.getStarttime() + " 00:00:00");
		map.put("endtime",model.getEndtime() + " 23:59:59");
		map.put("pSdate",pSdate + " 00:00:00");
		map.put("pEdate",pEdate + " 23:59:59");
		map.put("orgid",NumberUtils.toInt(request.getParameter("orgid")));

		List<Map> list =statisticsService.queryByORG(map);
		Map<String,Object> jsonMap=new HashMap<>();
		jsonMap.put("success", true);
		jsonMap.put("data",list);
		HtmlUtil.writerJson(response, jsonMap);
	}


	private String getMonthFDay(){

		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String firstday;
			// 获取前月的第一天
			Calendar cale = Calendar.getInstance();
			cale.add(Calendar.MONTH, 0);
			cale.set(Calendar.DAY_OF_MONTH, 1);
			firstday = format.format(cale.getTime());
			return firstday;
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}
	}

	private static String getDateByCnt(String startTime,int daycnt) {

		try{

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = format.parse(startTime);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d1);

			calendar.add(Calendar.DATE, daycnt);
			Date updateDate4 = calendar.getTime();
			return format.format(updateDate4);
		}catch (Exception e){
			return "";
		}
	}

	public static int caculateTotalTime(String startTime,String endTime) {

		SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy-MM-dd");
		Date date1;
		Date date ;
		Long l = 0L;
		try {

			date = formatter.parse(startTime);
			long ts = date.getTime();
			date1 =  formatter.parse(endTime);
			long ts1 = date1.getTime();

			l = (ts - ts1) / (1000 * 60 * 60 * 24);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return l.intValue()-1;//减一是为了包含当天的数据
	}

	public static void main(String[] args) {
//		System.out.println(caculateTotalTime("2020-01-01","2020-01-08"));
		System.out.println(getDateByCnt("2020-01-01",-8));
		System.out.println(getDateByCnt("2020-01-08",-8));
	}

}
