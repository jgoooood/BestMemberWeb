package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBCTemplate {
	//탬플릿 싱글톤패턴 적용 : 메소드를 통해서만 생성가능
	//---------------------------- 싱글톤패턴 적용 : 8줄 ------------------------
	private static JDBCTemplate instance;
	private static Connection conn;
	
	private JDBCTemplate() {	}
	
	public static JDBCTemplate getInstance() {
		if(instance == null) { //JDBCTemplate객체가 없을 때만 생성
			instance = new JDBCTemplate();
		}
		return instance;
	}
	//private으로 접근제어자를 바꿔줬기 때문에 JDBCTemplate클래스 안에서만 생성가능
	//---------------------------------------------------------------------------
	
	//연결생성메소드
	public Connection createConnection() {
		
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "MEMBERWEB";
		String password = "MEMBERWEB";
		try {
			if(conn == null || conn.isClosed()) { //연결이 없을때 or 닫힌 상태일때만 연결생성함
				Class.forName(driverName);
				conn = DriverManager.getConnection(url, user, password);
				conn.setAutoCommit(false); // 오토커밋해제
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/*
	 * close메소드 : 연결해제
	 */
	public void close(Connection conn) {
		if(conn != null) {
			try {
				if(!conn.isClosed()) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 커밋 메소드
	 */
	public void commit(Connection conn) {
		if(conn != null) {
			try {
				if(!conn.isClosed()) conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 롤백메소드
	 */
	public void rollback(Connection conn) {
		if(conn != null) {
			try {
				if(!conn.isClosed()) conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
