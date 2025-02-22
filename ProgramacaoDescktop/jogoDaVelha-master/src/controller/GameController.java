package controller;

import exception.ChestCaseOccupiedException;
import exception.OutOfChestException;
import model.Pos;

public class GameController {
    public static final int BOARD_SIZE = 3;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = -1;
    public static final int NO_PLAYER = 0;

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int winner;
    private int currentPlayer;

    public GameController() {
        reiniciar();
    }

    public boolean jogar(int x, int y) {
        validatePosition(x, y);
        return makeMove(x, y);
    }

    private void validatePosition(int x, int y) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            throw new OutOfChestException("Position out of bounds: (" + x + ", " + y + ")");
        }
        if (board[x][y] != NO_PLAYER) {
            throw new ChestCaseOccupiedException("Position already occupied: (" + x + ", " + y + ")");
        }
    }

    private boolean makeMove(int x, int y) {
        board[x][y] = currentPlayer;
        currentPlayer *= -1;
        return checkWinner();
    }

    private boolean checkWinner() {
        return checkRows() || checkColumns() || checkDiagonals() || checkDraw();
    }

    private boolean checkDraw() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == NO_PLAYER) return false;
            }
        }
        winner = NO_PLAYER;
        return true;
    }

    private boolean checkDiagonals() {
        int mainDiag = 0, antiDiag = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            mainDiag += board[i][i];
            antiDiag += board[i][BOARD_SIZE - 1 - i];
        }
        return checkSum(mainDiag) || checkSum(antiDiag);
    }

    private boolean checkRows() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            int sum = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                sum += board[i][j];
            }
            if (checkSum(sum)) return true;
        }
        return false;
    }

    private boolean checkColumns() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            int sum = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                sum += board[i][j];
            }
            if (checkSum(sum)) return true;
        }
        return false;
    }

    private boolean checkSum(int sum) {
        if (sum == BOARD_SIZE * PLAYER_1) {
            winner = PLAYER_1;
            return true;
        }
        if (sum == BOARD_SIZE * PLAYER_2) {
            winner = PLAYER_2;
            return true;
        }
        return false;
    }

    public void reiniciar() {
        currentPlayer = PLAYER_1;
        winner = NO_PLAYER;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = NO_PLAYER;
            }
        }
    }
}
