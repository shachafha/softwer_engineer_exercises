package hw_0;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;  // Note: Do not change this line.

    public static void theStudentsGame() {
        System.out.println("Dear president, please enter the board’s size.");
        String boardSizes = scanner.nextLine();
        int boardM = Integer.parseInt(boardSizes.substring(0, boardSizes.indexOf(("X")) - 1));
        int boardN = Integer.parseInt(boardSizes.substring(boardSizes.indexOf(("X")) + 2));
        int[][] board = new int[boardM][boardN];
        getStudentsIndexes(board, boardM, boardN);
        printBoard(board, boardM, boardN, 1);
        int i = 2;
        boolean changed = updateStatus(board, boardM, boardN);

        while (i <= 1000 && changed) {
            printBoard(board, boardM, boardN, i);
            i++;
            changed = updateStatus(board, boardM, boardN);
        }
        endGame(board, boardM, boardN, i, changed);

    }

    public static boolean updateStatus(int[][] board, int m, int n) {
        int[][] auxBoard = new int[m][n];
        boolean changed = false;
        int validFriends;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                validFriends = countValidFriends(board, m, n, i, j);
                if ((board[i][j] == 1) && (validFriends <= 1 || validFriends > 3))
                    auxBoard[i][j] = 1;
                if (board[i][j] == 0 && validFriends == 3)
                    auxBoard[i][j] = 1;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (auxBoard[i][j] == 1) {
                    changeStatus(board, i, j);
                    changed = true;
                }
            }
        }
        return changed;

    }

    public static int countValidFriends(int[][] board, int m, int n, int Mindex, int Nindex) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isBound(m, n, Mindex + i, Nindex + j) && !(i == 0 && j == 0)) {
                    if (board[Mindex + i][Nindex + j] == 1)
                        counter++;
                }
            }
        }

        return counter;
    }

    public static boolean isBound(int m, int n, int Mindex, int Nindex) {
        return Mindex >= 0 && Nindex >= 0 && Mindex < m && Nindex < n;
    }

    public static void printBoard(int[][] board, int m, int n, int semesterNum) {
        System.out.println("Semester number " + semesterNum + ":");
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
        String studentIndex = scanner.nextLine();
        while (!studentIndex.equals("Yokra")) {
            int indexM = Integer.parseInt(studentIndex.substring(0, studentIndex.indexOf((","))));
            int indexN = Integer.parseInt(studentIndex.substring(studentIndex.indexOf((",")) + 2));
            if (indexM < m && indexM >= 0 && indexN < n && indexN >= 0) {
                changeStatus(board, indexM, indexN);
                System.out.println("Dear president, please enter the cell’s indexes.");
            } else
                System.out.println("The cell is not within the board’s boundaries, enter a new cell.");
            studentIndex = scanner.nextLine();
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

