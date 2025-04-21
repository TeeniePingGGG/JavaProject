package banking.jdbc;

public class SQLDelete extends MyConnection{
	
	public SQLDelete(String user, String pass) {
        super(user, pass);
    }
	
	String query; //쿼리문 작성
	int result; //쿼리 실행 후 결과 반환
	
	public void dbExecute() {
		try {
			
			
			//계좌번호
			query ="delete from banking where acnum=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, inputValue("삭제할 계좌번호"));
			System.out.println(psmt.executeUpdate()+"계좌 정보가 삭제되었습니다");
			
			
		}catch(Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		new SQLDelete("education", "1234").dbExecute();
	}

}
