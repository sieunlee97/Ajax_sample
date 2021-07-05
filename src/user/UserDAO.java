package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/*Database Access Object, 데이터베이스에 접근하는 클래스  */

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt; //SQL문을 DB에 보내기 위한 객체
	private ResultSet rs; // SQL 질의에 의해 생성된 테이블을 저장하는 객체
	
	// 생성자
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/ajax?serverTimezone=Asia/Seoul&useSSL=false";
			String dbID="root";
			String dbPassword="apmsetup";
			Class.forName("com.mysql.cj.jdbc.Driver"); //드라이버 검색
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); //실제 연동
			//System.out.println("DB연동 성공");
		}catch (Exception e) {
			System.out.println("DB연동 실패");
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> search(String userName){ //DB안에 저장되어있는 회원 정보를  ArrayList로 가져옴
		String SQL = "SELECT * FROM user WHERE userName LIKE ?"; // 검색 SQL
		ArrayList<User> userList = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(SQL); //텍스트 SQL 구문을 DB에 보냄
			pstmt.setString(1,"%"+userName+"%"); // 매개변수 값 대입
			rs = pstmt.executeQuery(); //SQL질의 결과를 rs에 저장
			//결과가 2개 이상인 경우 while(rs.next()) 사용
			while (rs.next()) { //rs.next(): 커서 이동
				User user = new User();
				// rs.getString("컬럼")
				user.setUserName(rs.getString(1)); //SQL질의 결과의 1번 컬럼(이름)을 userName에 저장
				user.setUserAge(rs.getInt(2)); //SQL질의 결과의 2번 컬럼(나이)을 userAge에 저장
				user.setUserGender(rs.getString(3)); //SQL질의 결과의 3번 컬럼(성별)을 userGender에 저장
				user.setUserEmail(rs.getString(4)); //SQL질의 결과의 4번 컬럼(이메일)을 userEmail에 저장
				userList.add(user);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return userList; //userList로 반환
	}
	public int register(User user) {
		String SQL = "INSERT INTO user VALUES (?,?,?,?);";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, user.getUserAge());
			pstmt.setString(3, user.getUserGender());
			pstmt.setString(4, user.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}
}
