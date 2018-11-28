public class NaiveWashingtonEvaluator implements OthelloEvaluator {
    /**
     * Array containing the weight of each cell of the board.
     */
    private static final int[][] WEIGHTS = {
            {10, -3, 6, 3, 3, 6, -3, 10},
            {-3, -5, -1, -1, -1, -1, -5, -3},
            {6, -1, 1, 0, 0, 1, -1, 6},
            {3, -1, 0, 1, 1, 0, -1, 3},
            {3, -1, 0, 1, 1, 0, -1, 3},
            {6, -1, 1, 0, 0, 1, -1, 6},
            {-3, -5, -1, -1, -1, -1, -5, -3},
            {10, -3, 6, 3, 3, 6, -3, 10}
    };

    @Override
    public int evaluate(OthelloPosition position) {
        int evaluationW = 0;
        int evaluationB = 0;

        for (int i = 0; i < position.board.length; i++) {
            for (int j = 0; j < position.board.length; j++) {
                if (position.board[i][j] == 'O') {
                    evaluationW += NaiveWashingtonEvaluator.WEIGHTS[i][j];
                } else if (position.board[i][j] == 'X') {
                    evaluationB += NaiveWashingtonEvaluator.WEIGHTS[i][j];
                }
            }
        }

        return evaluationW - evaluationB;
    }
}
