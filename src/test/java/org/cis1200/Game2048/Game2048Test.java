package org.cis1200.Game2048;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class Game2048Test {
    // checkWin tests
    @Test
    public void testCheckWinIsWin() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 4, 2, 2 },
            { 4, 2048, 2, 2 },
            { 2, 4, 2, 2 },
            { 8, 2, 4, 2 } };
        game.setBoard(board);
        assertTrue(game.checkWin());
    }

    @Test
    public void testCheckWinIsNotWin() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 4, 2, 2 },
            { 4, 16, 2, 2 },
            { 2, 4, 2, 2 },
            { 8, 2, 4, 2 } };
        game.setBoard(board);
        assertFalse(game.checkWin());
    }

    // checkGameOver tests
    @Test
    public void testGameOverIsNotOver() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 4, 2, 2 },
            { 4, 0, 0, 2 },
            { 2, 4, 2, 2 },
            { 8, 2, 4, 2 } };
        game.setBoard(board);
        assertFalse(game.checkGameOver());
    }

    @Test
    public void testGameOverIsOver() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 2, 4, 16, 2 },
            { 4, 2, 32, 4 },
            { 2, 4, 2, 8 },
            { 4, 2, 4, 2 } };
        game.setBoard(board);
        assertTrue(game.checkGameOver());
    }

    // reset test
    @Test
    public void testReset() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 0, 0, 2 },
            { 0, 0, 0, 2 },
            { 0, 0, 2, 2 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.moveLeft();
        game.reset();
        assertArrayEquals(game.getBoard(), new int[4][4]);
    }

    // getCell test
    @Test
    public void testGetCell() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 0, 0, 2 },
            { 0, 2, 0, 2 },
            { 0, 0, 2, 2 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        int cell = game.getCell(1, 1);
        assertEquals(cell, 2);
    }

    // setCell test
    @Test
    public void testSetCell() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 0, 0, 2 },
            { 0, 2, 0, 2 },
            { 0, 0, 2, 2 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.setCell(1, 1, 4);
        assertEquals(game.getCell(1, 1), 4);
    }

    // populate tests
    @Test
    public void testPopulateEmptyBoard() {
        Game2048 game = new Game2048();
        game.populate();
        int populatedCells = 0;
        int value = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.getCell(i, j) != 0) {
                    populatedCells++;
                    value = game.getCell(i, j);
                }
            }
        }
        assertEquals(populatedCells, 1);
        assertEquals(2, value);
    }

    @Test
    public void testPopulateOnFullBoard() {
        Game2048 game = new Game2048();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                game.setCell(i, j, 4);
            }
        }
        int[][] ogBoard = game.getBoard();
        game.populate();

        int[][] newBoard = game.getBoard();
        assertArrayEquals(ogBoard, newBoard);
    }

    // move tests
    @Test
    public void testMoveRight() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 0, 0, 0, 0 },
            { 2, 2, 0, 0 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.moveRight();

        int[][] newBoard = new int[][] {
            { 0, 0, 4, 4 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 4 },
            { 0, 0, 0, 0 } };
        assertArrayEquals(game.getBoard(), newBoard);
    }

    @Test
    public void testMoveLeft() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 2, 2, 4 },
            { 0, 0, 0, 0 },
            { 2, 2, 0, 0 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.moveLeft();

        int[][] newBoard = new int[][] {
            { 4, 4, 0, 0 },
            { 0, 0, 0, 0 },
            { 4, 0, 0, 0 },
            { 0, 0, 0, 0 } };
        assertArrayEquals(game.getBoard(), newBoard);
    }

    @Test
    public void testMoveUp() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 0, 2, 4 },
            { 2, 0, 0, 0 },
            { 2, 0, 0, 0 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.moveUp();

        int[][] newBoard = new int[][] {
            { 4, 0, 2, 4 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 } };
        assertArrayEquals(game.getBoard(), newBoard);
    }

    @Test
    public void testMoveDown() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 0, 2, 4 },
            { 2, 0, 0, 0 },
            { 2, 0, 0, 0 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.moveDown();

        int[][] newBoard = new int[][] {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 4, 0, 2, 4 } };
        assertArrayEquals(game.getBoard(), newBoard);
    }

    // saveState test
    @Test
    public void testSaveState() {
        Game2048 game = new Game2048();
        String fp = "files/testState2.txt";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                game.setCell(i, j, 4);
            }
        }
        game.saveState(fp);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fp));
            assertEquals("4,4,4,4,", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loadState test
    @Test
    public void testLoadState() {
        Game2048 game = new Game2048();
        String fp = "files/testState.txt";
        int[][] ogBoard = new int[][] {
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 4, 0, 2, 4 } };

        game.loadState(fp);
        System.out.println(Arrays.deepToString(game.getBoard()));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(ogBoard[i][j], game.getBoard()[i][j]);
            }
        }
    }

    // undo test
    @Test
    public void testUndo() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 0, 2, 2 },
            { 2, 0, 0, 0 },
            { 2, 0, 2, 2 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.moveRight();
        game.moveUp();
        game.undo();
        game.undo();
        assertArrayEquals(game.getBoard(), board);
    }

    // redo test
    @Test
    public void testRedo() {
        Game2048 game = new Game2048();
        int[][] board = new int[][] {
            { 0, 0, 2, 2 },
            { 2, 0, 0, 0 },
            { 2, 0, 2, 2 },
            { 0, 0, 0, 0 } };
        game.setBoard(board);
        game.moveRight();
        game.undo();
        game.redo();
        int[][] newBoard = new int[][] {
            { 0, 0, 0, 4 },
            { 0, 0, 0, 2 },
            { 0, 0, 2, 4 },
            { 0, 0, 0, 0 } };
        assertArrayEquals(game.getBoard(), newBoard);
    }
}
