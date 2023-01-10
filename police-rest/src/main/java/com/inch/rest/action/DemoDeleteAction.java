
package com.inch.rest.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.model.GalleryModel;
import com.inch.model.Lcd_RelModel;
import com.inch.model.SysUser;
import com.inch.model.WelcomeModel;
import com.inch.rest.service.Lcd_RelService;
import com.inch.rest.service.WelcomeService;
import com.inch.rest.utils.RedisUtil;
import com.inch.rest.utils.SpringUtil;
import com.inch.utils.DBUtils;
import com.inch.utils.WebConstant;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
 
@Controller
@RequestMapping("/delDemo") 
public class DemoDeleteAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(DemoDeleteAction.class);
	
	
	@Autowired
	private WelcomeService<WelcomeModel> welcomeService; 
	
	@Autowired
	private Lcd_RelService<Lcd_RelModel> lcdRelService;
	
	@Autowired
	private TonyCommandService tonyService;

	@RequestMapping("/addSatire.json")
	public void  addSatire(GalleryModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+bean.getUsername());
		String flag=StringUtils.trimToEmpty(request.getParameter("flag"));
		
		ApplicationContext ac = SpringUtil.getApplicationContext();
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		PreparedStatement pst = null;
		String sql = "select * from gallery where adduser=? limit 1";

		ResultSet rs=null;
		
		GalleryModel mm=new GalleryModel();
			
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, suser.getId());
			rs=pst.executeQuery();
			
			if(rs!=null){
				while(rs.next()){
					mm.setGuid(rs.getString("guid"));
					mm.setClassid(rs.getString("classid"));
				}
			}
			
			pst.close();
			
			sql = "update gallery set endtime=? where adduser=? ";
			pst = conn.prepareStatement(sql);
			
			if("1".equals(flag)){
				pst.setString(1, "");
				mm.setEndtime("");
			}else{
				pst.setString(1, "2016-01-01 01:01:01");
				mm.setEndtime("2016-01-01 01:01:01");
			}
			
			pst.setInt(2, suser.getId());

			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs,pst, conn);
		}
		
		//发送给屏幕端
		tonyService.sendCommandToServer(Command.COMMAND_GALLERY, mm,mm.getClassid()+"");
	}
	
	
	@RequestMapping("/demoLesson.json") 
	public void  demoLesson(GalleryModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_LESSION_CHANGE, bean,bean.getClassid()+"");
	}
	
	@RequestMapping("/shutDownTv.json") 
	public void  shutDownTv(GalleryModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_SHUTDOWN_TV, bean,bean.getClassid()+"");
	}
	
	@RequestMapping("/delWelCome.json")
	public void  delWelCome(WelcomeModel model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		ApplicationContext ac = SpringUtil.getApplicationContext();
		
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		PreparedStatement pst = null;
		String sql = "delete from welcome where adduser=?";

		try {
			pst = conn.prepareStatement(sql);
			
			pst.setInt(1, suser.getId());

			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pst, conn);
		}
		sendSuccessMessage(response, "删除成功");
		
		//发送给屏幕端------进行初始化
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_DEL_WELCOME, "",model.getClassid()+"");
	}
	
	
	@RequestMapping("/delNotice.json")
	public void  delNotice(WelcomeModel model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		ApplicationContext ac = SpringUtil.getApplicationContext();
		
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		PreparedStatement pst = null;
		String sql = "delete from notice where adduser=? and date(addtime)= curdate() ";

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, suser.getId());
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pst, conn);
		}
		sendSuccessMessage(response, "删除成功");
		
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_DEL_NOTICE, "",model.getClassid()+"");
	}
	
	@RequestMapping("/delVideo.json")
	public void  delVideo(WelcomeModel model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		ApplicationContext ac = SpringUtil.getApplicationContext();
		
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		PreparedStatement pst = null;
		String sql = "delete from video where adduser=?";

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, suser.getId());
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pst, conn);
		}
		sendSuccessMessage(response, "删除成功");
		
		//发送给屏幕端------进行初始化
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_DEL_VIDEO, "",model.getClassid()+"");
	}
	

	@RequestMapping("/stopPlayVideo.json") 
	public void  stopPlayVideo(WelcomeModel model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_STOP_PLAY_VIDEO, "",model.getClassid()+"");
	}

	@RequestMapping("/stopPushVideo.json") 
	public void  stopPushVideo(WelcomeModel model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_STOP_PUSH_VIDEO, "",model.getClassid()+"");
	}
	

	
	
	@RequestMapping("/delHomeWork.json")
	public void  delHomeWork(WelcomeModel model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		ApplicationContext ac = SpringUtil.getApplicationContext();
		
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		PreparedStatement pst = null;
		String sql = "delete from homework where adduser=? and date(addtime)= curdate() ";

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, suser.getId());
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pst, conn);
		}
		sendSuccessMessage(response, "删除成功");
		
		//发送给屏幕端------进行初始化
		tonyService.sendCommandToServer(Command.COMMAND_DEMO_DEL_HOMEWORK, "",model.getClassid()+"");
	}
	
	@RequestMapping("/pressImage.json") 
	public void  pressImage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
