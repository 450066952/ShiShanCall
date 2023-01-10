
package com.inch.rest.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.interceptor.Auth;
import com.inch.model.LcdModel;
import com.inch.rest.service.GetInfoBySNService;
import com.inch.rest.service.LcdService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.WebConstant;
 
@Controller
@RequestMapping("/lcdset") 
public class LcdSetAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(LcdSetAction.class);
	
	@Autowired
	private LcdService<LcdModel> lcdService; 
	
	@Autowired
	private GetInfoBySNService getInfoBySNService;
	
	
	/**------lcd 屏显客户端----注册设备
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@Auth(verifyLogin=false)
	@RequestMapping("/registlcd")
	public void addlcd(LcdModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		int ret=0;
		
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			sendFailureMessage(response, "序列号不能为空");
			return;
		}
		
		LcdModel qModel=lcdService.queryById(bean.getGuid());
		
		if(qModel!=null){
			sendFailureMessage(response, "该序列号已注册！");
			return;
		}
		
		ret=lcdService.add(bean);
		
		if(ret>0){
			sendSuccessMessage(response, "注册成功~");
		}else{
			sendFailureMessage(response, "注册失败~");
		}
	}
	
	/**------lcd 屏显客户端----注册年级班级
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@Auth(verifyLogin=false)
	@RequestMapping("/registClass")
	public void regestClass(LcdModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		int ret=0;
		
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			sendFailureMessage(response, "序列号不能为空");
			return;
		}
		
		//判断该班级是否已经设置了屏幕
//		LcdModel lmodel=lcdService.queryByClass(bean);
//
//		if(lmodel!=null){
//
//			sendFailureMessage(response, "该班级已经存在屏显设备~");
//
//			return;
//		}
		
		
		ret=lcdService.regestClass(bean);
		
		if(ret>0){
			RedisUtil.del(WebConstant.SCREENUSER+bean.getGuid());//删除redis缓存
			sendSuccessMessage(response, "设置成功~");
		}else{
			sendFailureMessage(response, "设置失败~");
		}
	}
	
	/**------根据guid去获取---年级--班级信息
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@Auth(verifyLogin=false)
	@RequestMapping("/getClassInfo")
	public void getClassInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, String> map=new HashMap<String, String>();
		
		map.put("guid", StringUtils.trimToEmpty(request.getParameter("guid")));
		map.put("pid", StringUtils.trimToEmpty(request.getParameter("pid")));
		
		List<Map> list=getInfoBySNService.getClassBySN(map);
		
		sendSuccessData(response,list);
	}
	
	
	
}
