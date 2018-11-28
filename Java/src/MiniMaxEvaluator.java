public class MiniMaxEvaluator implements OthelloEvaluator {
    // Weight of the corners
    private static final int VALUE_CORNER = 5;

    // Weight of the cells in the edges
    private static final int VALUE_EDGE = 2;

    // Default weight of the cells
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

    /**
     * Return true if the coordinate (i, j) is in a corner
     * @param position
     * @param i
     * @param j
     * @return
     */
    private boolean isCorner(OthelloPosition position, int i, int j) {
        return  (i == 0 && j == 0) ||
                (i == 0 && j == position.board.length - 1) ||
                (i == position.board.length - 1 && j == 0) ||
                (i == position.board.length - 1 && j == position.board.length - 1);
    }

    /**
     * Return true if the coordinate (i, j) is in an edge
     * @param position
     * @param i
     * @param j
     * @return
     */
    private boolean isEdge(OthelloPosition position, int i, int j) {
        return  i == 0 ||
                i == position.board.length - 1 ||
                j == 0 ||
                j == position.board.length - 1;
    }
}
