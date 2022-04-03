package hw_1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;  // Note: Do not change this line.
    public static void theStudentsGame() {
        System.out.println("Dear president, please enter the board’s size.");
        String board_sizes = scanner.nextLine();
        int board_m = Integer.parseInt(board_sizes.substring(0,board_sizes.indexOf(("X"))-1));
        int board_n = Integer.parseInt(board_sizes.substring(board_sizes.indexOf(("X"))+2,board_sizes.length()));
        int[][] board =new int[board_m][board_n];
        getStudentsIndexes(board,board_m,board_n);
        printBoard(board, 1);
        int i=2;
        boolean changed = true;
        while (i<1000 && changed){
            changed = updateStatus(board,board_m,board_n);
            printBoard(board, i);
        }

    }

    public static boolean updateStatus(int[][] board, int m, int n){
        int[][] aux_board=new int[m][n];
        boolean changed = false;
        int valid_friends;
        for(int i=0;i< board.length;i++){
            for(int j=0;j< board[0].length;j++) {
                valid_friends = countValidFriends(board,m,n,i,j);
                if ((board[i][j] == 1) && (valid_friends <= 1 || valid_friends > 3))
                    aux_board[i][j] =1;
                if (board[i][j] == 0 && valid_friends == 3)
                    aux_board[i][j] =1;
            }
            }
        for(int i=0;i< aux_board.length;i++){
            for(int j=0;j< aux_board[0].length;j++){
                if(aux_board[i][j] == 1)
                {changeStatus(board,i,j);
                changed=true;}}}



        return changed;

    }
    public static void printBoard(int[][] board, int semester_num){
        System.out.println("Semester Number "+semester_num+":");
        for(int i=0;i< board.length;i++){
            for(int j=0;j< board[0].length;j++) {
                if (board[i][j] == 0)
                    System.out.print("▯");
                else
                    System.out.print("▮");
            }
            System.out.println();
        }
    }
    public static void getStudentsIndexes(int[][] board, int m, int n)
    {
        System.out.println("Dear president, please enter the call's indexes.");
        String student_index = scanner.nextLine();
        while (!student_index.equals("Yokra")) {
            int index_m = Integer.parseInt(student_index.substring(0, student_index.indexOf((","))));
            int index_n = Integer.parseInt(student_index.substring(student_index.indexOf((",")) + 2, student_index.length()));
            if (index_m<m && index_m>=0 && index_n<n && index_n>=0){
                changeStatus(board, index_m, index_n);
                System.out.println("Dear president, please enter the call's indexes.");
            }
            else
                System.out.println("The cell is not within the board’s boundaries, enter a new cell.");
            student_index = scanner.nextLine();
        }


    }
    public static void changeStatus(int[][] board, int m, int n){
        if (board[m][n] == 0) board[m][n]=1;
        else board[m][n]=0;
    }

    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numberOfGames; i++) {
            System.out.println("Game number " + i + " starts.");
            theStudentsGame();
            System.out.println("Game number " + i + " ended.");
            System.out.println("-----------------------------------------------");
        }
        System.out.println("All games are ended.");
    }
}

