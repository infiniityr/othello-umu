public class MiniMaxEvaluator implements OthelloEvaluator {

    private static final int VALUE_CORNER = 5;

    private static final int VALUE_EDGE = 2;

    private static final int VALUE = 1;

    @Override
    public int evaluate(OthelloPosition position) {
        int evaluationW = 0;
        int evaluationB = 0;

        for (int i = 0; i < position.board.length; i++) {
            for (int j = 0; j < position.board.length; j++) {
                if (this.isCorner(position, i, j)) {
                    if (position.board[i][j] == 'O') {
                        evaluationW += VALUE_CORNER;
                    } else if (position.board[i][j] == 'X') {
                        evaluationB += VALUE_CORNER;
                    }
                } else if (this.isEdge(position, i, j)) {
                    if (position.board[i][j] == 'O') {
                        evaluationW += VALUE_EDGE;
                    } else if (position.board[i][j] == 'X') {
                        evaluationB += VALUE_EDGE;
                    }
                } else {
                    if (position.board[i][j] == 'O') {
                        evaluationW += VALUE;
                    } else if (position.board[i][j] == 'X') {
                        evaluationB += VALUE;
                    }
                }
            }
        }

        return evaluationW - evaluationB;
    }

    private boolean isCorner(OthelloPosition position, int i, int j) {
        return  (i == 0 && j == 0) ||
                (i == 0 && j == position.board.length - 1) ||
                (i == position.board.length - 1 && j == 0) ||
                (i == position.board.length - 1 && j == position.board.length - 1);
    }

    private boolean isEdge(OthelloPosition position, int i, int j) {
        return  i == 0 ||
                i == position.board.length - 1 ||
                j == 0 ||
                j == position.board.length - 1;
    }
}
