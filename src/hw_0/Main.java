package hw_0;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final int MAX_SEMESTER =1000;
    private static final int PERFECT_NUM_OF_FRIENDS =3;
    private static final int SKIP_THE_SPACE =2;
    private static final int DELETE_THE_SPACE =-1;
    private static final int CHANGE_STUDENT_STATUS =1;
    private static final String ACADEMICALLY_INVALID ="▯";
    private static final String ACADEMICALLY_VALID ="▮";
    public static Scanner scanner; // Note: Do not change this line.


    /**
     * Runs one game, get board size from president and creates the board.
     * use other funcs to update the board and to end the game.
     */
    public static void theStudentsGame() {
        System.out.println("Dear president, please enter the board’s size.");
        String boardSizes = scanner.nextLine();
        int boardM = Integer.parseInt(boardSizes.substring(0, boardSizes.indexOf(("X")) +DELETE_THE_SPACE));
        int boardN = Integer.parseInt(boardSizes.substring(boardSizes.indexOf(("X")) + SKIP_THE_SPACE));
        int[][] board = new int[boardM][boardN];
        getStudentsIndexes(board, boardM, boardN);
        printBoard(board, boardM, boardN, 1);
        int i = 2;
        boolean changed = updateStatus(board, boardM, boardN);

        while (i <= MAX_SEMESTER && changed) {
            printBoard(board, boardM, boardN, i);
            i++;
            changed = updateStatus(board, boardM, boardN);
        }
        endGame(board, boardM, boardN, i, changed);

    }
    /**
     * this func gets then board and its sizes and
     * Updates students statuses based on those rules:
     * if a student is valid and has 1 or more than 3 valid friends
     * if a student is invalid and has 3 valid friends
     * it returns true if the board changed and false otherwise
     */
    public static boolean updateStatus(int[][] board, int m, int n) {
        int[][] auxBoard = new int[m][n];
        boolean changed = false;
        int validFriends;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                validFriends = countValidFriends(board, m, n, i, j);
                if ((board[i][j] == 1) && (validFriends <= 1 || validFriends > PERFECT_NUM_OF_FRIENDS))
                    auxBoard[i][j] = CHANGE_STUDENT_STATUS;
                if (board[i][j] == 0 && validFriends == PERFECT_NUM_OF_FRIENDS)
                    auxBoard[i][j] = CHANGE_STUDENT_STATUS;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (auxBoard[i][j] == CHANGE_STUDENT_STATUS) {
                    changeStatus(board, i, j);
                    changed = true;
                }
            }
        }
        return changed;

    }
    /**
     * gets the board, it's size and the current student's place.
     * returns the number of academic valid friends.
     */
    public static int countValidFriends(int[][] board, int m, int n, int mIndex, int nIndex) {
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isBound(m, n, mIndex + i, nIndex + j) && !(i == 0 && j == 0)) {
                    if (board[mIndex + i][nIndex + j] == 1)
                        counter++;
                }
            }
        }

        return counter;
    }
    /**
     * gets the board sizes and a specific location
     * returns true if the location is inside the board anf false otherwise.
     */
    public static boolean isBound(int m, int n, int mIndex, int nIndex) {
        return mIndex >= 0 && nIndex >= 0 && mIndex < m && nIndex < n;
    }
    /**
     * gets the board , its size and the semester number
     * prints messages and the board
     */
    public static void printBoard(int[][] board, int m, int n, int semesterNum) {
        System.out.println("Semester number " + semesterNum + ":");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0)
                    System.out.print(ACADEMICALLY_INVALID);
                else
                    System.out.print(ACADEMICALLY_VALID);
            }
            System.out.println();
        }
        System.out.println("Number of students: " + numOfValidStudents(board, m, n));
        System.out.println();
    }

    /**
     * gets the board , its size, last semester number and if the board changed in the last turn
     * prints the end message
     */
    public static void endGame(int[][] board, int m, int n, int lastSemesterNum, boolean changed) {
        if (numOfValidStudents(board, m, n) == 0)
            System.out.println("There are no more students.");
        else if (!changed)
            System.out.println("The students have stabilized.");
        else if (lastSemesterNum == MAX_SEMESTER+1) {
            System.out.println("The semesters limitation is over.");
        }

    }

    /**
     * gets the board and its size
     * returns the number of academic valid students
     */
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

    /**
     * gets the board and its size
     * scans the students, places and changes their status
     */
    public static void getStudentsIndexes(int[][] board, int m, int n) {
        System.out.println("Dear president, please enter the cell’s indexes.");
        String studentIndex = scanner.nextLine();
        while (!studentIndex.equals("Yokra")) {
            int indexM = Integer.parseInt(studentIndex.substring(0, studentIndex.indexOf((","))));
            int indexN = Integer.parseInt(studentIndex.substring(studentIndex.indexOf((",")) + SKIP_THE_SPACE));
            if (isBound(m,n,indexM,indexN)) {
                changeStatus(board, indexM, indexN);
                System.out.println("Dear president, please enter the cell’s indexes.");
            } else
                System.out.println("The cell is not within the board’s boundaries, enter a new cell.");
            studentIndex = scanner.nextLine();
        }


    }
    /**
     * gets the board and a place in the board and change its value
     */
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

