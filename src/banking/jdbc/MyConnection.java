package banking.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 인터페이스를 구현한 클래스로 extends 대신 implements를 사용한다
 또한 용어도 '상속'이 아닌 '구현'이라 표현한다.
 */
public class MyConnection implements IConnect{
	
	//멤버변수
	public Connection con;  //DB연결
	public ResultSet rs;	//select의 실행결과 반환
	public Statement stmt;	//정적 쿼리문 실행
	public PreparedStatement psmt; //동적 쿼리문 실행
	public CallableStatement csmt; //프로시저 실행
	
	
	public MyConnection(String user, String pass) {
		try {
			Class.forName(ORACLE_DRIVER);
			con = DriverManager.getConnection(ORACLE_URL,user,pass);
		} 
		catch (Exception e) {
			System.out.println("DB 커넥션 예외발생");
			e.printStackTrace();
		}
	}
	
	/*
	 IConnect 인터페이스를 구현했으므로 자식 클래스에서는 반드시
	 부모의 추상메서드를 오버라이딩해서 재정의 해야한다. 그렇지 않으면
	 에러가 발생된다.*/
	
	@Override
	public void dbExecute() {
		/*CRUD(DB의 기본적인 4가지 작업)는 자식클래스에서
		 * 처리 되어야하므로 부모클래스에서는 실행부를 정의할 수 없다.
		 * 따라서 실행부가 없는 메서드로 정의되었다.*/
	}
	
	//JDBC 작업을 위해 연결된 모든 자원을 해제
	@Override
	public void dbClose() {
		try {
			if(con!=null) con.close();
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(psmt!=null) psmt.close();
			if(csmt!=null) csmt.close();
			
			System.out.println("DB 자원 반납");
			
		} catch (SQLException e) {
			System.out.println("DB 자원 반납시 예외발생");
			e.printStackTrace();
		}
	}
	
	//사용자로터 입력을 받기 위해 정의
	@Override
	public String inputValue(String title) {
		Scanner scan = new Scanner(System.in);
		System.out.println(title+"을(를) 입력(exit->종료):");
		String inputStr = scan.nextLine();
		
		if("EXIT".equalsIgnoreCase(inputStr)) {
			System.out.println("프로그램을 종료합니다");
			dbClose();
			System.exit(0);
		}
		return inputStr;
	}
	


}
