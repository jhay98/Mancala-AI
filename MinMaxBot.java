import java.util.ArrayList;

class MinMaxBot extends Player{

    private int depth;

    public MinMaxBot(int player1or2, int numberOfCups, int initStones) {
        super(player1or2, numberOfCups, initStones);
        depth = 8;
    }

    @Override
    public ArrayList<Integer> getMoves(ArrayList<Integer> prevMoves) {
        if (prevMoves != null) {
            mainBoardCopy.moves(prevMoves);
        }
        ArrayList<ArrayList<Integer>> moves = mainBoardCopy.getPossibleMoves();
        ArrayList<Integer> bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        if (player1or2 == Board.PLAYER1) {
            int maxEval = Integer.MIN_VALUE;
            for (ArrayList<Integer> move : moves) {
                Board copy = mainBoardCopy.getCopy();
                copy.moves(move);
                int eval = minimax(copy, depth, alpha, beta, Board.PLAYER2);
                if (eval >= maxEval) {
                    maxEval = eval;
                    bestMove = move;
                }
                alpha = Math.max(alpha, eval);
            }
        } else {
            int minEval = Integer.MAX_VALUE;
            for (ArrayList<Integer> move : moves) {
                Board copy = mainBoardCopy.getCopy();
                copy.moves(move);
                int eval = minimax(copy, depth, alpha, beta, Board.PLAYER1);
                if (eval <= minEval) {
                    minEval = eval;
                    bestMove = move;
                }
                beta = Math.min(beta, eval);
            }
        }
        mainBoardCopy.moves(bestMove);
        return bestMove;
    }

    public static int minimax(Board board, int depth, int alpha, int beta, int player) {
        if (depth == 0 || board.isGameOver()) {
            return board.simpleStaticEvaluation();
        }
        ArrayList<ArrayList<Integer>> moves = board.getPossibleMoves();
        if (player == Board.PLAYER1) {
            int maxEval = Integer.MIN_VALUE;
            for (ArrayList<Integer> move : moves) {
                Board copy = board.getCopy();
                copy.moves(move);
                int eval = minimax(copy, depth - 1, alpha, beta, Board.PLAYER2);
                maxEval = Math.max(eval, maxEval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;
            for (ArrayList<Integer> move : moves) {
                Board copy = board.getCopy();
                copy.moves(move);
                int eval = minimax(copy, depth - 1, alpha, beta, Board.PLAYER1);
                minEval = Math.min(eval, minEval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

}