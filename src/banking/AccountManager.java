package banking;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;




//입금 예외처리
class DepositMoneyErrror extends Exception{
	public DepositMoneyErrror(String message) {
		super(message);
	}
}

class WithdrawMoneyError extends Exception{
	public WithdrawMoneyError(String mes) {
		super(mes);
	}
}

public class AccountManager {
	
	private HashSet<Account> accountMg; //set은 중복처리 해줌
	private AutoSaver autoSaveThread; 
	
	public AccountManager(int num) {
		accountMg = new HashSet<Account>();
		autoSaveThread = new AutoSaver(this);  // 자동 저장 쓰레드 초기화
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
	    System.out.println("3. 특판계좌");
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
	    BankingSystemMain.scan.nextLine();

	   
	    // Account 타입 변수 선언-> Account클래스가 계좌관련 부모클래스이다
	    //조건에 따른 객체를 선택하기 때문에 null값으로 초기화, 그리고 객체참조 변수이기 때문에 참조값 또는 null만 할당가능
	    Account newAccount = null;

	    if (geza.equals("1")) {
	        newAccount = new NormalAccount(acnum, name, money, basicInterestRate);
	    } else if (geza.equals("2")) {
	        System.out.println("신용등급(A, B, C 등급):");
	        String creditGrade = BankingSystemMain.scan.nextLine();
	        newAccount = new CreditTrustAccount(acnum, name, money, basicInterestRate, creditGrade);
	    } else if(geza.equals("3")) {
	    	  newAccount = new SpecialAccount(acnum, name, money, basicInterestRate, 0);
	    }
	    
	    else {
	        System.out.println("잘못된 선택입니다.");
	        return;
	    }
	    
//	    // 계좌 정보를 DB에 삽입하기 위해 SQLinsert 호출
//	    SQLinsert sqlInsert = new SQLinsert("education", "1234");
//	    sqlInsert.insertAccountToDB(newAccount);  // 계좌 객체를 DB에 삽입
	    
	    //중복계좌 확인
	    //Iterator는 HashSet<Account>에서 계좌정보를 순차적으로 가져오기 위해 사용!
	    //Iterator는 컬렉션에서 순차적으로 데이터를 탐색할 수 있는 방법!
	    Iterator<Account> ac = accountMg.iterator();
	    
	    //has.next() -> accountMg에서 아직 처리하지 않은 계좌가 남아있는지 확인하는 조건
	    while(ac.hasNext()) {
	    	
	    	//hasNext()가 true이면 next()를 호출하면서 다음 데이터를 가져온다.
	    	Account account = ac.next();
	    	if(acnum.compareTo(account.getAcnum())==0) {
	    		System.out.println("중복계좌발견됨. 덮어쓸까요?(y or n)");
	    		String resp = BankingSystemMain.scan.nextLine();
	    		
	    		if(resp.equalsIgnoreCase("y")) {
	    			ac.remove();
	    			accountMg.add(newAccount);
	    			System.out.println("기존정보 삭제 후 덮어쓰기!");
	    		}
	    		else if(resp.equalsIgnoreCase("n")) {
	    			System.out.println("새로운 정보는 무시!");
	    		}
	    		else {
	    			System.out.println("y or n 중에서 입력해주세요!");
	    		}
	    		return;
	    	}
	    }
	    accountMg.add(newAccount);
	    System.out.println("계좌개설이 완료되었습니다.");
	}

	
	//2.입금
	public void depositMoney() {
		
		try {
			
			System.out.println("***입   금***");
			System.out.print("계좌번호: "); 
			String acnum = BankingSystemMain.scan.nextLine();

			System.out.print("금액: ");
			int amount = BankingSystemMain.scan.nextInt(); 
			BankingSystemMain.scan.nextLine(); //줄바꿈제거
			
			if(amount<0) {
				throw new DepositMoneyErrror("정수만 입력하세요");
			}
			
			if(amount %500!=0) {
				throw new DepositMoneyErrror("500단위로만 입금 가능!");
			}

			// 계좌 검색 및 입금 처리
			for (Account account: accountMg) {
				if (account.getAcnum().equals(acnum)) {
					int newBalance = account.calculateInterest(amount);
					System.out.println("입금이 완료되었습니다. 현재 잔고: " + account.money);
					return;
				}
			}
			System.out.println("해당 계좌번호를 찾을 수 없습니다.");
		}
		//음수를 입금할 수 없다
		catch(DepositMoneyErrror e) {
			System.out.println("[예외발생] " + e.getMessage());
		}
		//금액입력시 문자를 입력할 수 없다.
		catch(InputMismatchException e) {
			e.printStackTrace();
			System.exit(0); //이거 안걸면 무한루프 걸림
		}
		
		
	}

