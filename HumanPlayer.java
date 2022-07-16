import java.util.ArrayList;
import java.util.Scanner;

class HumanPlayer extends Player{

    public HumanPlayer(int player1or2, int numberOfCups, int initStones) {
        super(player1or2, numberOfCups, initStones);
    }

    @Override
    public ArrayList<Integer> getMoves(ArrayList<Integer> prevMoves) {
        if (prevMoves != null) {
            mainBoardCopy.moves(prevMoves);
        }
        ArrayList<Integer> moves = new ArrayList<Integer>(1);
        boolean firstMove = true;
        do {
            if (firstMove) {
                firstMove = false;
            } else {
                mainBoardCopy.print();
                System.out.println("Go again");
            }
            boolean validMove = true;
            int move = -1;
            do {
                if (!validMove) {
                    System.out.println("Invalid move, dumbass");
                }
                Scanner sc = new Scanner(System.in);
                try {
                    move = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    validMove = false;
                    continue;
                }
                validMove = mainBoardCopy.move(move, true);
            } while(!validMove);
            moves.add(move);
        } while (mainBoardCopy.getPlayerTurn() == player1or2 && !mainBoardCopy.isGameOver());
        return moves;
    }
}