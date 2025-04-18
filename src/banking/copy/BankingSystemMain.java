package banking.copy;

import java.io.FileOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Handler;

class NumberError extends Exception{
	public NumberError(String message) {
		super(message);
	}
}

interface Menu {
    int MAKE = 1; 			// 계좌개설
    int DEPOSIT = 2;        // 입금
    int WITHDRAW = 3;       // 출금
    int INQUIRE = 4; 		// 계좌정보출력
    int DELETEAC = 5; 		//계좌정보삭제
    int SAVEOP = 6; 		// 저장옵션
    int EXIT = 7;           // 프로그램 종료
}
public class BankingSystemMain {
	
	public static Scanner scan = new Scanner(System.in);
	
	public static void showMenu() {
		
		System.out.println("-----Menu------");
	    System.out.println(Menu.MAKE + ". 계좌개설");
	    System.out.println(Menu.DEPOSIT + ". 입금");
	    System.out.println(Menu.WITHDRAW + ". 출금");
	    System.out.println(Menu.INQUIRE + ". 계좌정보출력");
	    System.out.println(Menu.DELETEAC+". 계좌정보삭제");
	    System.out.println(Menu.SAVEOP+". 저장옵션");
	    System.out.println(Menu.EXIT + ". 프로그램종료");
	}
	

	public static void main(String[] args) {
		
		AccountManager handler =  new AccountManager(50);
		
		handler.readAcnum();
		
		while(true) {
			showMenu();
			
			try {
				int choice = scan.nextInt();
				scan.nextLine();
				
				if(choice < 1 || choice > 7) {
					throw new NumberError("1~6 사이의 숫자를 입력해주세요");
				}
				
				switch(choice) {
				case 1:
					handler.makeAccount(choice); break;
					
				case 2:
					handler.depositMoney(); break;
					
				case 3:
					handler.withdrawMoney(); break;
					
				case 4:
					handler.acShowData(); break;
				case 5:
					handler.deleteAccount(); break;
				
				case 6:
				    handler.autoSaveMain(); 
				    break;
				case 7:
					handler.saveAcnum();
					System.out.println("**** 프로그램 종료 ****");
					return;
			}
			
			}
			catch(InputMismatchException e) {
				e.printStackTrace();
				System.exit(0); //이거 안걸면 무한루프 걸
			}
			
			catch(NumberError e) {
				System.out.println("[예외발생] " + e.getMessage());

			}
		}
	
	}


	


	

}
