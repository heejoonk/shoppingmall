package com.ezenac.shop.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Dbman {
	
	public static Connection getConnection() {
		Connection con = null;
		// 서버에서 데이터 베이스 연결객체를 관리하고 있고, 그 주체인 DataSource 에서 연결을 얻어와서 사용합니다.
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
			con = ds.getConnection();
		} catch (NamingException e) {  e.printStackTrace();
		} catch (SQLException e) { e.printStackTrace();
		}
		return con;
	}
	
	public static void close(Connection con, PreparedStatement pstmt , ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		}catch(Exception e) {		e.printStackTrace();		}
	}

}
