package org.cis1200.Game2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class instantiates a 2048 object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Game2048 board; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        requestFocusInWindow();

        board = new Game2048(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        addKeyListener(new KeyAdapter() {
            boolean result = false;

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    result = board.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    result = board.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    result = board.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    result = board.moveUp();
                }

                if (result) {
                    repaint();
                    board.populate();
                }
                updateStatus();
                repaint();
            }
        });
    }

    public String instructions() {
        String instructions = "This is 2048. Use your arrow keys to move the tiles." +
                "Tiles with the same number merge into one when they touch. " +
                "Add them up to reach 2048!";
        requestFocusInWindow();
        return instructions;
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        board.reset();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();

        status.setText("Playing...");
        board.populate();
        board.populate();
    }

    public void saveBoard() {
        String fp = "files/savedState.txt";
        board.saveState(fp);
    }

    public void loadBoard() {
        board.loadState("files/savedState.txt");
        repaint();
        requestFocusInWindow();
    }

    public void undoBoard() {
        board.undo();
        repaint();
        requestFocusInWindow();
    }

    public void redoBoard() {
        board.redo();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        boolean win = board.checkWin();
        if (win) {
            status.setText("You won!");
        }

        boolean gameOver = board.checkGameOver();
        if (gameOver) {
            status.setText("Game over.");
        }
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        int unitWidth = BOARD_WIDTH / 4;
        int unitHeight = BOARD_HEIGHT / 4;

        g.drawLine(unitWidth, 0, unitWidth, BOARD_HEIGHT);
        g.drawLine(unitWidth * 2, 0, unitWidth * 2, BOARD_HEIGHT);
        g.drawLine(unitWidth * 3, 0, unitWidth * 3, BOARD_HEIGHT);
        g.drawLine(0, unitHeight, BOARD_WIDTH, unitHeight);
        g.drawLine(0, unitHeight * 2, BOARD_WIDTH, unitHeight * 2);
        g.drawLine(0, unitHeight * 3, BOARD_WIDTH, unitHeight * 3);

        // Draws numbers
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int curr = board.getCell(i, j);
                if (curr != 0) {
                    String number = Integer.toString(curr);
                    int xCoord = j * unitWidth + (unitWidth - 10) / 2;
                    int yCoord = i * unitHeight + (unitHeight - 10) / 2 + 10;
                    g.drawString(number, xCoord, yCoord);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

}
