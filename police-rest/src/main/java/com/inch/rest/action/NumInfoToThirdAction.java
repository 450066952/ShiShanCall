package com.inch.rest.action;

import com.inch.interceptor.Auth;
import com.inch.rest.service.NumInfoToThirdService;
import com.inch.utils.CommonUtil;
import com.inch.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @Date 2022-01-18
 * @Note: 第三方获取用户取号信息
 */
@Controller
@RequestMapping("/queryNumInfo")
public class NumInfoToThirdAction extends BaseAction{

    @Autowired
    private NumInfoToThirdService service;

    @Auth(verifyLogin = false)
    @RequestMapping("/queryInfo")
    public void queryNumInfo(int orgid, String startTime, String endTime, HttpServletResponse response){

        if (orgid == 0){
            sendFailureMessage(response,"orgid不能为空！");
        }

        if(StringUtils.isBlank(startTime)){
            startTime = CommonUtil.nowDay() + " 00:00:00";
        }

        if(StringUtils.isBlank(endTime)){
            endTime = CommonUtil.nowDay() + " 23:59:59";
        }

        List<Map> mapList = service.queryNumInfo(orgid,startTime,endTime);

        HtmlUtil.writerJson(response,mapList);
    }
}
