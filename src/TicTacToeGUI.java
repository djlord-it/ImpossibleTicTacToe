import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private TicTacToe game;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private boolean playerTurn;

    public TicTacToeGUI() {
        game = new TicTacToe();
        playerTurn = true;

        setTitle("Impossible TicTacToe ❌⭕️");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create game board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i, col = j;
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (playerTurn && game.makeMove(row, col)) {
                            updateButton(row, col);

                            if (!game.isGameOver()) {
                                // AI's turn
                                playerTurn = false;
                                SwingUtilities.invokeLater(() -> {
                                    aiMove();
                                });
                            }
                        }
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }

        // Status and Reset Panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        statusLabel = new JLabel("Player X's Turn");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton resetButton = new JButton("Wanna Retry ? 🤣");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        controlPanel.add(statusLabel);
        controlPanel.add(resetButton);

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    private void aiMove() {
        int[] move = TicTacToeAI.getBestMove(game.getBoard(), 'O');
        game.makeMove(move[0], move[1]);
        updateButton(move[0], move[1]);
        playerTurn = true;
        checkGameStatus();
    }

    private void updateButton(int row, int col) {
        buttons[row][col].setText(String.valueOf(game.getBoard()[row][col]));
        buttons[row][col].setEnabled(game.getBoard()[row][col] == '-');
        checkGameStatus();
    }

    private void checkGameStatus() {
        if (game.isGameOver()) {
            if (game.getWinner() == '-') {
                statusLabel.setText("Oops!🤭");
                disableAllButtons();
            } else {
                statusLabel.setText(game.getWinner() + " Wins!😂😂");
                disableAllButtons();
            }
        } else {
            statusLabel.setText(game.getCurrentPlayer() + "'s Turn");
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        game.resetGame();
        playerTurn = true;
        statusLabel.setText("Player X's Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}