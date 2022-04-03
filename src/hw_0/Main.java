package hw_0;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;  // Note: Do not change this line.

    public static void theStudentsGame() {
        System.out.println("Dear president, please enter the board’s size.");
        String board_sizes = scanner.nextLine();
        int board_m = Integer.parseInt(board_sizes.substring(0, board_sizes.indexOf(("X")) - 1));
        int board_n = Integer.parseInt(board_sizes.substring(board_sizes.indexOf(("X")) + 2));
        int[][] board = new int[board_m][board_n];
        getStudentsIndexes(board, board_m, board_n);
        printBoard(board, board_m, board_n, 1);
        int i = 2;
        boolean changed = updateStatus(board, board_m, board_n);
        ;
        while (i <= 1000 && changed) {
            printBoard(board, board_m, board_n, i);
            i++;
            changed = updateStatus(board, board_m, board_n);
        }
        endGame(board, board_m, board_n, i, changed);

    }

    public static boolean updateStatus(int[][] board, int m, int n) {
        int[][] aux_board = new int[m][n];
        boolean changed = false;
        int valid_friends;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                valid_friends = countValidFriends(board, m, n, i, j);
                if ((board[i][j] == 1) && (valid_friends <= 1 || valid_friends > 3))
                    aux_board[i][j] = 1;
                if (board[i][j] == 0 && valid_friends == 3)
                    aux_board[i][j] = 1;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (aux_board[i][j] == 1) {
                    changeStatus(board, i, j);
                    changed = true;
                }
            }
        }
        return changed;

    }

    public static int countValidFriends(int[][] board, int m, int n, int m_index, int n_index) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isBound(m, n, m_index + i, n_index + j) && !(i == 0 && j == 0)) {
                    if (board[m_index + i][n_index + j] == 1)
                        counter++;
                }
            }
        }

        return counter;
    }

    public static boolean isBound(int m, int n, int m_index, int n_index) {
        return m_index >= 0 && n_index >= 0 && m_index < m && n_index < n;
    }

    public static void printBoard(int[][] board, int m, int n, int semester_num) {
        System.out.println("Semester number " + semester_num + ":");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0)
                    System.out.print("▯");
                else
                    System.out.print("▮");
            }
            System.out.println();
        }
        System.out.println("Number of students: " + numOfValidStudents(board, m, n));
        System.out.println();
    }

    public static void endGame(int[][] board, int m, int n, int i, boolean changed) {
        if (numOfValidStudents(board, m, n) == 0)
            System.out.println("There are no more students.");
        else if (!changed)
            System.out.println("The students have stabilized.");
        else if (i == 1001) {
            System.out.println("The semesters limitation is over.");
        }

    }

    public static int numOfValidStudents(int[][] board, int m, int n) {
        int counter = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1)
                    counter++;
            }
        }
        return counter;
    }

    public static void getStudentsIndexes(int[][] board, int m, int n) {
        System.out.println("Dear president, please enter the cell’s indexes.");
        String student_index = scanner.nextLine();
        while (!student_index.equals("Yokra")) {
            int index_m = Integer.parseInt(student_index.substring(0, student_index.indexOf((","))));
            int index_n = Integer.parseInt(student_index.substring(student_index.indexOf((",")) + 2));
            if (index_m < m && index_m >= 0 && index_n < n && index_n >= 0) {
                changeStatus(board, index_m, index_n);
                System.out.println("Dear president, please enter the cell’s indexes.");
            } else
                System.out.println("The cell is not within the board’s boundaries, enter a new cell.");
            student_index = scanner.nextLine();
        }


    }

    public static void changeStatus(int[][] board, int m, int n) {
        if (board[m][n] == 0) board[m][n] = 1;
        else board[m][n] = 0;
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

