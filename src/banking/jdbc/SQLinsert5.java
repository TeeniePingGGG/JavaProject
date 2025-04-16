package banking.jdbc;

import java.sql.ResultSet;

public class SQLinsert5 extends MyConnection {
    
    public SQLinsert5(String user, String pass) {
        super(user, pass);
    }
    
    String query;
    ResultSet rs = null;
    
    @Override
    public void dbExecute() {
        try {
            // 1. 계좌번호 입력 받기
            String acnum = inputValue("조회할 계좌번호");

            // 2. 해당 계좌번호의 정보 조회
            query = "SELECT acnum, name, money, basicInterestRate FROM banking WHERE acnum = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, acnum);
            rs = psmt.executeQuery();

            // 3. 결과 출력
            if (rs.next()) {
                String name = rs.getString("name");
                int money = rs.getInt("money");
                double basicInterestRate = rs.getDouble("basicInterestRate");

                System.out.println("계좌번호: " + acnum);
                System.out.println("이름: " + name);
                System.out.println("잔액: " + money);
                System.out.println("기본 이자율: " + basicInterestRate);
            } else {
                System.out.println("해당 계좌번호는 존재하지 않습니다.");
            }

        } catch (Exception e) {
            System.out.println("예외 발생");
            e.printStackTrace();
        } 
    }

    // 테스트용 main 메서드
    public static void main(String[] args) {
        new SQLinsert5("education", "1234").dbExecute();
    }
}
