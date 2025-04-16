package banking.jdbc;

import java.sql.SQLException;

import banking.Account;
import banking.AccountManager;
import banking.BankingSystemMain;

public class SQLinsert extends MyConnection {
	
	public SQLinsert(String user, String pass) {
		super(user,pass);
	}
	
	String query; //쿼리문 작성
	int result; //쿼리 실행 후 결과 반환
	
	@Override
	public void dbExecute() {
		try {
			//계좌개설
			query = "INSERT INTO banking VALUES	"
					+"(seq_banking_idx.NEXTVAL,?,?,?,?)";
			
			psmt = con.prepareStatement(query);
			//쿼리문의 인파라미터를 입력값을 통해 설정함
			psmt.setString(1, inputValue("계좌번호"));
			psmt.setString(2, inputValue("이름"));
			psmt.setInt(3, Integer.parseInt(inputValue("잔액")));
			psmt.setInt(4, Integer.parseInt(inputValue("이자율")));

			
			result = psmt.executeUpdate();
			System.out.println("[psmt]"+result+"행 입력됨");
			
		} catch (Exception e) {
			System.out.println("쿼리 실행 중 예외발생");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new SQLinsert("education", "1234").dbExecute();
	}

}
