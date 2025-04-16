package banking.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import banking.Account;
import banking.AccountManager;
import banking.BankingSystemMain;

public class SQLinsert2 extends MyConnection {
	
	public SQLinsert2(String user, String pass) {
		super(user,pass);
	}
	
	String query; //쿼리문 작성
	int result; //쿼리 실행 후 결과 반환
	ResultSet rs = null;
	
	@Override
	public void dbExecute() {
		try {
			 //입금
			 //입력값 받기(계좌번호, 입금액)
            String acnum = inputValue("계좌번호");
            int deposit = Integer.parseInt(inputValue("입금액"));
            
            //잔액랑 이자율 조회하기
            query = "SELECT money, basicInterestRate FROM banking WHERE acnum = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, acnum);
            rs = psmt.executeQuery();  

            int currentMoney = 0; //잔액
            double rate = 0;  //이자율
            
            //계좌가 존재하는지 확인
            if (rs.next()) {
            	//계좌가 존재하면 해당 계좌의 잔액과 이자율값을 가져온다.
                currentMoney = rs.getInt("money");  
                rate = rs.getInt("basicInterestRate");  
            } else {
                System.out.println("계좌가 존재하지 않습니다.");
                return;  
            }
            
            // 새로운 잔액 계산: 기존 잔액 + (이자율 반영) + 입금액
            double interestAmount = currentMoney * (rate / 100);  //이자 금액 계산
            int newMoney = (int)(currentMoney + interestAmount + deposit);  //새로운 금액 계산

            // 업데이트 쿼리 작성
            query = "UPDATE banking SET money = ? WHERE acnum = ?";
            psmt = con.prepareStatement(query);

            psmt.setInt(1, newMoney);  
            psmt.setString(2, acnum);  

            result = psmt.executeUpdate();  
			
			
		} catch (Exception e) {
			System.out.println("쿼리 실행 중 예외발생");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new SQLinsert2("education", "1234").dbExecute();
	}

}
