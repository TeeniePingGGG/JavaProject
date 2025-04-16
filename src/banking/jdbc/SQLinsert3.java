package banking.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import banking.Account;
import banking.AccountManager;
import banking.BankingSystemMain;

public class SQLinsert3 extends MyConnection {
	
	public SQLinsert3(String user, String pass) {
		super(user,pass);
	}
	
	String query; //쿼리문 작성
	int result; //쿼리 실행 후 결과 반환
	ResultSet rs = null;
	
	@Override
	public void dbExecute() {
		try {
			//출금
			
			//계좌번호,출금액 입력값 받기
			String acnum = inputValue("계좌번호");
			int withdrawm = Integer.parseInt(inputValue("출금액"));
			
			//잔고조회
			query = "select money from banking where acnum=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, acnum);
			rs = psmt.executeQuery();
			
			int currentMoney = 0;//잔액
			
			//계좌가 존재하는지 확인
			if(rs.next()) {
				//계좌가 존재하면 해당 계좌의 잔액을 가져온다.
				currentMoney = rs.getInt("money");
			}else {
				System.out.println("계좌가 없는디?");
				return;
			}
			
			//출금 구하기
			int outMoney =(int)(currentMoney-withdrawm);
			
			//업데이트 쿼리문 작성
			
			 query = "UPDATE banking SET money = ? WHERE acnum = ?";
	            psmt = con.prepareStatement(query);

	            psmt.setInt(1, outMoney); 
	            psmt.setString(2, acnum);  
	            
			result = psmt.executeUpdate();
			System.out.println("[psmt]"+result+"행 입력됨");
			
		} catch (Exception e) {
			System.out.println("쿼리 실행 중 예외발생");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new SQLinsert3("education", "1234").dbExecute();
	}

}
