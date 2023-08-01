package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SingletonConnectionHelper {
	private static Connection conn = null;
	private SingletonConnectionHelper() {} 
	//생성자 private
	//  SingletonConnectionHelper single = new SingletonConnectionHelper();(x)
	
	public static Connection getConnection(String dsn) throws ClassNotFoundException { //oracle , mysql 도 연결
		Class.forName("oracle.jdbc.OracleDriver");
		if(conn != null) {
			return conn;
		}
		
		try {
			 if(dsn.equals("oracle")) {
				
				 conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","KOSA","1004");
				 
			 } else if(dsn.equals("mysql")) {
				
				 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true","KOSA","1004");
				 
			 }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("싱글톤 DB연결 = " + e.getMessage());
		}
		
		return conn;
	}
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}


}
