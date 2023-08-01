package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/*
JDBC 작업 (5가지 : 전체, 조건, 삽입, 삭제, 수정 : class EmpDao{ 5개 함수})
1. 드라이버 로딩
2. 연결 객체 생성, 명령, 자원 해제
3. 반복적인 코드 제거

 반복적인 코드를 가지고 있는 별도의 클래스
 DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "KOSA", "1004");
 
 ConnectionHelper 설계
 함수 ..... 자주 사용 > static 설계 > 함수 종류 >> overloading >> 다형성
 
 현업에서는 성능 개선을 위해서 connection 객체 미리 생성해서 사용하고 반환 하는
 pool방식을 선택한다
 hudi.blog/dbcp-and-hikaricp/
 spring 프로젝트 진행시 connection pool 방식 사용
*/



public class ConnectionHelper {
	private static String jdbc = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String host = "KOSA";
	private static String password = "1004";
	
	public static Connection getConnection(String dsn) {
		
		Connection conn = null;
		
		try {
			if(dsn.equals("oracle")) {
				conn = DriverManager.getConnection(jdbc, host, password);
			} else if(dsn.equals("mysql")) {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb?sampledb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true", host, password);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return conn;
	}
	
	public static Connection getConnection(String dsn, String id, String pwd) {
		
		Connection conn = null;
		
		try {
			if(dsn.equals("oracle")) {
				conn = DriverManager.getConnection(jdbc, id, pwd);
			} else if(dsn.equals("mysql")) {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampledb?sampledb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true", id, pwd);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
