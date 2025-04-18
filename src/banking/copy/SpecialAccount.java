package banking.copy;

public class SpecialAccount extends NormalAccount{
	
	//입금액 횟수
	int depositAmountCn=0;

	public SpecialAccount(String acnum, String name, int money, double basicInterestRate, int depositAmountNum) {
		super(acnum, name, money, basicInterestRate);
		this.depositAmountCn = depositAmountNum;
	}
	 
	 @Override
	    public int calculateInterest(int depositAmount) {
		 depositAmountCn++;

		 if(depositAmountCn%2!=0) {
			 return super.calculateInterest(depositAmount);
		 }else {
			 double newBalance = money + (money * basicInterestRate / 100) + depositAmount+500;
		        money = (int) newBalance;  
		        return money;  
		 }
	        
	    }

}
