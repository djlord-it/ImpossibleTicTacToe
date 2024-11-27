import java.util.ArrayList;
import java.util.List;

public class TicTacToeAI {
    private static final int BOARD_SIZE = 3;

    // Main method to get the best move with strategic improvements
    public static int[] getBestMove(char[][] board, char aiPlayer) {
        // Prioritize center and corners in the opening moves
        if (isBoardEmpty(board)) {
            return new int[]{1, 1}; // Always take the center first
        }

        // Check for immediate winning move
        int[] winningMove = findWinningMove(board, aiPlayer);
        if (winningMove != null) return winningMove;

        // Block opponent's winning move
        char humanPlayer = (aiPlayer == 'X') ? 'O' : 'X';
        int[] blockingMove = findWinningMove(board, humanPlayer);
        if (blockingMove != null) return blockingMove;

        // Advanced strategy: look for strategic positions
        int[] strategicMove = findStrategicMove(board, aiPlayer);
        if (strategicMove != null) return strategicMove;

        // If no strategic move, use minimax
        return miniMaxMove(board, aiPlayer);
    }

    // Check if the board is completely empty
    private static boolean isBoardEmpty(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != '-') return false;
            }
        }
        return true;
    }

    // Find an immediate winning move
    private static int[] findWinningMove(char[][] board, char player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '-') {
                    // Try this move
                    board[i][j] = player;
                    boolean isWinningMove = isWinner(board, player);
                    // Undo the move
                    board[i][j] = '-';

                    if (isWinningMove) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    // Check if the current player has won
    private static boolean isWinner(char[][] board, char player) {
        // Check rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    // Find strategic moves
    private static int[] findStrategicMove(char[][] board, char aiPlayer) {
        // Prioritize corners if center is taken
        if (board[1][1] != '-') {
            int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
            for (int[] corner : corners) {
                if (board[corner[0]][corner[1]] == '-') {
                    return corner;
                }
            }
        }

        // Create fork opportunities
        List<int[]> forkMoves = findForkMoves(board, aiPlayer);
        if (!forkMoves.isEmpty()) {
            return forkMoves.get(0);
        }

        // Block opponent's potential forks
        char humanPlayer = (aiPlayer == 'X') ? 'O' : 'X';
        List<int[]> opponentForkMoves = findForkMoves(board, humanPlayer);
        if (!opponentForkMoves.isEmpty()) {
            return opponentForkMoves.get(0);
        }

        // Fallback to minimax move
        return null;
    }

    // Find potential fork moves
    private static List<int[]> findForkMoves(char[][] board, char player) {
        List<int[]> forkMoves = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '-') {
                    // Try this move
                    board[i][j] = player;

                    // Count potential winning lines
                    int winningLines = countPotentialWinningLines(board, player);

                    // Undo the move
                    board[i][j] = '-';

                    // If move creates multiple winning possibilities
                    if (winningLines >= 2) {
                        forkMoves.add(new int[]{i, j});
                    }
                }
            }
        }

        return forkMoves;
    }

    // Count potential winning lines
    private static int countPotentialWinningLines(char[][] board, char player) {
        int count = 0;

        // Check rows and columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Check row
            if (isLinePotentialWinner(board[i][0], board[i][1], board[i][2], player)) count++;

            // Check column
            if (isLinePotentialWinner(board[0][i], board[1][i], board[2][i], player)) count++;
        }

        // Check diagonals
        if (isLinePotentialWinner(board[0][0], board[1][1], board[2][2], player)) count++;
        if (isLinePotentialWinner(board[0][2], board[1][1], board[2][0], player)) count++;

        return count;
    }

    // Check if a line has potential for winning
    private static boolean isLinePotentialWinner(char a, char b, char c, char player) {
        int playerCount = 0;
        int emptyCount = 0;

        if (a == player) playerCount++;
        if (b == player) playerCount++;
        if (c == player) playerCount++;

        if (a == '-') emptyCount++;
        if (b == '-') emptyCount++;
        if (c == '-') emptyCount++;

        return playerCount == 1 && emptyCount == 2;
    }

    // Fallback to Minimax for complex scenarios
    private static int[] miniMaxMove(char[][] board, char aiPlayer) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = aiPlayer;
                    int score = minimax(board, 0, false, aiPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = '-';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    // Minimax algorithm with alpha-beta pruning
    private static int minimax(char[][] board, int depth, boolean isMaximizing, char aiPlayer, int alpha, int beta) {
        char humanPlayer = (aiPlayer == 'X') ? 'O' : 'X';

        int result = evaluateBoard(board, aiPlayer, humanPlayer);
        if (result != -1) return result;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = aiPlayer;
                        int score = minimax(board, depth + 1, false, aiPlayer, alpha, beta);
                        board[i][j] = '-';
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) break;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = humanPlayer;
                        int score = minimax(board, depth + 1, true, aiPlayer, alpha, beta);
                        board[i][j] = '-';
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, score);
                        if (beta <= alpha) break;
                    }
                }
            }
            return bestScore;
        }
    }

    // Evaluate board state
    private static int evaluateBoard(char[][] board, char aiPlayer, char humanPlayer) {
        // Check for winning conditions
        if (isWinner(board, aiPlayer)) return 10;
        if (isWinner(board, humanPlayer)) return -10;

        // Check for draw
        boolean isDraw = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '-') {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) break;
        }

        return isDraw ? 0 : -1;
    }
}