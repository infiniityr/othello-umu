import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MiniMaxAlgorithm implements OthelloAlgorithm {
    private static final int DEFAULT_DEPTH = 3;

    protected OthelloEvaluator evaluator;

    protected int depth;

    public MiniMaxAlgorithm() {
        this(DEFAULT_DEPTH);
    }

    public MiniMaxAlgorithm(int depth) {
        this(new MiniMaxEvaluator(), depth);
    }

    public MiniMaxAlgorithm(OthelloEvaluator evaluator, int depth) {
        this.evaluator = evaluator;
        this.depth = depth;
    }

    @Override
    public void setEvaluator(OthelloEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void setSearchDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public OthelloAction evaluate(OthelloPosition position) {
        return position.playerToMove ? this.evaluateMax(position, this.depth) : this.evaluateMin(position, this.depth);
    }

    private OthelloAction evaluateMin(OthelloPosition position, int depth) {

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
            OthelloAction bestNextAction = this.evaluateMax(nextPos, depth - 1);
            if (bestAction.getValue() > bestNextAction.getValue()) {
                bestAction = a;
                bestAction.setValue(bestNextAction.value);
            }
        }

        return bestAction;
    }

    private OthelloAction evaluateMax(OthelloPosition position, int depth) {

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
            OthelloAction bestNextAction = this.evaluateMin(nextPos, depth - 1);
            if (bestAction.getValue() < bestNextAction.getValue()) {
                bestAction = a;
                bestAction.setValue(bestNextAction.value);
            }
        }

        return bestAction;
    }
}