	//3. 출금
	public void withdrawMoney() {
		
		try {
			System.out.println("***출   금***");
			System.out.println("계좌번호와 출금할 금액을 입력하세요");
			System.out.println("계좌번호:"); 
			String inputAcNum = BankingSystemMain.scan.nextLine();
			System.out.println("출금액:"); 
			int outMoney = BankingSystemMain.scan.nextInt();
			BankingSystemMain.scan.nextLine();
			
			if(outMoney<0) {
				throw new WithdrawMoneyError("정수만 입력하세요");
			}
			if(outMoney %1000!=0) {
				throw new WithdrawMoneyError("1000단위로만 출금 가능!");
			}
			
			 boolean found = false;

		        for (Account account: accountMg) {
		            if (account.getAcnum().equals(inputAcNum)) {
		                found = true;

		                if (account.money >= outMoney) {
		                    account.money -= outMoney;
		                    System.out.println("출금이 완료되었습니다. 현재 잔고: " + account.money);
		                } else {
		                    System.out.println("잔고가 부족합니다. 금액 전체를 출금할까요?");
		                    System.out.println("YES: 금액전체 출금처리, NO: 출금요청취소");
		                    String response = BankingSystemMain.scan.nextLine();

		                    if (response.equalsIgnoreCase("YES")) {
		                        int totalWithdraw = account.money;
		                        account.money = 0;
		                        System.out.println("전체 금액 " + totalWithdraw + "원이 출금되었습니다. 현재 잔고: 0원");
		                    } else if (response.equalsIgnoreCase("NO")) {
		                        System.out.println("출금 요청이 취소되었습니다.");
		                    } else {
		                        System.out.println("잘못된 입력입니다. 출금 요청이 취소되었습니다.");
		                    }
		                }
		                return;
		            }
		        }

		        if (!found) {
		            System.out.println("해당 계좌번호를 찾을 수 없습니다.");
		        }
		}
		catch(WithdrawMoneyError e) {
			System.out.println("[예외발생]"+e.getMessage());
		}
					
	}
	
	
	//4. 계좌정보 출력(계좌번호, 고객이름, 잔고를 입력받은 후 전체계좌정보 출력)
	public void acShowData() {
		
		for(Account account: accountMg) {
			account.acAllData();
		}
		System.out.println("-------------");
		System.out.println("전체계좌정보 출력이 완료되었습니다.");
	}
	
