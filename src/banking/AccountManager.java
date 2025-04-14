package banking;



public class AccountManager {
	
	//계좌를 저장하고, 몇개의 계좌가 저장되어 있는지
	private Account[] accountMg;
	private int numOfAcount;
	
	//생성자
	public AccountManager(int num) {
		
		 accountMg = new Account[num];
		 numOfAcount = 0;
		 
	}
	
	//1. 계좌개설
	public void makeAccount(int choice) {
	    System.out.println("***신규계좌개설***");

	    String acnum;
	    String name;
	    int money;

	    System.out.println("계좌선택");
	    System.out.println("1. 보통계좌");
	    System.out.println("2. 신용신뢰계좌");
	    String geza = BankingSystemMain.scan.nextLine();  // 계좌 종류 선택

	    System.out.println("계좌번호:"); 
	    acnum = BankingSystemMain.scan.nextLine();
	    
	    System.out.println("고객이름:"); 
	    name = BankingSystemMain.scan.nextLine();
	    
	    System.out.println("잔고:"); 
	    money = BankingSystemMain.scan.nextInt();

	    // 기본 이자율 입력 받기
	    System.out.println("기본이자% (정수형태로 입력):");
	    double basicInterestRate = BankingSystemMain.scan.nextDouble();

	    
	    if (geza.equals("1")) {
	    	//한번에 초기화 하기 위해 생성자로 선언
	        accountMg[numOfAcount++] = new NormalAccount(acnum, name, money, basicInterestRate); 
	        /* 만약 메서드로 선언하고 싶다면...
	         NomalAccount account = new NomalAccount(acnum, name, money)
	         account.setBasicInterestRate(basicInterestRate);
	         accountMg[numOfAcount++] = count;
	         
	         */
	    } else if (geza.equals("2")) {
	        System.out.println("신용등급(A, B, C 등급):");
	        String creditGrade = BankingSystemMain.scan.next();
	        accountMg[numOfAcount++] = new CreditTrustAccount(acnum, name, money, basicInterestRate, creditGrade);
	    }

	    System.out.println("계좌개설이 완료되었습니다.");
	}

	
	//2.입금
	public void depositMoney() {
		

		System.out.println("***입   금***");
		System.out.print("계좌번호: "); String inputAcNum = BankingSystemMain.scan.nextLine();

		System.out.print("금액: ");
		int amount = BankingSystemMain.scan.nextInt(); BankingSystemMain.scan.nextLine();

		// 계좌 검색 및 입금 처리
		for (int i = 0; i < numOfAcount; i++) {
			if (accountMg[i].acnum.equals(inputAcNum)) {
				int newBalance = accountMg[i].calculateInterest(amount);
				System.out.println("입금이 완료되었습니다. 현재 잔고: " + accountMg[i].money);
				return;
			}
		}

		System.out.println("해당 계좌번호를 찾을 수 없습니다.");
	}

	//3. 출금
	public void withdrawMoney() {
		System.out.println("***출   금***");
		System.out.println("계좌번호와 출금할 금액을 입력하세요");
		System.out.println("계좌번호:"); String inputAcNum = BankingSystemMain.scan.nextLine();
		System.out.println("출금액:"); int outMoney = BankingSystemMain.scan.nextInt();
		
		//계좌 검색과 출금 처리
		for (int i = 0; i < numOfAcount; i++) {
			accountMg[i].money -= outMoney;
			System.out.println("출금이 완료되었습니다. ");
			return;
		
	}
				
	}
	
	
	//4. 계좌정보 출력(계좌번호, 고객이름, 잔고를 입력받은 후 전체계좌정보 출력)
	public void acShowData() {
		
		for(int i=0; i<numOfAcount; i++) {
			accountMg[i].acAllData();
		}
		System.out.println("-------------");
		System.out.println("전체계좌정보 출력이 완료되었습니다.");
	}
	

}
