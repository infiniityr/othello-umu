public class WashingtonEvaluator implements OthelloEvaluator {
    private static final int[][] RELATIVE_WEIGHTS = {
            {20, -3, 11, 8, 8, 11, -3, 20},
            {-3, -7, -4, 1, 1, -4, -7, -3},
            {11, -4, 2, 2, 2, 2, -4, 11},
            {8, 1, 2, -3, -3, 2, 1, 8},
            {8, 1, 2, -3, -3, 2, 1, 8},
            {11, -4, 2, 2, 2, 2, -4, 11},
            {-3, -7, -4, 1, 1, -4, -7, -3},
            {20, -3, 11, 8, 8, 11, -3, 20}
    };

    private static final int[] DISKS_X = {-1, -1, 0, 1, 1, 1, 0, -1};

    private static final int[] DISKS_Y = {0, 1, 1, 1, 0, -1, -1, -1};

    @Override
    public int evaluate(OthelloPosition position) {
        int nb_pos_white = 0, nb_pos_black = 0;
        int front_pos_white = 0, front_pos_black = 0;

        int p = 0, c = 0, l = 0, m = 0, f = 0, d = 0;

        for (int i = 0; i < OthelloPosition.BOARD_SIZE; i++) {
            for (int j = 0; j < OthelloPosition.BOARD_SIZE; j++) {
                if (position.board[i][j] == 'O') {
                    d += WashingtonEvaluator.RELATIVE_WEIGHTS[i][j];
                    nb_pos_white++;
                } else if (position.board[i][j] == 'X') {
                    d -= WashingtonEvaluator.RELATIVE_WEIGHTS[i][j];
                    nb_pos_black++;
                }

                if (position.board[i][j] != 'E') {
                    for (int k = 0; k < OthelloPosition.BOARD_SIZE; k++) {
                        int x = i + WashingtonEvaluator.DISKS_X[k];
                        int y = j + WashingtonEvaluator.DISKS_Y[k];
                        if (x >= 0 &&
                            x < OthelloPosition.BOARD_SIZE &&
                            y >= 0 &&
                            y < OthelloPosition.BOARD_SIZE &&
                            position.board[x][y] == 'E') {
                            if (position.board[i][j] == 'O') front_pos_white++;
                            else front_pos_black++;
                            break;
                        }
                    }
                }
            }
        }

        if (nb_pos_white > nb_pos_black) {
            p = (100 * nb_pos_white) / (nb_pos_white + nb_pos_black);
        } else if (nb_pos_white < nb_pos_black){
            p = -(100 * nb_pos_black) / (nb_pos_white + nb_pos_black);
        }

        if (front_pos_white > front_pos_black) {
            f = -(100 * front_pos_white) / (front_pos_white + front_pos_black);
        } else if (front_pos_white < front_pos_black) {
            f = (100 * front_pos_black) / (front_pos_white + front_pos_black);
        }

        //Corners
        nb_pos_white = nb_pos_black = 0;
        for (int i = 0; i < OthelloPosition.BOARD_SIZE; i += OthelloPosition.BOARD_SIZE - 1) {
            for (int j = 0; j < OthelloPosition.BOARD_SIZE; j += OthelloPosition.BOARD_SIZE - 1) {
                if (position.board[i][j] == 'O') {
                    nb_pos_white++;
                } else if (position.board[i][j] == 'X') {
                    nb_pos_black++;
                }
            }
        }
        c = 25 * (nb_pos_white - nb_pos_black);

        //Corners closeness
        nb_pos_white = nb_pos_black = 0;
        if (position.board[0][0] == 'E') {
            if (position.board[0][1] == 'O') nb_pos_white++;
            else if (position.board[0][1] == 'X') nb_pos_black++;
            if (position.board[1][1] == 'O') nb_pos_white++;
            else if (position.board[1][1] == 'X') nb_pos_black++;
            if (position.board[1][0] == 'O') nb_pos_white++;
            else if (position.board[1][0] == 'X') nb_pos_black++;
        }
        if (position.board[0][OthelloPosition.BOARD_SIZE - 1] == 'E') {
            if (position.board[0][OthelloPosition.BOARD_SIZE - 2] == 'O') nb_pos_white++;
            else if (position.board[0][OthelloPosition.BOARD_SIZE - 2] == 'X') nb_pos_black++;
            if (position.board[1][OthelloPosition.BOARD_SIZE - 2] == 'O') nb_pos_white++;
            else if (position.board[1][OthelloPosition.BOARD_SIZE - 2] == 'X') nb_pos_black++;
            if (position.board[1][OthelloPosition.BOARD_SIZE - 1] == 'O') nb_pos_white++;
            else if (position.board[1][OthelloPosition.BOARD_SIZE - 1] == 'X') nb_pos_black++;
        }
        if (position.board[OthelloPosition.BOARD_SIZE - 1][0] == 'E') {
            if (position.board[OthelloPosition.BOARD_SIZE - 1][1] == 'O') nb_pos_white++;
            else if (position.board[OthelloPosition.BOARD_SIZE - 1][1] == 'X') nb_pos_black++;
            if (position.board[OthelloPosition.BOARD_SIZE - 2][1] == 'O') nb_pos_white++;
            else if (position.board[OthelloPosition.BOARD_SIZE - 2][1] == 'X') nb_pos_black++;
            if (position.board[OthelloPosition.BOARD_SIZE - 2][0] == 'O') nb_pos_white++;
            else if (position.board[OthelloPosition.BOARD_SIZE - 2][0] == 'X') nb_pos_black++;
        }
        if (position.board[OthelloPosition.BOARD_SIZE - 1][OthelloPosition.BOARD_SIZE - 1] == 'E') {
            if (position.board[OthelloPosition.BOARD_SIZE - 2][OthelloPosition.BOARD_SIZE - 1] == 'O') nb_pos_white++;
            else if (position.board[OthelloPosition.BOARD_SIZE - 2][OthelloPosition.BOARD_SIZE - 1] == 'X') nb_pos_black++;
            if (position.board[OthelloPosition.BOARD_SIZE - 2][OthelloPosition.BOARD_SIZE - 2] == 'O') nb_pos_white++;
            else if (position.board[OthelloPosition.BOARD_SIZE - 2][OthelloPosition.BOARD_SIZE - 2] == 'X') nb_pos_black++;
            if (position.board[OthelloPosition.BOARD_SIZE - 1][OthelloPosition.BOARD_SIZE - 2] == 'O') nb_pos_white++;
            else if (position.board[OthelloPosition.BOARD_SIZE - 1][OthelloPosition.BOARD_SIZE - 2] == 'X') nb_pos_black++;
        }
        l = -12 * (nb_pos_white - nb_pos_black);

        //Mobility
        if (position.playerToMove) {
            nb_pos_white = position.getMoves().size();
        } else {
            nb_pos_black = position.getMoves().size();
        }
        OthelloPosition opponentPosition = position.clone();
        opponentPosition.playerToMove = !opponentPosition.playerToMove;
        if (opponentPosition.playerToMove) {
            nb_pos_white = opponentPosition.getMoves().size();
        } else {
            nb_pos_black = opponentPosition.getMoves().size();
        }
        if (nb_pos_white > nb_pos_black) {
            m = (100 * nb_pos_white) / (nb_pos_white + nb_pos_black);
        } else if (nb_pos_white < nb_pos_black) {
            m = -(100 * nb_pos_black) / (nb_pos_white + nb_pos_black);
        }

        return (10 * p) + (801 * c) + (382 * l) + (79 * m) + (74 * f) + (10 * d);
    }
}
