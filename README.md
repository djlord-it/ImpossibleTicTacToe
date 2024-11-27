# Tic-Tac-Toe Game

This is a simple **Tic-Tac-Toe** game built using **Java**. The game allows a player to compete against either a human opponent or an AI opponent. The AI is implemented using a basic minimax algorithm, which evaluates all possible moves and selects the optimal one.

## Features

- **AI opponent:** Play against an AI that uses the minimax algorithm to determine the best moves.
- **Graphical User Interface (GUI):** The game is displayed with a clean, easy-to-use GUI built using **Swing**.
- **Optimized AI:** The AI evaluates each possible game state using the minimax algorithm, ensuring that the AI always plays optimally.

## How to Play

1. **Player vs. AI:** Select the first player (either X or O), and let the AI make its move automatically.
2. The first player to get three of their marks in a row (vertically, horizontally, or diagonally) wins.

## AI Algorithm: Minimax

The AI is based on the **Minimax** algorithm, which is a decision-making algorithm used in two-player games like Tic-Tac-Toe. Here's how it works:

1. **Game Tree Evaluation:**
    - The algorithm generates a tree of all possible moves from the current game state.
    - It then simulates each move until it reaches the end of the game (either a win, lose, or draw).

2. **Maximizing Player (AI):**
    - The AI attempts to maximize its chances of winning. It chooses the move that leads to the best possible outcome for itself, assuming the opponent also plays optimally.

3. **Minimizing Player (Opponent):**
    - The opponent's objective is to minimize the AI's score by blocking the AI's winning moves.

4. **Score Calculation:**
    - The terminal states of the game are assigned values:
        - **Win:** +1
        - **Draw:** 0
        - **Loss:** -1
    - The AI then evaluates the game tree by recursively calculating the scores for each potential move, and the best move is selected based on this score.

## Game Logic

The game follows the basic rules of Tic-Tac-Toe, where two players take turns marking a 3x3 grid. The game checks for a winner after each move. If a player gets three marks in a row, they win. If all spaces are filled and no player has won, the game ends in a draw.

### Key Methods:
- **Minimax Algorithm:** The core AI decision-making algorithm.
- **Evaluating the Game Board:** After every move, the game board is evaluated to determine if there is a winner, a draw, or if the game should continue.
- **Player Input:** Players can select their moves by clicking on the cells in the GUI.

## Dependencies

- **Java 8 or later:** This project is built using Java and requires Java 8 or later for execution.
- **Swing:** Used for the graphical user interface (GUI).

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/djlord-it/tic-tac-toe.git
    cd tic-tac-toe
    ```

2. **Compile the project:**

    ```bash
    javac *.java
    ```

3. **Run the game:**

    ```bash
    java Main
    ```

## Conclusion

This project demonstrates a classic Tic-Tac-Toe game with a simple yet effective AI implementation using the minimax algorithm. You can challenge a friend or test your skills against the AI!

## License

MIT