//		
//		ApplicationContext ac = SpringUtil.getApplicationContext();
//		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
//		SqlSession session = sqlSessionFactory.openSession();
//		Connection conn = session.getConnection();
//		PreparedStatement pst = null;
//		String sql = "select g.pic from gallery_photo g"
//		+" inner join gallery g2 on g.pguid=g2.guid"
//		+" inner JOIN school s1 on g2.classid=s1.id"
//		+" inner join school s2 on  s1.pid=s2.id"
//		+" where s2.pid=77";
//
//		ResultSet rs=null;
//		
////		GalleryModel mm=new GalleryModel();
//		
//		String picString="";
//			
//		try {
//			pst = conn.prepareStatement(sql);
//			rs=pst.executeQuery();
//			
//			if(rs!=null){
//				while(rs.next()){
//					picString=StringUtils.trimToEmpty(rs.getString("pic"));
//					//http://58.211.71.39:9208/display-web/Upload/20160914/20160914125108_218.jpg
//					
//					String newFilename=StringUtils.substringAfterLast(picString, "/");
//					
//					newFilename="press_"+newFilename;
//					
//					File sssFile=new File(picString.replace("http://58.211.71.39:9208/display-web", "c:"));
//					
//					if(!sssFile.exists()){
//						System.out.println("-------------------"+newFilename);
//					}
//					
//					InputStream inputStream = new ByteArrayInputStream(ImageCompressUtil3.getBytes(picString.replace("http://58.211.71.39:9208/display-web", "c:")));
//					
//					
//					Boolean flag= ImageCompressUtil3.compressImage(inputStream,0.9f,"c:\\pressimg\\"+newFilename);
////					ImageCompressUtil3
//				}
//			}
//			
//			pst.close();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtils.close(rs,pst, conn);
//		}
//		
//		System.out.println("------------------完成-----------------------");
	}
	
	@RequestMapping("/checkImage.json") 
	public void  checkImage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		
		ApplicationContext ac = SpringUtil.getApplicationContext();
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		PreparedStatement pst = null;
		String sql = "select g.pic from gallery_photo g"
		+" inner join gallery g2 on g.pguid=g2.guid"
		+" inner JOIN school s1 on g2.classid=s1.id"
		+" inner join school s2 on  s1.pid=s2.id"
		+" where s2.pid=77";

		ResultSet rs=null;
		
//		GalleryModel mm=new GalleryModel();
		
		String picString="";
			
		try {
			pst = conn.prepareStatement(sql);
			rs=pst.executeQuery();
			
			if(rs!=null){
				while(rs.next()){
					picString=StringUtils.trimToEmpty(rs.getString("pic"));
				}
			}
			
			pst.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs,pst, conn);
		}
		
		System.out.println("------------------完成-----------------------");
	}
	
	@RequestMapping("/updateMode.json")
	public void  updateMode(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
//		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		String model=StringUtils.trimToEmpty(request.getParameter("model"));
		String classid=StringUtils.trimToEmpty(request.getParameter("classid"));
		
		ApplicationContext ac = SpringUtil.getApplicationContext();
		
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		PreparedStatement pst = null;
		String sql = "update lcdlist set model =? where classid=?";

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, NumberUtils.toInt(model));
			pst.setInt(2, NumberUtils.toInt(classid));
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pst, conn);
		}
		
		
		List<String> list=lcdRelService.getLcdSn(classid);
		
		for (int i = 0; i < list.size(); i++) {
			
			tonyService.sendCommandToDevice(Command.COMMAND_MODEL, model,list.get(i));
			
			RedisUtil.del(WebConstant.SCREENUSER+list.get(i));
		}
	}
	
	@RequestMapping("/teachSign.json") 
	public void  teachSign(WelcomeModel model,HttpServletRequest request,HttpServletResponse response,String sn) throws Exception {
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		Map<String, String> map=new HashMap<String, String>();
		map.put("name", suser.getName());
		
		sendSuccessMessage(response, "发送成功");
		
		tonyService.sendCommandToDevice(Command.COMMAND_SEND_TECH_SIGN, map,sn);
	}
	
}
