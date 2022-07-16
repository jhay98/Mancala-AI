import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class RandomBot extends Player{

    public RandomBot(int player1or2, int numberOfCups, int initStones) {
        super(player1or2, numberOfCups, initStones);
    }
    @Override
    public ArrayList<Integer> getMoves(ArrayList<Integer> prevMoves) {
        if (prevMoves != null) {
            mainBoardCopy.moves(prevMoves);
        }
        ArrayList<ArrayList<Integer>> allPossibleMoves = mainBoardCopy.getPossibleMoves();
        ArrayList<Integer> moves = allPossibleMoves.get(ThreadLocalRandom.current().nextInt(allPossibleMoves.size()));
        for (int i = 0; i < moves.size() - 1; i++) {
            mainBoardCopy.move(moves.get(i), false);
        }
        mainBoardCopy.move(moves.get(moves.size() - 1), false);
        return moves;
    }

}