package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionUtil {
	public static Connection getConnection() throws Exception {
		// 톰캣 서버에서 자원을 찾기 위해 InitialContext 인스턴스 생성
		InitialContext initialContext = new InitialContext();
		// lookup() 메소드로 JNDI 이름으로 등록돼있는 서버 자원 찾음
		// @name : 서버 자원의 JNDI 이름
		// 찾으려는 자원이 JDBC DataSource이므로 java:comp/env...
		DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/kosa");
		return ds.getConnection();
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
