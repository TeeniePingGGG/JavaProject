package banking;

public class CreditTrustAccount extends Account {
    double basicInterestRate;
    String creditGrade;

    // 생성자
    public CreditTrustAccount(String acnum, String name, int money, double basicInterestRate, String creditGrade) {
        super(acnum, name, money);  // 부모 클래스 생성자 호출
        this.basicInterestRate = basicInterestRate;
        this.creditGrade = creditGrade;
    }

    // 신용 신뢰 계좌의 이자 계산
    @Override
    public int calculateInterest(int depositAmount) {
        double creditInterest = 0;
        if (creditGrade.equals("A")) {
            creditInterest = 0.05;  
        } else if (creditGrade.equals("B")) {
            creditInterest = 0.03;  
        } else if (creditGrade.equals("C")) {
            creditInterest = 0.01;  
        }

        // 잔고 + 기본이자 + 신용등급 이자 + 입금액
        double newBalance = money + (money * basicInterestRate / 100) + (money * creditInterest) + depositAmount;
        money = (int) newBalance; 
        return money; 
    }
    
    public void acAllData() {
    	super.acAllData();
    	System.out.println("기본이자> "+basicInterestRate+"%");
    	System.out.println("신용등급>"+creditGrade);
    	
    }
}
