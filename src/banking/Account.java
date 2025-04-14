package banking;

public class Account {

	public String acnum;
	public String name;
	public int money;
	
	
	public Account(String acnum, String name, int money) {
		super();
		this.acnum = acnum; //계좌번호
		this.name = name; //이름
		this.money = money;//잔고
		
	}
	
	
	//계좌정보 출력
	void acAllData() {
		System.out.println("***계좌정보출력***");
		System.out.println("-------------");
		
		System.out.println("계좌번호:"+acnum);
		System.out.println("고객이름:"+name);
		System.out.println("잔고:"+money);
	}
	
	
}
