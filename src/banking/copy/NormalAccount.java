package banking.copy;

public class NormalAccount extends Account {
    //기본이자
	double basicInterestRate;

    //생성자
    public NormalAccount(String acnum, String name, int money, double basicInterestRate) {
        super(acnum, name, money);  // 부모 클래스 생성자 호출
        this.basicInterestRate = basicInterestRate;
    }

    // 일반 계좌의 이자 계산
    @Override
    public int calculateInterest(int depositAmount) {
        // 잔고 + 기본이자 + 입금액
        double newBalance = money + (money * basicInterestRate / 100) + depositAmount;
        money = (int) newBalance;  // 새로운 잔고를 계산하여 money에 저장
        return money;  // 새로운 잔고 반환
    }
    
    public void acAllData() {
    	super.acAllData();
    	System.out.println("기본이자> "+basicInterestRate+"%");
    	
    }
}
