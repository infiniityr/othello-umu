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

    public void setStopAt (long stopAt) {
        this.stopAt = stopAt;
    }

    @Override
    public OthelloAction evaluate(OthelloPosition position) throws TimeoutException {
        int A = Integer.MIN_VALUE, B = Integer.MAX_VALUE;
        OthelloAction bestAction =  position.playerToMove ? this.evaluateMax(position, this.depth, A, B) : this.evaluateMin(position, this.depth, A, B);
        return bestAction;
    }

    public OthelloAction evaluateMin(OthelloPosition position, int depth, int alpha, int beta) throws TimeoutException {
        this.checkTime();
        if (depth == 0) {
            OthelloAction a = new OthelloAction(0, 0);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }

        LinkedList<OthelloAction> actions = position.getMoves();

        if (actions.size() == 0) {
            OthelloAction a = new OthelloAction(0, 0, true);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }
        OthelloAction bestAction = new OthelloAction(0, 0, true);
        bestAction.setValue(Integer.MAX_VALUE);

        for (OthelloAction a : actions) {
            OthelloPosition nextPos = position.makeMove(a);

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

    public OthelloAction evaluateMax(OthelloPosition position, int depth, int alpha, int beta) throws TimeoutException {
        this.checkTime();
        if (depth == 0) {
            OthelloAction a = new OthelloAction(0, 0);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }

        LinkedList<OthelloAction> actions = position.getMoves();

        if (actions.size() == 0) {
            OthelloAction a = new OthelloAction(0, 0, true);
            a.setValue(this.evaluator.evaluate(position));
            return a;
        }

        OthelloAction bestAction = new OthelloAction(0, 0, true);
        bestAction.setValue(Integer.MIN_VALUE);

        for (OthelloAction a : actions) {
            OthelloPosition nextPos = position.makeMove(a);

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

    public void checkTime() throws TimeoutException {
        if (System.currentTimeMillis() >= this.stopAt) {
            throw new TimeoutException();
        }
    }
}
