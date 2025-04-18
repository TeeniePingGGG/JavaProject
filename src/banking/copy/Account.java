package banking.copy;

import java.io.Serializable;

public abstract class Account implements Serializable {

	public String acnum;
	public String name;
	public int money;
	
	
	public Account(String acnum, String name, int money) {
		super();
		this.acnum = acnum; //계좌번호
		this.name = name; //이름
		this.money = money;//잔고
		
	}
	
	//계좌번호를 반환하는 메서드
	public String getAcnum() {
        return acnum;
    }
	//잔고를 반환하는 메서드
	 public int getMoney() {
	        return money;
	    }
	
	//잔고를 설정하는 메서드
	 public void setMoney(int money) {
	        this.money = money;
	    }
	 
	 @Override
	 public boolean equals(Object obj) {
	     if (this == obj) return true;
	     if (!(obj instanceof Account)) return false;

	     Account other = (Account) obj;
	     return this.getAcnum().equals(other.getAcnum()); // 계좌번호가 같으면 같다고 본다
	 }

	 @Override
	 public int hashCode() {
	     return acnum.hashCode();
	 }



	
	//이자 계산을 위한 추상메서드 각 클래스에 맞게 다형성을 위해!!!! 나는 추상메서드를 사용!!!
	 public abstract int calculateInterest(int depositAmount);
	
	//계좌정보 출력
	void acAllData() {
		System.out.println("***계좌정보출력***");
		System.out.println("-------------");
		
		System.out.println("계좌번호:"+acnum);
		System.out.println("고객이름:"+name);
		System.out.println("잔고:"+money);
	}


	
	
	
}
