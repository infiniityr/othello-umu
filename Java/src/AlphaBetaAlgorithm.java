import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

public class AlphaBetaAlgorithm implements OthelloAlgorithm {

    protected static final int DEFAULT_DEPTH = 7;

    public OthelloEvaluator evaluator;

    protected int depth;

    public long stopAt;

    public AlphaBetaAlgorithm() {
        this(DEFAULT_DEPTH);
    }

    public AlphaBetaAlgorithm(int depth) {
        this(new MiniMaxEvaluator(), depth);
    }

    public AlphaBetaAlgorithm(OthelloEvaluator evaluator, int depth) {
        this.evaluator = evaluator;
        this.depth = depth;
        this.stopAt = Long.MAX_VALUE;
    }

    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void setSearchDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Set the stop timestamp
     * @param stopAt
     */
    public void setStopAt (long stopAt) {
        this.stopAt = stopAt;
    }

    /**
     * Return the best action to perform for the input position
     * @param position
     * @return
     * @throws TimeoutException
     */
    @Override
    public OthelloAction evaluate(OthelloPosition position) throws TimeoutException {
        int A = Integer.MIN_VALUE, B = Integer.MAX_VALUE;
        OthelloAction bestAction =  position.playerToMove ? this.evaluateMax(position, this.depth, A, B) : this.evaluateMin(position, this.depth, A, B);
        return bestAction;
    }

    /**
     * Get the best action to perform in the input position for the black player
     * @param position
     * @param depth
     * @param alpha
     * @param beta
     * @return
     * @throws TimeoutException
     */
    public OthelloAction evaluateMin(OthelloPosition position, int depth, int alpha, int beta) throws TimeoutException {
        this.checkTime();
        if (depth == 0) { // If we are at the root, return an empty action with the heuristic value
            OthelloAction a = new OthelloAction(0, 0);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }

        // Get all possible moves for the black player in the input position
        LinkedList<OthelloAction> actions = position.getMoves();

        if (actions.size() == 0) { // If there is any moves, return an "pass" action
            OthelloAction a = new OthelloAction(0, 0, true);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }

        OthelloAction bestAction = new OthelloAction(0, 0, true);
        bestAction.setValue(Integer.MAX_VALUE);

        for (OthelloAction a : actions) {
            // Simulate the action in the current position to get the nextPosition
            OthelloPosition nextPos = position.makeMove(a);

            // Get the best action of the white player for the next position
            OthelloAction bestNextAction = this.evaluateMax(nextPos, depth - 1, alpha, beta);
            if (bestAction.getValue() > bestNextAction.getValue()) {
                bestAction = a;
                bestAction.setValue(bestNextAction.value);

                if (alpha >= bestAction.getValue()) {
                    return bestAction;
                }
                beta = Integer.min(beta, bestAction.getValue());
            }
        }
        return bestAction;
    }

    /**
     * Get the best action to perform in the input position for the white player
     * @param position
     * @param depth
     * @param alpha
     * @param beta
     * @return
     * @throws TimeoutException
     */
    public OthelloAction evaluateMax(OthelloPosition position, int depth, int alpha, int beta) throws TimeoutException {
        this.checkTime();
        if (depth == 0) { // If we are at the root, return an empty action with the heuristic value
            OthelloAction a = new OthelloAction(0, 0);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }

        // Get all possible moves for the white player in the input position
        LinkedList<OthelloAction> actions = position.getMoves();

        if (actions.size() == 0) { // If there is any moves, return an "pass" action
            OthelloAction a = new OthelloAction(0, 0, true);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }

        OthelloAction bestAction = new OthelloAction(0, 0, true);
        bestAction.setValue(Integer.MIN_VALUE);

        for (OthelloAction a : actions) {
            // Simulate the action in the current position to get the nextPosition
            OthelloPosition nextPos = position.makeMove(a);

            // Get the best action of the black player for the next position
            OthelloAction bestNextAction = this.evaluateMin(nextPos, depth - 1, alpha, beta);
            if (bestAction.getValue() < bestNextAction.getValue()) {
                bestAction = a;
                bestAction.setValue(bestNextAction.value);

                if (bestAction.getValue() >= beta) {
                    return bestAction;
                }
                alpha = Integer.max(alpha, bestAction.getValue());

            }
        }

        return bestAction;
    }

    /**
     * Throw an exception if the currentTime is greater than the stop timestamp.
     * @throws TimeoutException
     */
    public void checkTime() throws TimeoutException {
        if (System.currentTimeMillis() >= this.stopAt) {
            throw new TimeoutException();
        }
    }
}
