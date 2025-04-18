package banking.copy;

import java.util.*;

public class Threebythree {
    public static void main(String[] args) {
    	
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        
        int[] xRowCol = new int[2]; // X의 위치 
        String[][] board = initBoard(xRowCol, rand);
        int xRow = xRowCol[0];
        int xCol = xRowCol[1];

        // 게임판 출력
        printBoard(board);

        System.out.println("============");
        System.out.println("[ 이동 ] a:Left, d:Right, w:Up, s:Down");
        System.out.println("[ 종료 ] x:Exit");

        // 게임 루프
        while (true) {
            System.out.print("키를 입력해주세요: ");
            String inputNum = scan.nextLine();

            if (inputNum.equalsIgnoreCase("x")) {
                System.out.println("게임 종료");
                break;
            }

            int newXRow = xRow;
            int newXCol = xCol;

            // 사용자 입력 처리
            if (inputNum.equalsIgnoreCase("a")) { // 왼쪽
                newXCol = xCol - 1;
            } else if (inputNum.equalsIgnoreCase("d")) { // 오른쪽
                newXCol = xCol + 1;
            } else if (inputNum.equalsIgnoreCase("w")) { // 위쪽
                newXRow = xRow + 1;
            } else if (inputNum.equalsIgnoreCase("s")) { // 아래쪽
                newXRow = xRow - 1;
            } else {
                System.out.println("잘못된 입력입니다.");
                continue;
            }

            // 이동 가능한 범위인지 확인
            if (newXRow >= 0 && newXRow < 3 && newXCol >= 0 && newXCol < 3) {
                String temp = board[newXRow][newXCol];
                board[newXRow][newXCol] = "X";
                board[xRow][xCol] = temp;

                xRow = newXRow;
                xCol = newXCol;

                printBoard(board);

                // 정답 체크
                if (isCorrect(board)) {
                    System.out.println("정답입니다! 게임을 종료합니다.");
                    System.out.println("재시작하시겠습니까?(y누르면 재시작, 나머지는 종료)");
                    String putNum = scan.nextLine();
                    if (putNum.equalsIgnoreCase("y")) {
                        System.out.println("게임을 재시작합니다");
                        board = initBoard(xRowCol, rand); 
                        xRow = xRowCol[0];
                        xCol = xRowCol[1];
                        printBoard(board);
                    } else {
                        System.out.println("게임을 종료합니다");
                        break;
                    }
                }
            } else {
                System.out.println("이동불가");
            }
        }

        scan.close();
    }

    // 퍼즐 초기화 및 X 위치 설정 메서드
    public static String[][] initBoard(int[] xRowCol, Random rand) {
        String[][] board = new String[3][3];

        int[] numList = new int[8];
        for (int i = 1; i <= 8; i++) {
            numList[i - 1] = i;
        }

        // X 초기 위치
        xRowCol[0] = 2;
        xRowCol[1] = 2;
        board[2][2] = "X";

        // 숫자 넣기
        int i = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == null) {
                    board[row][col] = String.valueOf(numList[i++]);
                }
            }
        }

        shuffleBoard(board, xRowCol, rand); // 보드 섞기
        return board;
    }

    // 보드 섞기 메서드
    public static void shuffleBoard(String[][] board, int[] xRowCol, Random rand) {
        int shuffleNum = 20;
        int xRow = xRowCol[0];
        int xCol = xRowCol[1];

        for (int j = 0; j < shuffleNum; j++) {
            int ranNum = rand.nextInt(4);
            int newRow = xRow;
            int newCol = xCol;

            switch (ranNum) {
                case 0: newCol = xCol - 1; break;
                case 1: newCol = xCol + 1; break;
                case 2: newRow = xRow + 1; break;
                case 3: newRow = xRow - 1; break;
            }

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                String temp = board[newRow][newCol];
                board[newRow][newCol] = "X";
                board[xRow][xCol] = temp;

                xRow = newRow;
                xCol = newCol;
            }
        }

        xRowCol[0] = xRow;
        xRowCol[1] = xCol;
    }

    // 게임판 출력 메서드
    public static void printBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("[" + (board[i][j] == null ? " " : board[i][j]) + "] ");
            }
            System.out.println();
        }
    }

    // 정답 확인 메서드
    public static boolean isCorrect(String[][] board) {
        String[][] correctBoard = {
            {"1", "2", "3"},
            {"4", "5", "6"},
            {"7", "8", "X"}
        };

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].equals(correctBoard[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
