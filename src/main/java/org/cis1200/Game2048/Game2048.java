package org.cis1200.Game2048;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class is a model for 2048.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of 2048,
 * visualized with Strings printed to the console.
 */
public class Game2048 {

    private int[][] board;
    private boolean gameOver;

    private LinkedList<int[][]> history = new LinkedList<>();
    private int[][] prevBoard = new int[4][4];

    private boolean clickUndo;

    /**
     * Constructor sets up game state.
     */
    public Game2048() {
        reset();
    }

    public int[][] getBoard() {
        int[][] boardCopy = new int[4][4];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = board[i].clone();
        }
        return boardCopy;
    }

    public void setBoard(int[][] newBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                this.board[i][j] = newBoard[i][j];
            }
        }
    }

    /**
     * checkWin checks whether the game has reached a win condition.
     * 
     * @return true if the player has won, and false otherwise
     */
    public boolean checkWin() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 2048) { // checks if there is a 2048 cell
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkGameOver() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    return false; // checks if there is an empty space
                }

                if (j < board[i].length - 1 && board[i][j] == board[i][j + 1]) {
                    return false; // checks if cell on the left matches cell on the right
                }

                if (i < board.length - 1 && board[i][j] == board[i + 1][j]) {
                    return false; // checks if cell above matches cell below
                }
            }
        }
        return true;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("Current game state:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new int[4][4];
        gameOver = false;
        history = new LinkedList<>();
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty
     */
    public int getCell(int r, int c) {
        return board[r][c];
    }

    public int setCell(int r, int c, int val) {
        board[r][c] = val; // sets cell to a new number
        return val;
    }

    public void populate() {
        boolean put2 = false;
        while (!put2) {
            int row = (int) (Math.random() * 4); // randomly chooses row
            int col = (int) (Math.random() * 4); // randomly chooses col

            if (getCell(row, col) == 0) {
                setCell(row, col, 2); // sets cell to 2 if there is an empty space
                put2 = true;
            } else {
                return;
            }
        }
    }

    public boolean moveRight() {
        history.add(getBoard());
        boolean moved = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = board[i].length - 1; j >= 0; j--) { // iterate from right to left
                if (board[i][j] != 0) {
                    int tempJ = j;
                    while (tempJ + 1 < board[i].length && board[i][tempJ + 1] == 0) {
                        // shift one to the right
                        moved = true;
                        int curr = board[i][tempJ];
                        setCell(i, tempJ + 1, curr);
                        setCell(i, tempJ, 0);
                        tempJ++;
                    }
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = board[i].length - 1; j >= 0; j--) {
                if (j > 0 && board[i][j] == board[i][j - 1]) {
                    moved = true;
                    setCell(i, j, board[i][j] * 2); // merges
                    setCell(i, j - 1, 0); // sets left to 0
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = board[i].length - 1; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int tempJ = j;
                    while (tempJ + 1 < board[i].length && board[i][tempJ + 1] == 0) {
                        // shift one to the right
                        moved = true;
                        int curr = board[i][tempJ];
                        setCell(i, tempJ + 1, curr);
                        setCell(i, tempJ, 0);
                        tempJ++;
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveLeft() {
        history.add(getBoard());
        boolean moved = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) { // iterate from left to right
                if (board[i][j] != 0) {
                    int tempJ = j;
                    while (tempJ - 1 >= 0 && board[i][tempJ - 1] == 0) {
                        // shift one to the left
                        moved = true;
                        int curr = board[i][tempJ];
                        setCell(i, tempJ - 1, curr);
                        setCell(i, tempJ, 0);
                        tempJ--;
                    }
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length - 1; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i][j + 1]) {
                    setCell(i, j, board[i][j] * 2); // merges
                    setCell(i, j + 1, 0); // sets right to 0
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0) {
                    int tempJ = j;
                    while (tempJ - 1 >= 0 && board[i][tempJ - 1] == 0) {
                        moved = true;
                        int curr = board[i][tempJ];
                        setCell(i, tempJ - 1, curr);
                        setCell(i, tempJ, 0);
                        tempJ--;
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveUp() {
        history.add(getBoard());
        boolean moved = false;
        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board.length; i++) { // iterate from top to bottom
                if (board[i][j] != 0) {
                    int tempI = i;
                    while (tempI - 1 >= 0 && board[tempI - 1][j] == 0) {
                        // shift one up
                        moved = true;
                        int curr = board[tempI][j];
                        setCell(tempI - 1, j, curr);
                        setCell(tempI, j, 0);
                        tempI--;
                    }
                }
            }
        }

        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board.length - 1; i++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j]) {
                    setCell(i, j, board[i][j] * 2); // merges
                    setCell(i + 1, j, 0); // sets below to 0
                }
            }
        }

        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] != 0) {
                    int tempI = i;
                    while (tempI - 1 >= 0 && board[tempI - 1][j] == 0) {
                        moved = true;
                        int curr = board[tempI][j];
                        setCell(tempI - 1, j, curr);
                        setCell(tempI, j, 0);
                        tempI--;
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveDown() {
        history.add(getBoard());
        boolean moved = false;
        for (int j = 0; j < board.length; j++) {
            for (int i = board.length - 1; i >= 0; i--) { // iterate from bottom to top
                if (board[i][j] != 0) {
                    int tempI = i;
                    while (tempI + 1 < board.length && board[tempI + 1][j] == 0) {
                        // shift one down
                        moved = true;
                        int curr = board[tempI][j];
                        setCell(tempI + 1, j, curr);
                        setCell(tempI, j, 0);
                        tempI++;
                    }
                }
            }
        }

        for (int j = 0; j < board.length; j++) {
            for (int i = board.length - 1; i > 0; i--) {
                if (board[i][j] != 0 && board[i][j] == board[i - 1][j]) {
                    setCell(i, j, board[i][j] * 2); // merges
                    setCell(i - 1, j, 0); // sets above to 0
                }
            }
        }

        for (int j = 0; j < board.length; j++) {
            for (int i = board.length - 1; i >= 0; i--) {
                if (board[i][j] != 0) {
                    int tempI = i;
                    while (tempI + 1 < board.length && board[tempI + 1][j] == 0) {
                        moved = true;
                        int curr = board[tempI][j];
                        setCell(tempI + 1, j, curr);
                        setCell(tempI, j, 0);
                        tempI++;
                    }
                }
            }
        }
        return moved;
    }

    public void saveState(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    System.out.println(board[i][j]);
                    bw.write(board[i][j] + ",");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void loadState(String filePath) {
        this.board = new int[4][4];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            System.out.println("loading");

            while ((line = reader.readLine()) != null && row < 4) {
                System.out.print(line);
                String[] boardInts = line.split(",");
                for (int col = 0; col < boardInts.length && col < 4; col++) {
                    board[row][col] = Integer.parseInt(boardInts[col]);
                }
                row++;
            }
            System.out.println(Arrays.deepToString(this.board));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void undo() {
        if (!history.isEmpty()) {
            prevBoard = board;
            board = history.remove(history.size() - 1);
            clickUndo = true;
        }
    }

    public void redo() {
        if (clickUndo) {
            history.add(board);
            board = prevBoard;
        }
        clickUndo = false;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Game2048 game = new Game2048();
    }
}
