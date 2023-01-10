package com.inch.action;

import com.inch.annotation.Auth;
import com.inch.utils.BuidRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/hikvision")
public class TonyHikvision extends BaseAction{

    @RequestMapping("/downloadVideo")
    public void queryByPerson(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BuidRequest.sendRequest(request,response,"hikvision/downloadVideo","get");
    }

    @Auth(verifyLogin = false,verifyURL = false)
    @RequestMapping("/getChannelNum")
    public void getChannelNum(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BuidRequest.sendRequest(request,response,"hikvision/getChannelNum","get");
    }

}
