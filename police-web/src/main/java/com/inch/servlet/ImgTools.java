package com.inch.servlet;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.lang.StringUtils;

import com.inch.utils.CopyFileUtil;




public class ImgTools {

	public static void main(String[] args) {
		
		
		File dir=new File("c:\\testimg");
		
		File[]  files=dir.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			ImageCompress(files[i],"c:\\testimg2\\press_"
					+StringUtils.substringBeforeLast(files[i].getName(), ".")+"."+StringUtils.substringAfterLast(files[i].getName(), ".")
					,519,389);
		}
		
		
		try {
			
//			ImageCompress(fromimg,"c:\\testimg\\888888.jpg");
			
//			test(fromimg, toPic);
//			test2(fromimg, toPic);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	/**
	 * 图片压缩
	 * @param fromPic
	 * @param path
	 * @throws Exception
	 */
	public static void ImageCompress(File fromPic,String path,int basewidth,int baseheight){
		
		try {
			BufferedImage image = ImageIO.read(fromPic);
			int imageWidth = image.getWidth();
			int imageHeitht = image.getHeight();
	    	int target_width=0;
	    	int target_height=0;
			
			if(basewidth==0&&baseheight==0){
				basewidth=519;
				baseheight=389;
			}
			
//			int basewidth=519;
//	    	int baseheight=389;
	    	

	    	
	    	if(imageWidth>imageHeitht){
	    		if(imageWidth>basewidth){
	    			//横图--宽度519  (int) (ori_height * w / ori_width);
	    			target_width=basewidth;
	    			target_height=(int) (basewidth * imageHeitht / imageWidth);
	    		}else{
	    			//不压缩---复制
	    			CopyFileUtil.copyFile(fromPic, path, true);
	    			return;
	    		}
	    	}else{
	    		if(imageHeitht>baseheight){
	    			//竖图
	    			target_height=baseheight;
	    			target_width=(int) (baseheight * imageWidth / imageHeitht);
	    		}else{
	    			//不压缩
	    			CopyFileUtil.copyFile(fromPic, path, true);
	    			return;
	    		}
	    	}
	    	
			Thumbnails.of(image).size(target_width, target_height).outputQuality(0.9f)
			.outputFormat(StringUtils.substringAfterLast(path, ".")).toFile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 大于1920直接压缩
	 * @param fromPic
	 * @param path
	 * @param basewidth
	 * @param baseheight
	 */
	public static void ImageCompressMax(File fromPic,String path,int maxWidth,Boolean iscopy){
		
		try {
			BufferedImage image = ImageIO.read(fromPic);
			int imageWidth = image.getWidth();
			int imageHeitht = image.getHeight();
			
			//int maxWidth=1920;
			
			int target_width=0;
	    	int target_height=0;
			
			if(imageWidth>maxWidth){
				target_width=maxWidth;
    			target_height=(int) (maxWidth * imageHeitht / imageWidth);
    			
    			Thumbnails.of(image).size(target_width, target_height).outputQuality(0.9f)
    			.outputFormat(StringUtils.substringAfterLast(path, ".")).toFile(path);
			}else{
				if(iscopy){
					CopyFileUtil.copyFile(fromPic, path, true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 生成300*300的缩略图--------多余的进行剪裁
	 * @param fromPic
	 * @param toPic
	 * @throws Exception
	 */
	public static void cutImage(File fromPic ,String  toPic) {
		
		try{
			
			BufferedImage image = ImageIO.read(fromPic);
			Builder<BufferedImage> builder = null;

			int imageWidth = image.getWidth();
			int imageHeitht = image.getHeight();
			
			if (imageWidth > imageHeitht) {
				image = Thumbnails.of(fromPic).height(300).asBufferedImage();
			} else {
				image = Thumbnails.of(fromPic).width(300).asBufferedImage();
			}
			
			builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, 300, 300).size(300, 300);

			builder.outputFormat(StringUtils.substringAfterLast(toPic, ".")).toFile(toPic);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
