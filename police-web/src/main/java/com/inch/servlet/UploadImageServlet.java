package com.inch.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.inch.utils.HtmlUtil;

public class UploadImageServlet extends HttpServlet {
	
	
	public static  String IMG_BASE_URL="";

	static {
        try {
        	PropertiesConfiguration jconfig=new PropertiesConfiguration("config.properties");
        		
        	IMG_BASE_URL=jconfig.getString("imgserver");
        	
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }
    }
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			
			response.setContentType("text/html; charset=UTF-8");
	        
	        SimpleDateFormat df_day = new SimpleDateFormat("yyyyMMdd");
	        String folder= df_day.format(new Date());
	        
	        String savePath = request.getSession().getServletContext().getRealPath("/") + "/Upload/"+folder+"/";
	        String saveUrl = request.getContextPath() + "/Upload/"+folder+"/";
	        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp","amr","wav","mp4","xls","xlsx"};
	        String[] imgTypes=new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
	        //??????????????????
	        long maxSize = 2000*1024*1024;//2000MB
	        
	        if (!ServletFileUpload.isMultipartContent(request)) {
	        	getError(response,"??????????????????");
	            return;
	        }
	        //????????????
	        File uploadDir = new File(savePath);
	        if (!uploadDir.isDirectory()) {
	        	uploadDir.mkdirs(); 
	        }
	        //?????????????????????
	        if (!uploadDir.canWrite()) {
	        	getError(response,"??????????????????????????????");
	            return;
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
	                //??????????????????
	            	
	                if (item.getSize() > maxSize) {
	                	getError(response,"?????????????????????????????????");
	                    return;

	                }
	                //???????????????
	                String fileExt =StringUtils.substringAfterLast(fileName, ".").toLowerCase();
	                if (!Arrays.<String>asList(fileTypes).contains(fileExt)) {
	                	getError(response,"????????????????????????????????????????????????");
	                    return;
	                }
	                
	                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	                String newFileName ="";//df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
	                
	                try {
	                	 String ispress=StringUtils.trimToEmpty(request.getParameter("ispress"));//????????????
	                	 String isthum=StringUtils.trimToEmpty(request.getParameter("thum"));//?????????????????????
	                	 String notkeeppic=StringUtils.trimToEmpty(request.getParameter("notkeeppic"));//?????????????????????
	                	 maxW=NumberUtils.toInt(request.getParameter("maxW"));
	                	 int maxH=NumberUtils.toInt(request.getParameter("maxH"));
	                	 
		                 String allStr="";
		                 
		               //??????????????????
		                 if (Arrays.<String>asList(imgTypes).contains(fileExt)) {
		                	 fileExt="jpg";//??????????????????jpg
	                	 }
		                 
		                 newFileName=df.format(new Date()) + "_" + new Random().nextInt(1000)+"."+fileExt;//+"_"
		                		 //+StringUtils.substringBeforeLast(item.getName(), ".") + "." + fileExt;
	                	
		                 //??????????????????
	                	 File uploadedFile = new File(savePath, newFileName);
		                 item.write(uploadedFile);
		                 
		                 allStr+=IMG_BASE_URL+saveUrl+newFileName;
		                 
		                 //??????????????????
		                 if (Arrays.<String>asList(imgTypes).contains(fileExt)) {
	                		 //??????????????????1920????????????
		                	 ImgTools.ImageCompressMax(uploadedFile, savePath+newFileName,1920,false);
		                	 
		                	 if(isthum.length()>0){
			                	   //?????????????????????
			                	   String smallImg = df.format(new Date()) + "_small_" + new Random().nextInt(1000) + "." + fileExt;
			                	   ImgTools.cutImage(uploadedFile, savePath+smallImg);
			                	   allStr+="@"+IMG_BASE_URL+saveUrl+smallImg;
			                 }
			                 
			                 if(ispress.length()>0){
			                	   
			                	   //????????????????????????----?????????--??????jpg???png????????????????????????
			                	   String presImg = df.format(new Date()) + "_mydisplay_" + new Random().nextInt(1000) + "." + fileExt;
				           			if(maxW>0&&maxH==0){
				        				//?????????????????????  ????????????????????????????????????
				           				ImgTools.ImageCompressMax(uploadedFile,savePath+presImg,maxW,true);
				        			}else{
				        				ImgTools.ImageCompress(uploadedFile, savePath+presImg,maxW,maxH);
				        			}
				                   //??????????????????
				                   allStr+="@"+IMG_BASE_URL+saveUrl+presImg;
			                  }
	                	 }
		                 
		                 //???????????????
		                 if(notkeeppic.length()>0){
		                	 allStr=StringUtils.substringAfter(allStr, "@");
		                	 uploadedFile.delete();
		                 }
	                   
		                 map.put(item.getFieldName(), allStr);
	                    
	                } catch (Exception e) {
	                	e.printStackTrace();
	                	getError(response,"?????????????????????");
	                    return;

	                }
	            }
	        }
	        
	        	//????????????????????????treep?????????????????????-
			    for (String key : map.keySet()) { 
			    	 newFileName_all+=map.get(key)+",";
			    }
			 
	        	 newFileName_all=StringUtils.substringBeforeLast(newFileName_all, ",");
	        	 
	        	 if(maxW==1080){
	        		 newFileName_all=newFileName_all.replaceAll(IMG_BASE_URL, "");
	        	 }
	        	 
	        	 Map<String, Object> obj=new HashMap<String, Object>();   
	             obj.put("error", 0);
	             obj.put("url",  newFileName_all);
	             obj.put("data",  map);
	             
	             HtmlUtil.writerWithOutJson(response,obj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
public static void main(String[] args) {
	String aaaString="11111@2222@$$$$$$";
	System.out.println(StringUtils.substringAfter(aaaString,"@"));
}

	private void getError(HttpServletResponse response,String message) {
		Map<String, Object> obj=new HashMap<String, Object>();   
		obj.put("error", 1);
		obj.put("message", message);
		
		HtmlUtil.writerJson(response,obj);
//		return obj.toString().toJSONString();
	}
	
	public void init() throws ServletException {
		// Put your code here
	}

}
