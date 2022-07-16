import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Game {

    Board board;
    ArrayList<Integer> prevMoves;
    Player p1;
    Player p2;
    boolean log;

    /**
     * 0. Human
     * 1. Random bot
     * 2. God bot
     */
    public Game (int p1Option, int p2Option, int numberOfCups, int initStones, boolean log) {
        board = new Board(numberOfCups, initStones);
        this.log = log;
        switch (p1Option) {
            case 0: p1 = new HumanPlayer(1, numberOfCups, initStones); break;
            case 1: p1 = new RandomBot(1, numberOfCups, initStones); break;
            case 2: p1 = new MinMaxBot(1, numberOfCups, initStones); break;
            default:break;
        }
        switch (p2Option) {
            case 0: p2 = new HumanPlayer(2, numberOfCups, initStones); break;
            case 1: p2 = new RandomBot(2, numberOfCups, initStones); break;
            case 2: p2 = new MinMaxBot(2, numberOfCups, initStones); break;
            default:break;
        }

    }

    public void run() {
        board.print();
        do {
            if (board.getPlayerTurn() == 1) {
                println("Player 1 turn");
                prevMoves = p1.getMoves(prevMoves);
                System.out.println(prevMoves);
                board.moves(prevMoves);
                board.print();
            } else {
                println("Player 2 turn");
                prevMoves = p2.getMoves(prevMoves);
                System.out.println(prevMoves);
                board.moves(prevMoves);
                board.print();
            }
            System.out.println("static eval: " + board.simpleStaticEvaluation());
        } while (!board.isGameOver());
        System.out.println("Game over. Player " + board.getWinner() + " won.");
    }

    public void print(String text) {
        if (log){
            System.out.print(text);
        }
    }

    public void println(String text) {
        if (log){
            System.out.println(text);
        }
    }

    public static void main(String[] args) {
        Game game = new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]), 6, 4, true);
        game.run();
    }

}