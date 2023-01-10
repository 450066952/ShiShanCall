package com.inch.service;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.inch.utils.DBUtils;
import com.inch.utils.SpringUtil;

/**
 * 
 * @Title: StudentService.java
 * @Package com.tony.service
 * @Description: TODO
 * @author tony
 * @@@
 * @date 2016年5月19日
 */
@Service("examService")
public class ExamService {

	private static final Logger logger = Logger.getLogger(ExamService.class);

	public Boolean importScore(String fileName,String examname,int classid,int adduser) {
		
		int totalcnt=0;
		
		try {
			Workbook workBook = null;
			
			try {
				workBook = new XSSFWorkbook(fileName);
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(new FileInputStream(fileName));
			}

			ApplicationContext ac = SpringUtil.getApplicationContext();
			SqlSessionFactory mSqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
			SqlSession session = mSqlSessionFactory.openSession();
			Connection conn = session.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pst1 = null;
			PreparedStatement pst = null;
			String sql = " insert into exam (guid,examname,classid,adduser) values (?,?,?,?) ";

			try {
				
				
				String pguid=UUID.randomUUID().toString();
				
				pst1 = conn.prepareStatement(sql);
				pst1.setString(1, pguid);// 主键
				pst1.setString(2, examname);// 父节点
				pst1.setInt(3, classid);//班级id
				pst1.setInt(4, adduser);//添加人
				
				pst1.executeUpdate();
				
				sql = " insert into exam_detial(guid,pguid,name,score) values (?,?,?,?) ";
				
				pst=conn.prepareStatement(sql);

				for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
					Sheet sheet = workBook.getSheetAt(numSheet);
					if (sheet == null) {
						continue;
					}
					// 循环行Row
					for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
						Row row = sheet.getRow(rowNum);
						if (row == null) {
							continue;
						}
						
						if(StringUtils.trimToEmpty(row.getCell(1).getStringCellValue()).length()==0){
							continue;
						}
						
						pst.setString(1, UUID.randomUUID().toString());// 主键
						pst.setString(2, pguid);// 父节点
						pst.setString(3, StringUtils.trimToEmpty(row.getCell(0)
								.getStringCellValue()));// 姓名
						pst.setString(4, StringUtils.trimToEmpty(row.getCell(1)
								.getStringCellValue()));//考试成绩
						
						pst.addBatch();
						
						if (numSheet % 100 == 0) {
							pst.executeBatch();
							pst.clearBatch();
						}
						
						totalcnt++;
					}
					
					pst.executeBatch();
				}
				
				conn.commit();

			} catch (SQLException e) {
				DBUtils.rollback(conn);
				e.printStackTrace();
				
				return false;
			} finally {
				DBUtils.close(pst, conn);
				DBUtils.close(pst1, conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}

		if(totalcnt>0){
			return true;
		}else{
			return false;
		}
	}


	public Boolean importStudent(String fileName) {

		int totalcnt=0;

		try {
			Workbook workBook = null;

			try {
				workBook = new XSSFWorkbook(fileName);
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(new FileInputStream(fileName));
			}

			ApplicationContext ac = SpringUtil.getApplicationContext();
			SqlSessionFactory mSqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
			SqlSession session = mSqlSessionFactory.openSession();
			Connection conn = session.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql = "";

			try {



				sql = " insert into course (guid,name,week,coursesort,classid,ptime,adduser) values (?,?,?,?,?,?,228)  ";

				pst=conn.prepareStatement(sql);

				for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
					Sheet sheet = workBook.getSheetAt(numSheet);

//					String gradename=StringUtils.trimToEmpty(sheet.getRow(0).getCell(0).getStringCellValue());
//					gradename=StringUtils.substringBefore(gradename,"（");
//
//					String classname=StringUtils.trimToEmpty(sheet.getRow(0).getCell(0).getStringCellValue());
//					classname=StringUtils.substringBefore(classname,"学");
//					classname="（"+StringUtils.substringAfter(classname,"（");



					if (sheet == null) {
						continue;
					}
					// 循环行Row
					for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {


						System.out.println(numSheet+"-----------------------"+rowNum);

						Row row = sheet.getRow(rowNum);
						if (row == null) {
							break;
						}

//						StringUtils.isBlank(row.getCell(1))

						if(row.getCell(1)==null){
							break;
						}

//						if(StringUtils.trimToEmpty(row.getCell(1).getStringCellValue()).length()==0){
//							break;
//						}

//						System.out.println(StringUtils.trimToEmpty(row.getCell(1).getStringCellValue()));

						String guid=StringUtils.trimToEmpty(row.getCell(0).getStringCellValue());
						if(guid.length()==0){
							guid=UUID.randomUUID().toString();
						}

						pst.setString(1, guid);// 主键
						pst.setString(2, StringUtils.trimToEmpty(row.getCell(1).getStringCellValue()));// 姓名
						pst.setInt(3, NumberUtils.toInt(row.getCell(2).getStringCellValue()));// 主键
						pst.setInt(4, NumberUtils.toInt(row.getCell(3).getStringCellValue()));// 主键
						pst.setInt(5, NumberUtils.toInt(row.getCell(4).getStringCellValue()));// 主键
						pst.setString(6, StringUtils.trimToEmpty(row.getCell(8).getStringCellValue()));// 主键

						pst.addBatch();

						if (numSheet % 100 == 0) {
							pst.executeBatch();
							pst.clearBatch();
						}

						totalcnt++;


					}

					pst.executeBatch();
				}

				conn.commit();

			} catch (SQLException e) {
				DBUtils.rollback(conn);
				e.printStackTrace();

				return false;
			} finally {
				DBUtils.close(pst, conn);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		if(totalcnt>0){
			return true;
		}else{
			return false;
		}
	}
	
}
