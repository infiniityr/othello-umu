import java.util.concurrent.TimeoutException;

public class Othello {

    public static void main(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException("There must be 2 arguments. The position and the time limit");
        }

        OthelloPosition position = new OthelloPosition(args[0]);
        int time_limit = Integer.parseInt(args[1]);

        AlphaBetaAlgorithm alphaBeta = new AlphaBetaAlgorithm();

        // Calculate the timestamp to stop the alpha-beta algorithm.
        // The -20 correspond (mostly) of the other computations.
        alphaBeta.setStopAt(System.currentTimeMillis() + time_limit * 1000 - 20);
        OthelloAction bestAction = new OthelloAction(0, 0, true);

        for (int i = AlphaBetaAlgorithm.DEFAULT_DEPTH; i < 20; i++) {
            alphaBeta.setSearchDepth(i);
            try {
                bestAction = alphaBeta.evaluate(position);
            } catch (TimeoutException e) {
                break;
            }
        }

        bestAction.print();
    }

}
