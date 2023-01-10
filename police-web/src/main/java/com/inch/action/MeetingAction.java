
package com.inch.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.annotation.Auth;
import com.inch.utils.DBUtils;
import com.inch.utils.HtmlUtil;
import com.inch.utils.SpringUtil;
 
@Controller
@RequestMapping("/meeting") 
public class MeetingAction extends BaseAction{

	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/signup") 
	public void  list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ApplicationContext ac = SpringUtil.getApplicationContext();
		SqlSessionFactory mSqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
		SqlSession session = mSqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pst1 = null;
		String sql = " insert into meeting (name,phone,num) values (?,?,?) ";

		try {
			pst1 = conn.prepareStatement(sql);
			pst1.setString(1, StringUtils.trim(request.getParameter("cname"))); 
			pst1.setString(2, StringUtils.trim(request.getParameter("phone"))); 
			pst1.setString(3, StringUtils.trim(request.getParameter("peoplenumber"))); 
			
			pst1.executeUpdate();
			
			conn.commit();
		}catch(Exception e){
			DBUtils.rollback(conn);
			e.printStackTrace();
			this.sendFailureMessage(response, "报名失败，请重试~");
		}finally
		{
			DBUtils.close(pst1, conn);
		}
		
		this.sendSuccessMessage(response, "报名成功，请如约赴会！");
	}
	
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/logint") 
	public void  logint(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("istrue", 1);
		HtmlUtil.writerJson(response, map);
	}
	
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/logint2") 
	public void  logint2(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("istrue", 1);
		HtmlUtil.writerJson(response, map);
	}
	
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/logint3") 
	public void  logint3(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("istrue", 1);
		HtmlUtil.writerJson(response, map);
	}
}
