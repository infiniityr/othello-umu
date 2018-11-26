import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class OthelloPosition {
    public static final int BOARD_SIZE = 8;

    public boolean playerToMove;

    /**
     * O => White
     * X => Black
     */
    public char[][] board;

    public OthelloPosition() {
        this("WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE");
    }

    public OthelloPosition(String s) {
        if (s.length() != 65) {
            throw new IllegalArgumentException("The input string must be 65 char long !");
        }
        switch (s.charAt(0)) {
            case 'W':
                this.playerToMove = true;
                break;
            case 'B':
                this.playerToMove = false;
                break;
            default:
                throw new IllegalArgumentException("The first character must be the current player : either 'W' or 'B'");
        }

        this.board = new char[BOARD_SIZE][BOARD_SIZE];

        for (int i = 1; i < s.length(); i++) {
            int row = (i - 1) / 8;
            int column = (i - 1) % 8;
            this.board[row][column] = s.charAt(i);
        }

    }

    public LinkedList<OthelloAction> getMoves() {
        LinkedList<OthelloAction> actions = new LinkedList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.isCandidate(i, j) && this.isMove(i, j)) {
                    actions.add(new OthelloAction(i, j));
                }
            }
        }
        if (actions.size() == 0) {
            actions.add(new OthelloAction(-1, -1, true));
        }
        return actions;
    }

    private boolean isCandidate(int i, int j) {
        return this.isFree(i, j) && this.hasOpponentNeighbor(i, j);
    }

    private boolean hasOpponentNeighbor(int i, int j) {
        for (int row = (i-1 < 0)?i:i-1; row <= i+1 && row < BOARD_SIZE; row++) {
            for (int column = (j-1 < 0)?j:j-1; column <= j+1 && column < BOARD_SIZE; column++) {
                if (!this.isFree(row, column) && this.isOpponentSquare(row, column))
                    return true;
            }
        }
        return false;
    }

    private boolean isFree(int i, int j) {
        return this.board[i][j] != 'X' && this.board[i][j] != 'O';
    }

    /**
     * Verify if the current player can play on the square (i,j).
     * This function won't call check functions if not needed => gain some time.
     * @param i row index to check
     * @param j column index to check
     * @return true if (i,j) is movement for the current player, false otherwise
     */
    private boolean isMove(int i, int j) {

        for (int row = (i-1 < 0)?i:i-1; row <= i+1 && row < BOARD_SIZE; row++) {
            for (int column = (j-1 < 0)?j:j-1; column <= j+1 && column < BOARD_SIZE; column++) {
                if (this.isOpponentSquare(row, column)) {
                    // row - i : 1 if South, 0 if Middle, -1 if North
                    // column - j : 1 if East, 0 if Middle, -1 if West
                    if (this.checkDirection(row, column, row - i, column - j))
                        return true;
                }
            }
        }

        return false;
    }

    private boolean checkDirection(int i, int j, int incRow, int incCol) {
        while ((i >= 0 && i < BOARD_SIZE) && (j >= 0 && j < BOARD_SIZE)) {
            if (this.isFree(i, j))
                return false;
            if (this.isOwnSquare(i, j))
                return true;
            i += incRow;
            j += incCol;
        }
        return false;
    }

    private boolean isOwnSquare(int i, int j) {
        return (this.playerToMove && this.board[i][j] == 'O')
                || (!this.playerToMove && this.board[i][j] == 'X');
    }

    private boolean isOpponentSquare(int i, int j) {
        return (this.playerToMove && this.board[i][j] == 'X')
                || (!this.playerToMove && this.board[i][j] == 'O');
    }


    public OthelloPosition makeMove(OthelloAction action) {
        OthelloPosition nextPosition = this.clone();

        if (action.isPassMove()) { // Pass => the board does not change
            nextPosition.playerToMove = !nextPosition.playerToMove;
            return nextPosition;
        }

        char c = (nextPosition.playerToMove)?'O':'X';

        nextPosition.board[action.row][action.column] = c;

        for (int row = (action.row-1 < 0)?action.row:action.row-1; row <= action.row+1 && row < BOARD_SIZE; row++) {
            for (int column = (action.column-1 < 0)?action.column:action.column-1; column <= action.column+1 && column < BOARD_SIZE; column++) {
                if (nextPosition.isOpponentSquare(row, column)) {
                    int relativeI = row - action.row;
                    int relativeJ = column - action.column;

                    if (this.checkDirection(row, column, relativeI, relativeJ)) {
                        int i = row, j = column;
                        while (!nextPosition.isOwnSquare(i, j)) {
                            nextPosition.board[i][j] = c;
                            i += relativeI;
                            j += relativeJ;
                        }
                    }
                }
            }
        }

        // Change user for next move
        nextPosition.playerToMove = !nextPosition.playerToMove;
        return nextPosition;
    }


    @Override
    protected OthelloPosition clone() {
        return new OthelloPosition(this.toString());
    }

    /**
     * Draws an ASCII representation of the position. White squares are marked by
     * '0' while black squares are marked by 'X'.
     */
    public void illustrate() {
        System.out.print("   ");
        for (int i = 0; i < BOARD_SIZE; i++)
            System.out.print("| " + (i + 1) + " ");
        System.out.println("|");
        printHorizontalBorder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(" " + (i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.board[i][j] == 'E') {
                    System.out.print("|   ");
                } else {
                    System.out.print("| " + this.board[i][j] + " ");
                }
            }
            System.out.println("| " + (i + 1) + " ");
            printHorizontalBorder();
        }
        System.out.print("   ");
        for (int i = 0; i < BOARD_SIZE; i++)
            System.out.print("| " + (i + 1) + " ");
        System.out.println("|\n");
    }

    private void printHorizontalBorder() {
        System.out.print("---");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("|---");
        }
        System.out.println("|---");
    }

    @Override
    public String toString() {
        String s = "";
        if (this.playerToMove) {
            s += "W";
        } else {
            s += "B";
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                s += this.board[i][j];
            }
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OthelloPosition that = (OthelloPosition) o;
        return playerToMove == that.playerToMove &&
                Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(playerToMove);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }
}
