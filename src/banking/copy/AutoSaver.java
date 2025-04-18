package banking.copy;

public class AutoSaver extends Thread {
    
    private AccountManager handler;

    // 생성자에서는 handler만 받음
    public AutoSaver(AccountManager handler) {
        this.handler = handler;
        setDaemon(true);  // 메인 쓰레드가 종료되면 자동으로 종료되도록 설정
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(5000);  // 5초마다 자동 저장
                System.out.println("자동 저장 중...");  // 이 부분을 추가해 확인
                handler.saveAccountsAsText();  // 텍스트 형식으로 저장
            } catch (InterruptedException e) {
                System.out.println("자동저장이 중단되었습니다.");
                break;
            }
        }
    }

}
