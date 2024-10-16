import java.util.Scanner;

public class TicTacToe {
    static char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}; // 3x3 board
    static Scanner scanner = new Scanner(System.in);

    public static void printBoard() {
        // Prints the current board
        for (int i = 0; i < 9; i += 3) {
            System.out.println("| " + board[i] + " | " + board[i + 1] + " | " + board[i + 2] + " |");
        }
        System.out.println();
    }

    public static boolean checkWinner(char player) {
        // Checks if the given player has won
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Horizontal
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Vertical
            {0, 4, 8}, {2, 4, 6}             // Diagonal
        };
        for (int[] condition : winConditions) {
            if (board[condition[0]] == player && 
                board[condition[1]] == player && 
                board[condition[2]] == player) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDraw() {
        // Checks if the game is a draw
        for (char cell : board) {
            if (cell == ' ') return false;
        }
        return true;
    }

    public static int minimax(boolean isMaximizing) {
        // Minimax algorithm for optimal AI move
        if (checkWinner('O')) return 1;   // AI wins
        if (checkWinner('X')) return -1;  // Player wins
        if (isDraw()) return 0;           // Draw

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'O';
                    int score = minimax(false);
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int score = minimax(true);
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    public static void aiMove() {
        // AI computes the best move using Minimax
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int score = minimax(false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        board[bestMove] = 'O';
    }

    public static void playerMove() {
        // Takes input from the player for their move
        System.out.print("Enter your move (1-9): ");
        int move = scanner.nextInt() - 1;

        if (move < 0 || move > 8 || board[move] != ' ') {
            System.out.println("Invalid move. Try again.");
            playerMove();
        } else {
            board[move] = 'X';
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe! You are 'X' and the AI is 'O'.");
        printBoard();

        while (true) {
            playerMove();
            printBoard();

            if (checkWinner('X')) {
                System.out.println("You win!");
                break;
            }
            if (isDraw()) {
                System.out.println("It's a draw!");
                break;
            }

            System.out.println("AI's turn...");
            aiMove();
            printBoard();

            if (checkWinner('O')) {
                System.out.println("AI wins!");
                break;
            }
            if (isDraw()) {
                System.out.println("It's a draw!");
                break;
            }
        }
    }
}
