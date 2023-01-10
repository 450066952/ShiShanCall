
package com.inch.rest.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.model.GalleryModel;
import com.inch.model.GalleryPhotoModel;
import com.inch.model.Lcd_RelModel;
import com.inch.model.SysUser;
import com.inch.rest.service.Lcd_RelService;
import com.inch.rest.service.PicPhotoService;
import com.inch.rest.service.PicService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.CommonUtil;
import com.inch.utils.DateUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
 
@Controller
@RequestMapping("/pic") 
public class PicAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(PicAction.class);
	
	@Autowired
	private PicService<GalleryModel> picService; 
	
	@Autowired
	private PicPhotoService<GalleryPhotoModel> picPhotoService; 
	
	@Autowired
	private TonyCommandService tonyService;
	
	@Autowired
	private Lcd_RelService<Lcd_RelModel> lcdRelService;
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@RequestMapping("/getQueryList.json") 
	public void  list(GalleryModel model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		if(StringUtils.trimToEmpty(model.getClassid()).length()==0){
			if(SuperAdmin.YES.key ==  suser.getIsadmin()){//超级管理员
				model.setIsadmin(SuperAdmin.YES.key);
			}else{
				model.setIsadmin(SuperAdmin.NO.key);
				model.setClassid(suser.getSchoolids());
			}
		}
		HtmlUtil.writerJson(response, picService.queryByList(model));
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save.json")
	public void save(GalleryModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		int ret=0;
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+bean.getUsername());
		bean.setAdduser(suser.getId());
		
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			bean.setGuid(UUID.randomUUID().toString());
			ret=picService.add(bean);
		}else{
			ret=picService.update(bean);
			
			if(ret>0){
				bean.setAddtime(DateUtil.getNowPlusTime());
				//发送给屏幕端
				tonyService.sendCommandToServer(Command.COMMAND_PIC, bean,bean.getClassid()+"");
			}
			
			//判断哪个班级是新增的，新增的要把里面的图片给发过去
			
			String addstr=CommonUtil.compareAdd(bean.getClassid(), bean.getOldclassid());
			if(addstr.length()>0){
				String s="";
				String[] pic=null;
				pic=addstr.split(",");
				
				GalleryPhotoModel ssModel=new GalleryPhotoModel(); 
				ssModel.setPguid(bean.getGuid());
				List<GalleryPhotoModel> dataList = picPhotoService.queryByListT(ssModel);
				
				//获取宣传册主题信息
				GalleryModel gmodel= picService.queryById(bean.getGuid()); 
				for (int i=0;i<pic.length;i++) {
					s=pic[i];
					if(s.length()>0){
						
						for(int j=0;j<dataList.size();j++){
							ssModel=dataList.get(j);
							
							ssModel.setBegintime(gmodel.getBegintime());
							ssModel.setEndtime(gmodel.getEndtime());
							ssModel.setClassid(gmodel.getClassid());
							
							//发送给屏幕端
							tonyService.sendCommandToServer(Command.COMMAND_PIC_PHOTO, ssModel,s);
						}
					}
				}
			}
			
			
			
			
			String delStr=CommonUtil.compare(bean.getClassid(), bean.getOldclassid());
			if(delStr.length()>0){
				tonyService.sendCommandToServer(Command.COMMAND_DEL_PIC_ALL, bean.getGuid(),delStr);
			}
		}
		
		if(ret>0){
			
			//显示屏的对应关系
			lcdRelService.addLcdRel(bean.getGuid(), bean.getClassid());
			
			sendSuccessMessage(response, "保存成功~");
		}else{
			sendFailureMessage(response, "保存失败");
		}
	}
	
	@RequestMapping("/getId.json")
	public void getId(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		GalleryModel bean  = picService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		if(bean.getClassid()==null){
			bean.setClassid("");
		}
		
		bean.setClassids(bean.getClassid().split(","));
		bean.setOldclassid(bean.getClassid());
		
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/delete.json")
	public void delete(String id,HttpServletResponse response) throws Exception{
		
		//获取选中相册下的所有照片
		List<String> photoList=picService.getChildPhoto(id);
		String str="";
		for (String s : photoList) {
			str+=s+",";
		}
		
		if(str.length()>0){
		    tonyService.sendDeleteToServer(Command.COMMAND_DEL_PIC_PHOTO,str);
		}
		
	    int ret =picService.deleteBactch(id.split(","));
		
		if(ret>0){
			sendSuccessMessage(response, "删除成功");
		}else{
			this.sendFailureMessage(response, "删除失败");
		}
	}
	
}
