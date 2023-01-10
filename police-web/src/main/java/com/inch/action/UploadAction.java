package com.inch.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.annotation.Auth;
import com.inch.servlet.ImgTools;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.HtmlUtil;
 
@Controller
public class UploadAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(UploadAction.class);
	
	public static  String IMG_BASE_URL="";

	static {
        try {
        	PropertiesConfiguration jconfig=new PropertiesConfiguration("config.properties");
        		
        	IMG_BASE_URL=jconfig.getString("imgserver");
        	
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }
    }
	
	/**
	 * APP上传等
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/uploadFile.json") 
	public void  uploadFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 HtmlUtil.writerStr(response,saveUploadFile(request));
	}
	
	/**
	 * KinderEditor跨域处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/uploadKinderFile") 
	public String  uploadKinderFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		 String referer = request.getHeader("referer");
         Pattern p = Pattern.compile("([a-z]*:(//[^/?#]+)?)?", Pattern.CASE_INSENSITIVE);
         Matcher mathcer = p.matcher(referer);
         String callBackPath ="";
         if (mathcer.find()) {
             callBackPath = mathcer.group();// 请求来源
             
             String json=FastJsonUtils.toJson(saveUploadFile(request));
             String url = "redirect:" + callBackPath + "/screen-webn/kindeditor-4.1.10/plugins/image/redirect.html?s=" + json.toString() + "#" + json.toString();

             return url;
             
         }else{
        	 return null;
         }
	}
	
	/**
	 * 保存上传文件
	 * @param request
	 * @param response
	 * @return
	 */
	private String saveUploadFile(HttpServletRequest request){
		try{
	        SimpleDateFormat df_day = new SimpleDateFormat("yyyyMMdd");
	        String folder= df_day.format(new Date());
	        
	        String savePath = request.getSession().getServletContext().getRealPath("/") + "/Upload/"+folder+"/";
	        String saveUrl = request.getContextPath() + "/Upload/"+folder+"/";
	        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp","amr","wav","mp4"};
	        String[] imgTypes=new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
	        //最大文件大小
	        long maxSize = 100*1024*1024;//100MB
	        
	        if (!ServletFileUpload.isMultipartContent(request)) {
	        	return getErrorString("请选择文件。");
	        }
	        //检查目录
	        File uploadDir = new File(savePath);
	        if (!uploadDir.isDirectory()) {
	        	uploadDir.mkdirs(); 
	        }
	        //检查目录写权限
	        if (!uploadDir.canWrite()) {
	        	return getErrorString("上传目录没有写权限。");
	        }
	        
	        Map<String,String> map=new TreeMap<String,String>();
	        
	        FileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setHeaderEncoding("UTF-8");
	        List items = upload.parseRequest(request);
	        Iterator itr = items.iterator();
	        String newFileName_all = "";
	        
	        int maxW=0;
	        while (itr.hasNext()) {
	        
	            FileItem item = (FileItem) itr.next();
	            String fileName = item.getName();
	            if (!item.isFormField()) {
	                //检查文件大小
	            	
	                if (item.getSize() > maxSize) {
	                	return getErrorString("上传文件大小超过限制。");
	                }
	                //检查扩展名
	                String fileExt =StringUtils.substringAfterLast(fileName, ".").toLowerCase();
	                if (!Arrays.<String>asList(fileTypes).contains(fileExt)) {
	                	return getErrorString("上传文件扩展名是不允许的扩展名。");
	                }
	                
	                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	                String newFileName ="";//df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
	                
	                try {
	                	 String ispress=StringUtils.trimToEmpty(request.getParameter("ispress"));//是否压缩
	                	 String isthum=StringUtils.trimToEmpty(request.getParameter("thum"));//是否生成缩略图
	                	 String notkeeppic=StringUtils.trimToEmpty(request.getParameter("notkeeppic"));//是否生成缩略图
	                	 maxW=NumberUtils.toInt(request.getParameter("maxW"));
	                	 int maxH=NumberUtils.toInt(request.getParameter("maxH"));
	                	 
		                 String allStr="";
		                 
		               //上传的是图片
		                 if (Arrays.<String>asList(imgTypes).contains(fileExt)) {
		                	 fileExt="jpg";//图片统一格式jpg
	                	 }
		                 
		                 newFileName=df.format(new Date()) + "_" + new Random().nextInt(1000)+"."+fileExt;//+"_"
		                		 //+StringUtils.substringBeforeLast(item.getName(), ".") + "." + fileExt;
	                	
		                 //生成原始文件
	                	 File uploadedFile = new File(savePath, newFileName);
		                 item.write(uploadedFile);
		                 
		                 allStr+=IMG_BASE_URL+saveUrl+newFileName;
		                 
		                 //上传的是图片
		                 if (Arrays.<String>asList(imgTypes).contains(fileExt)) {
	                		 //如果宽度超过1920进行压缩
		                	 ImgTools.ImageCompressMax(uploadedFile, savePath+newFileName,1920,false);
		                	 
		                	 if(isthum.length()>0){
			                	   //一、生成缩略图
			                	   String smallImg = df.format(new Date()) + "_small_" + new Random().nextInt(1000) + "." + fileExt;
			                	   ImgTools.cutImage(uploadedFile, savePath+smallImg);
			                	   allStr+="@"+IMG_BASE_URL+saveUrl+smallImg;
			                 }
			                 
			                 if(ispress.length()>0){
			                	   
			                	   //二、生成压缩图片----压缩图--统一jpg（png格式文件比较大）
			                	   String presImg = df.format(new Date()) + "_mydisplay_" + new Random().nextInt(1000) + "." + fileExt;
				           			if(maxW>0&&maxH==0){
				        				//不判断横图竖图  直接以宽度为基准进行压缩
				           				ImgTools.ImageCompressMax(uploadedFile,savePath+presImg,maxW,true);
				        			}else{
				        				ImgTools.ImageCompress(uploadedFile, savePath+presImg,maxW,maxH);
				        			}
				                   //生成了新文件
				                   allStr+="@"+IMG_BASE_URL+saveUrl+presImg;
			                  }
	                	 }
		                 
		                 //不保存原图
		                 if(notkeeppic.length()>0){
		                	 allStr=StringUtils.substringAfter(allStr, "@");
		                	 uploadedFile.delete();
		                 }
	                   
		                 map.put(item.getFieldName(), allStr);
	                    
	                } catch (Exception e) {
	                	e.printStackTrace();
	                	return getErrorString("上传文件失败。");
	                }
	            }
	        }
	        
	        	//将接收到参数放在treep进行排序再加密-
			    for (String key : map.keySet()) { 
			    	 newFileName_all+=map.get(key)+",";
			    }
			 
	        	 newFileName_all=StringUtils.substringBeforeLast(newFileName_all, ",");
	        	 
//	        	 if(maxW==1080){
//	        		 newFileName_all=newFileName_all.replaceAll(IMG_BASE_URL, "");
//	        	 }
	        	 
	        	 Map<String, Object> obj=new HashMap<String, Object>();   
	             obj.put("error", 0);
	             obj.put("url",  newFileName_all);
	             //obj.put("data",  map);
	             
	             return FastJsonUtils.toJson(obj);
	            		 
	             
	             //HtmlUtil.writerWithOutJson(response,obj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "";
		
	}
	
	private void getError(HttpServletResponse response,String message) {
		
		Map<String, Object> obj=new HashMap<String, Object>();   
		 
		obj.put("error", 1);
		obj.put("message", message);
		
		HtmlUtil.writerJson(response,obj);
	}
	
	private String getErrorString(String message) {
		
		Map<String, Object> obj=new HashMap<String, Object>();   
		 
		obj.put("error", 1);
		obj.put("message", message);
		
		return FastJsonUtils.toJson(obj);
	}
}
