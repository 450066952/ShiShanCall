
package com.inch.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inch.annotation.Auth;
import com.inch.model.GalleryPhotoModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.TonyResult;
 
@Controller
@RequestMapping("/picPhoto") 
public class PicPhotoAction extends BaseAction{

	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/list") 
	public ModelAndView  list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		
		//从rest服务去获取数据
		String json=BuidRequest.getRequestResult(request,response,"picPhoto/getQueryList.json","get");
		TonyResult taotaoResult = TonyResult.formatToList(json, GalleryPhotoModel.class);
		context.put("piclist", taotaoResult.getData());
		
		return forword("pic/picphoto",context); 
	}

	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,String[] classid) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"picPhoto/save.json","post");
	}
	
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/getId")
	public void getId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"picPhoto/getId.json","post");
	}
	
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"picPhoto/delete.json","post");
	}
	
}
