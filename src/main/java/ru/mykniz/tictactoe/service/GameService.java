package ru.mykniz.tictactoe.service;

import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mykniz.tictactoe.entity.Board;
import ru.mykniz.tictactoe.repository.GameRepository;

import java.util.InputMismatchException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

/**
 * @author Shundeev Nick
 */
@Service
public class GameService {
    @Getter
    private String turn = "X";
    @Getter
    private boolean isWin;
    private static final String[] BOARD = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final String DASH = "|---|---|---|";
    private String winner;
    private Long currentBoardId;

    @Autowired
    private GameRepository gameRepository;

    public void createBoard() {
        if (gameRepository.findAll().isEmpty()) {
            val rowsBuilder = convertArrToRows(BOARD);
            val board = Board
                    .builder()
                    .rows(rowsBuilder.toString())
                    .build();
            gameRepository.save(board);
        }
        val currentBoard = gameRepository.findAll().stream().findFirst().get();
        currentBoardId = currentBoard.getId();
    }

    private StringBuilder convertArrToRows(String[] board) {
        val rowsBuilder = new StringBuilder();
        val rows = List.of(
                "| " + board[0] + " | " + board[1] + " | " + board[2] + " |",
                "| " + board[3] + " | " + board[4] + " | " + board[5] + " |",
                "| " + board[6] + " | " + board[7] + " | " + board[8] + " |");

        for (String s : rows) {
            rowsBuilder.append(s).append(",");
        }
        rowsBuilder.deleteCharAt(rowsBuilder.length() - 1);
        return rowsBuilder;
    }

    public String[] getBoardRows() {
        return getBoard()
                .getRows()
                .split(",");
    }

    private String[] getBoardElements(Board board) {
        return board
                .getRows()
                .replaceAll("[^0-9OX]", "")
                .split("");
    }

    public Board getBoard() {
        return gameRepository
                .findById(currentBoardId)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public String getResult(String position) {
        val board = getBoard();
        val currentBoardElements = getBoardElements(board);
        if (!isWin) {
            int numInput;
            try {
                numInput = Integer.parseInt(position);
                if (!(numInput > 0 && numInput <= 9)) {
                    throw new RuntimeException("Invalid input; re-enter slot number:");
                }
            } catch (InputMismatchException e) {
                throw new RuntimeException("Invalid input; re-enter slot number:");
            }

            if (currentBoardElements[numInput - 1].equals(String.valueOf(numInput))) {
                currentBoardElements[numInput - 1] = turn;

                val rowsBuilder = convertArrToRows(currentBoardElements);
                board.setRows(rowsBuilder.toString());
                gameRepository.save(board);

                if (turn.equals("X")) {
                    turn = "O";
                } else {
                    turn = "X";
                }
                winner = checkWinner(currentBoardElements);
            } else {
                throw new RuntimeException("Slot already taken; re-enter slot number:");
            }
        }

        if (isNull(winner)) {
            return turn + "'s turn; enter a slot number to place " + turn + " in:";
        }
        if (winner.equalsIgnoreCase("draw")) {
            return "It's a draw! Thanks for playing.";
        }

        return "Congratulations! " + winner + "'s have won! Thanks for playing.";
    }

    private String checkWinner(String[] board) {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;
            }

            if (line.equals("XXX")) {
                isWin = true;
                return "X";
            } else if (line.equals("OOO")) {
                isWin = true;
                return "O";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (asList(board).contains(String.valueOf(a + 1))) {
                break;
            } else if (a == 8) {
                isWin = true;
                return "draw";
            }
        }

        return null;
    }

    public void restore() {
        isWin = false;
        turn = "X";
    }

    public void restoreAll() {
        gameRepository.deleteAll();
        restore();
    }
}
