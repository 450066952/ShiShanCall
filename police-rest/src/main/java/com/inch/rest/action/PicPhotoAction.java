
package com.inch.rest.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.model.GalleryModel;
import com.inch.model.GalleryPhotoModel;
import com.inch.model.SysUser;
import com.inch.rest.service.PicPhotoService;
import com.inch.rest.service.PicService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.DateUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
 
@Controller
@RequestMapping("/picPhoto") 
public class PicPhotoAction extends BaseAction{
	
	@Autowired
	private PicPhotoService<GalleryPhotoModel> picPhotoService; 
	
	@Autowired
	private PicService<GalleryModel> picService; 
	
	@Autowired
	private TonyCommandService tonyService;
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@RequestMapping("/getQueryList.json") 
	public void  list(GalleryPhotoModel model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String, Object>();

		List<GalleryPhotoModel> dataList = picPhotoService.queryByListT(model);
		
		context.put(SUCCESS, true);
		context.put(MSG, "OK");
		context.put("data", dataList);
		HtmlUtil.writerJson(response, context);
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save.json")
	public void save(GalleryPhotoModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		int ret=0;
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+bean.getUsername());
		bean.setAdduser(suser.getId());
		
		String[] pic=null;
		pic=StringUtils.trimToEmpty(bean.getPic()).split(",");
		
		
		if(pic!=null&&pic.length>0){
			String s="";
			//获取宣传册主题信息
			GalleryModel gmodel= picService.queryById(bean.getPguid()); 
			
			for (int i=0;i<pic.length;i++) {
				s=pic[i];
				if(s.length()>0){
					
					bean.setPic(s);
					
					bean.setGuid(UUID.randomUUID().toString());
					ret+=picPhotoService.add(bean);
					
					if(ret>0){
						
						bean.setAddtime(DateUtil.getNowPlusTime());
						bean.setPic(bean.getPic());
						bean.setBegintime(gmodel.getBegintime());
						bean.setEndtime(gmodel.getEndtime());
						bean.setClassid(gmodel.getClassid());
						
						
						//发送给屏幕端
						tonyService.sendCommandToServer(Command.COMMAND_PIC_PHOTO, bean,bean.getClassid());
					}
				}
			}
			

			if(ret>0){
				sendSuccessMessage(response, "保存成功~");
			}else{
				sendFailureMessage(response, "保存失败");
			}
			
		}else{
			sendFailureMessage(response, "请选择图片");
		}
	}
	
	@RequestMapping("/getId.json")
	public void getId(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		GalleryPhotoModel bean  = picPhotoService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/delete.json")
	public void delete(String id,HttpServletResponse response) throws Exception{
		
		//发送给屏幕端
	    tonyService.sendDeleteToServer(Command.COMMAND_DEL_PIC_PHOTO,id);
	    
	    int ret =picPhotoService.deleteBactch(id.split(","));
		
		if(ret>0){
			sendSuccessMessage(response, "删除成功");
		}else{
			this.sendFailureMessage(response, "删除失败");
		}
	}
}
