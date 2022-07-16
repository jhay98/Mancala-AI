import java.util.ArrayList;

abstract class Player {
    
    int player1or2; // Am I player 1 or 2?
    Board mainBoardCopy;

    public Player(int player1or2, int numberOfCups, int initStones) {
        this.player1or2 = player1or2;
        this.mainBoardCopy = new Board(numberOfCups, initStones);
    }
    
    public abstract ArrayList<Integer> getMoves(ArrayList<Integer> prevMoves);
}