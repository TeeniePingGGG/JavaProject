package banking;

import java.util.Scanner;
import java.util.logging.Handler;

public class BankingSystemMain {
	
	public static Scanner scan = new Scanner(System.in);
	
	public static void showMenu() {
		System.out.println("-----Menu------");
		System.out.println("1.계좌개설");
		System.out.println("2.입	금");
		System.out.println("3.출	금");
		System.out.println("4.계좌정보출력");
		System.out.println("5.프로그램종료");
	}
	

	public static void main(String[] args) {
		
		AccountManager handler =  new AccountManager(50);
		
		while(true) {
			showMenu();
			
			int choice = scan.nextInt();
			scan.nextLine();
			
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
				System.out.println("프로그램이 종료되었습니다");
				return;
			}
		}
	
	}

}
