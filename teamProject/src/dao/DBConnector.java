package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
//	Connection 타입의 getConnection 메소드 생성
	public static Connection getConnection() {
//		Connection 타입의 connection 객체 선언
		Connection connection = null;
		try {
//			연결에 필요한 정보
			String userName = "hr";
			String password = "hr";
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			
//			드라이버를 메모리에 할당
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			정보를 전달하여 연결 객체 가져오기
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("연결 성공");
			
//			드라이버 로딩 관련 catch문
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패");
			
//			SQL 연결 관련 catch 문
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("연결 실패");
			
//			그 외 오류 catch문
		} catch(Exception e) {
			System.out.println("알 수 없는 오류");
		}
		
//		connection 정보 Connection 타입으로 리턴
		return connection;
	}
}
