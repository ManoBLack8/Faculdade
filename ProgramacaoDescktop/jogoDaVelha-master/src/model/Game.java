package model;

public class Game {
    public static final int BOARD_SIZE = 3;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = -1;
    public static final int NO_PLAYER = 0;

    private int[][] board;
    private int currentPlayer;
    private int winner;

    public Game() {
        reset();
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public boolean play(int x, int y) {
        if (!isValidMove(x, y)) {
            return false;
        }

        board[x][y] = currentPlayer;
        if (checkWinner(x, y)) {
            winner = currentPlayer;
            return true;
        }

        currentPlayer = (currentPlayer == PLAYER_1) ? PLAYER_2 : PLAYER_1;
        return false;
    }

    public void reset() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PLAYER_1;
        winner = NO_PLAYER;
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == NO_PLAYER;
    }

    private boolean checkWinner(int x, int y) {
        return checkRow(x) || checkColumn(y) || checkDiagonals();
    }

    private boolean checkRow(int row) {
        int sum = 0;
        for (int j = 0; j < BOARD_SIZE; j++) {
            sum += board[row][j];
        }
        return Math.abs(sum) == BOARD_SIZE;
    }

    private boolean checkColumn(int col) {
        int sum = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            sum += board[i][col];
        }
        return Math.abs(sum) == BOARD_SIZE;
    }

    private boolean checkDiagonals() {
        int mainDiag = 0, antiDiag = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            mainDiag += board[i][i];
            antiDiag += board[i][BOARD_SIZE - 1 - i];
        }
        return Math.abs(mainDiag) == BOARD_SIZE || Math.abs(antiDiag) == BOARD_SIZE;
    }
}
