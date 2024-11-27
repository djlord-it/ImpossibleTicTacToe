public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;
    private char winner;

    public TicTacToe() {
        board = new char[3][3];
        initializeBoard();
        currentPlayer = 'X';
        gameOver = false;
        winner = '-';
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != '-' || gameOver) {
            return false;
        }

        board[row][col] = currentPlayer;

        if (checkWinner()) {
            gameOver = true;
            winner = currentPlayer;
        } else if (isBoardFull()) {
            gameOver = true;
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        return true;
    }

    private boolean checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer &&
                    board[i][1] == currentPlayer &&
                    board[i][2] == currentPlayer) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == currentPlayer &&
                    board[1][j] == currentPlayer &&
                    board[2][j] == currentPlayer) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][2] == currentPlayer) {
            return true;
        }

        if (board[0][2] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public char getWinner() {
        return winner;
    }
    //reset game button
    public void resetGame() {
        initializeBoard();
        currentPlayer = 'X';
        gameOver = false;
        winner = '-';
    }
}