	//5.계좌정보 삭제
	public void deleteAccount() {
		System.out.println("**** 계좌정보삭제 ****");
		
		for(Account account:accountMg) {
			account.acAllData();
		}
		System.out.println("-----------------------");
		System.out.println("삭제할 계좌번호를 입력하시오");
		String inputAcnum = BankingSystemMain.scan.nextLine();
		
		Account tempAccount = new NormalAccount(inputAcnum, "", 0, 0);
		
		
		if(accountMg.contains(tempAccount)) {
			accountMg.remove(tempAccount);
			System.out.println("계좌가 삭제되었습니돠아");
		}
		else {
			System.out.println("계좌번호를 발견하지 못했습니다");
		}
	}
	
	
	
	
	//io를 통해 직렬화하기
	public void saveAcnum() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
									new FileOutputStream("src/banking/AccountInfo.obj"));
			for(Account aco: accountMg) {
				out.writeObject(aco);
			}
			System.out.println("Account.obj 파일로 저장되었습니다 ");
			
		}
		catch(Exception e){
			System.out.println("계좌직렬화 중 예외발생");
			e.printStackTrace();
		}
	}
	
	public void readAcnum() {
	    try 
	    {
	    	ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/banking/AccountInfo.obj"));
	    
	        while (true) {
	            try {
	                Account aco = (Account) in.readObject();  
	                accountMg.add(aco);  
	            } catch (EOFException e) {
	                break;
	            }
	            
	        }
	        System.out.println("AccountInfo.obj 복원완료");
	    }catch (Exception e) {
	        System.out.println("Account.obj 파일없음");
	       
	    }
	}
	
	public void autoSave() {
	    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
	            new FileOutputStream("src/banking/AutoSaveAccount.txt")))) {

	        for (Account account : accountMg) {
	            // 계좌 유형을 구분
	            String accountType = ""; // 계좌 유형을 저장할 변수

	            if (account instanceof NormalAccount) {
	                accountType = "[보통계좌]";
	            } else if (account instanceof CreditTrustAccount) {
	                accountType = "[신용신뢰계좌]";
	            }

	            // 계좌 정보 출력 시 계좌 유형을 포함
	            writer.write(accountType); // 계좌 유형 추가
	            writer.newLine();
	            writer.write("계좌번호: " + account.acnum); // 계좌번호
	            writer.newLine();
	            writer.write("이름: " + account.name); // 고객 이름
	            writer.newLine();
	            writer.write("잔고: " + account.money); // 잔고
	            writer.newLine();

	            // 신용신뢰계좌인 경우 신용등급도 출력
	            if (account instanceof CreditTrustAccount) {
	                CreditTrustAccount cta = (CreditTrustAccount) account;
	                writer.write("신용등급: " + cta.creditGrade); // 신용등급
	                writer.newLine();
	            }

	            writer.write("---------------"); // 구분선
	            writer.newLine();
	        }

	        System.out.println("계좌 정보가 텍스트로 자동저장되었습니다.");

	    } catch (Exception e) {
	        System.out.println("자동 저장 중 예외 발생");
	        e.printStackTrace();
	    }
	}




    // 자동저장 쓰레드 시작
    public void startAutoSaveThread() {
        if (!autoSaveThread.isAlive()) {
            autoSaveThread.start();  
            System.out.println("자동저장이 시작되었습니다.");
        } else {
            System.out.println("자동저장이 이미 실행 중입니다.");
        }
    }

    // 자동저장 쓰레드 중지
    public void stopAutoSaveThread() {
        if (autoSaveThread.isAlive()) {
            autoSaveThread.interrupt();  
            System.out.println("자동저장이 중지되었습니다.");
        } else {
            System.out.println("자동저장이 실행되지 않았습니다.");
        }
        

    }
 
    public void autoSaveMain() {
        System.out.println("**** 자동저장을 시작합니다 ****");
        System.out.println("저장옵션을 선택하시오");
        System.out.println("1. 자동저장 ON");
        System.out.println("2. 자동저장 OFF");
        System.out.print("선택: ");
        String choNum = BankingSystemMain.scan.nextLine();

        if (choNum.equals("1")) {
        	//thread가 없거나 이미 종료되었을 경우
            if (autoSaveThread == null || !autoSaveThread.isAlive()) {
                autoSaveThread = new AutoSaver(this);  
                autoSaveThread.start();                
                System.out.println("자동저장이 시작되었습니다.");
            } 
            else {
                System.out.println("이미 자동저장이 실행 중입니다.");
            }
        } 
        else if (choNum.equals("2")) {
            if (autoSaveThread != null && autoSaveThread.isAlive()) {
                autoSaveThread.interrupt();           
                System.out.println("자동저장이 중지되었습니다.");
            } 
            else {
                System.out.println("자동저장이 실행 중이 아닙니다.");
            }
        } 
        else {
            System.out.println("잘못된 입력입니다. 1 또는 2를 입력하세요.");
        }
    }
    
    public void saveAccountsAsText() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("src/banking/AutoSaveAccount.txt")))) {

            if (accountMg.isEmpty()) {
                System.out.println("저장할 계좌 정보가 없습니다.");
            }

            for (Account acc : accountMg) {
               
                if (acc instanceof NormalAccount) {
                    writer.write("[보통계좌]");
                } else if (acc instanceof CreditTrustAccount) {
                    writer.write("[신용신뢰계좌]");
                }

                writer.newLine();
                writer.write("계좌번호: " + acc.acnum);
                writer.newLine();
                writer.write("이름: " + acc.name);
                writer.newLine();
                writer.write("잔고: " + acc.money);
                writer.newLine();

                
                if (acc instanceof CreditTrustAccount) {
                    CreditTrustAccount cta = (CreditTrustAccount) acc; //다운캐스팅
                    writer.write("신용등급: " + cta.creditGrade);
                    writer.newLine();
                }

                writer.write("---------------");
                writer.newLine();
            }

            System.out.println("계좌 정보가 텍스트로 저장되었습니다!");

        } catch (Exception e) {
            System.out.println("텍스트 저장 중 오류 발생!");
            e.printStackTrace();
        }
    }







}
	
	


