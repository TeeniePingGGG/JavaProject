package banking.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import banking.Account;
import banking.AccountManager;
import banking.BankingSystemMain;

public class SQLinsert4 extends MyConnection {
	
	public SQLinsert4(String user, String pass) {
		super(user,pass);
	}
	
	String query; //쿼리문 작성
	int result; //쿼리 실행 후 결과 반환
	ResultSet rs = null;
	
	@Override
	public void dbExecute() {
		try {
			
			//전체 계좌 출력
			
			//계좌조회
			query = "select acnum, name, money, basicInterestRate from banking order by num";
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				String acnum = rs.getString("acnum");
                String name = rs.getString("name");
                int money = rs.getInt("money");
                double basicInterestRate = rs.getDouble("basicInterestRate");
                
                System.out.println("계좌번호: " + acnum + ", 이름: " + name + ", 잔액: " + money + ", 기본 이자율: " + basicInterestRate);
			}
			
			System.out.println("[psmt]"+result+"행 입력됨");
			
		} catch (Exception e) {
			System.out.println("쿼리 실행 중 예외발생");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new SQLinsert4("education", "1234").dbExecute();
	}

}
