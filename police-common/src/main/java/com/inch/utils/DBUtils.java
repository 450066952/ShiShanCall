package com.inch.utils;

import java.sql.*;

public class DBUtils {
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs, Statement stmt, Connection conn,StringBuffer sb ) {
		try {
			close(rs,stmt,conn);
			if(null!=sb)
				sb.delete(0, sb.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt, Connection conn) {
		close(null, stmt, conn);
	}
	public static void close(Statement stmt, Connection conn,StringBuffer sb ) {
		close(null, stmt, conn);
		if(null!=sb)
		sb.delete(0, sb.length());
	}
	public static void rollback(Connection conn){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
