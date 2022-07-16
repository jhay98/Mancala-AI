import java.util.ArrayList;

class Board {

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    private int numberOfCups;
    private int initStones;
    private int playerTurn;
    private boolean gameOver;

    private final Cup player1FirstCup;
    private final Cup player2FirstCup;
    private final Cup player1Mancala;
    private final Cup player2Mancala;

    public Board(int numberOfCups, int initStones) {
        // Init vars
        this.numberOfCups = numberOfCups;
        this.initStones = initStones;
        this.playerTurn = PLAYER1;
        this.gameOver = false;

        // Init cups
        player1FirstCup = new Cup(initStones, PLAYER1, 0);
        player2FirstCup = new Cup(initStones, PLAYER2, 0);
        player1Mancala = new Cup(0, PLAYER1, numberOfCups);
        player2Mancala = new Cup(0, PLAYER2, numberOfCups);
        initCups();
    }

    /*
     * Create a copy
     */
    public Board(int numberOfCups, int initStones, int playerTurn, Cup player1FirstCup) {
        // Init vars
        // numberOfCups = 6;
        // initStones = 4;
        this.numberOfCups = numberOfCups;
        this.initStones = initStones;
        this.playerTurn = playerTurn;

        // Init cups
        this.player1FirstCup = player1FirstCup.getCopy();
        Cup currentCup = player1FirstCup;
        Cup currentCupNew = this.player1FirstCup;
        for (int i = 0; i < numberOfCups - 1; i++) {
            currentCup = currentCup.getNextCup();
            currentCupNew.setNextCup(currentCup.getCopy());
            currentCupNew = currentCupNew.getNextCup();
        }
        currentCup = currentCup.getNextCup();
        this.player1Mancala = currentCup.getCopy();
        currentCupNew.setNextCup(this.player1Mancala);        
        currentCupNew = currentCupNew.getNextCup();

        currentCup = currentCup.getNextCup();
        this.player2FirstCup = currentCup.getCopy();
        currentCupNew.setNextCup(this.player2FirstCup);        
        currentCupNew = currentCupNew.getNextCup();
        
        for (int i = 0; i < numberOfCups - 1; i++) {
            currentCup = currentCup.getNextCup();
            currentCupNew.setNextCup(currentCup.getCopy());
            currentCupNew = currentCupNew.getNextCup();
        }
        currentCup = currentCup.getNextCup();
        this.player2Mancala = currentCup.getCopy();
        currentCupNew.setNextCup(this.player2Mancala);        
        currentCupNew = currentCupNew.getNextCup();
        currentCupNew.setNextCup(this.player1FirstCup);
    }

    public void initCups() {
        Cup currentCup = player1FirstCup;
        for (int i = 0; i < numberOfCups - 1; i++) {
            currentCup.setNextCup(new Cup(initStones, PLAYER1, i + 1));
            currentCup = currentCup.getNextCup();
        }
        currentCup.setNextCup(player1Mancala);
        currentCup = player1Mancala;
        currentCup.setNextCup(player2FirstCup);
        currentCup = player2FirstCup;
        
        for (int i = 0; i < numberOfCups - 1; i++) {
            currentCup.setNextCup(new Cup(initStones, PLAYER2, i + 1));
            currentCup = currentCup.getNextCup();
        }
        currentCup.setNextCup(player2Mancala);
        currentCup = player2Mancala;
        currentCup.setNextCup(player1FirstCup);
    }

    public boolean move(int position, boolean checkIfValid) {
        if (!validMove(position)) {//ADD && checkIfValid
            System.out.println("INVALID MOVE: " + position);
            System.exit(0);
            return false;
        }
        Cup currentCup = getCup(playerTurn, position);
        int remainingStones = currentCup.getStones();
        currentCup.setStones(0);
        currentCup = currentCup.getNextCup();
        while (remainingStones > 0) {
            if (playerTurn == PLAYER1 && currentCup == player2Mancala || playerTurn == PLAYER2 && currentCup == player1Mancala) {
                currentCup = currentCup.getNextCup();
                continue;
            }
            currentCup.addStone();
            remainingStones--;
            if (remainingStones == 0 &&
                currentCup != player1Mancala &&
                currentCup != player2Mancala &&
                currentCup.getStones() == 1 &&
                playerTurn == currentCup.getPlayerSide()) {
                Cup oppositeCup = getCup(getOpponent(playerTurn), (numberOfCups - 1) - currentCup.getIndex());
                getCup(playerTurn, numberOfCups).addStones(currentCup.getStones() + oppositeCup.getStones());
                currentCup.setStones(0);
                oppositeCup.setStones(0);
            }
            currentCup = currentCup.getNextCup();
        }
        if (currentCup != player1FirstCup && currentCup != player2FirstCup) {
            playerTurn = getOpponent(playerTurn);
        }
        gameOverCheck();
        if (gameOver) {
            endGame();
        }
        return true;
    }

    // Assumes moves are valid
    public void moves(ArrayList<Integer> moves) {
        for (Integer i : moves) {
            move(i, false);
        }
    }

    public boolean validMove(int position) {
        if (position < 0 || position >= numberOfCups || getCup(playerTurn, position).getStones() == 0 || gameOver) {
            return false;
        }
        return true;
    }

    public Board getCopy() {
        return new Board(numberOfCups, initStones, playerTurn, player1FirstCup);  
    }

    public ArrayList<ArrayList <Integer>> getPossibleMoves() {
        ArrayList<ArrayList <Integer>> allMoves = new ArrayList<ArrayList <Integer>>(numberOfCups);
        if (gameOver) {
            return allMoves;
        }
        Cup currentCup;
        if (playerTurn == PLAYER1) {
            currentCup = player1FirstCup;
        } else {
            currentCup = player2FirstCup;
        }
        for (int i = 0; i < numberOfCups; i++) {
            if (currentCup.getStones() != 0) {
                if (ripples(currentCup)) {
                    Board sim = getCopy();
                    sim.move(currentCup.getIndex(), false);
                    ArrayList<ArrayList <Integer>> submoves = sim.getPossibleMoves();
                    for (ArrayList<Integer> submove : submoves) {
                        ArrayList<Integer> move = new ArrayList<Integer>(2);
                        move.add(currentCup.getIndex());
                        move.addAll(submove);
                        allMoves.add(move);
                    }
                } else {
                    ArrayList<Integer> move = new ArrayList<Integer>(2);
                    move.add(currentCup.getIndex());
                    allMoves.add(move);
                }
            }
            currentCup = currentCup.getNextCup();
        }
        return allMoves;
    }

    public boolean ripples(Cup cup) {
        if ((cup.getIndex() + cup.getStones()) % (2 * numberOfCups + 1) != numberOfCups) {
            return false;
        }
        if (cup.getStones() != 1) {
            return true;
        }
        Cup currentCup;
        if (cup.getPlayerSide() == PLAYER1) {
            currentCup = player1FirstCup;
        } else {
            currentCup = player2FirstCup;
        }
        for (int i = 0; i < numberOfCups - 1; i++) {
            if (currentCup.getStones() != 0) {
                return true;
            }
            currentCup = currentCup.getNextCup();
        }
        return false;
    }

    public int simpleStaticEvaluation() {
        // return player1Mancala.getStones() - player2Mancala.getStones();
        Cup currentCup = player1FirstCup;
        int eval = 0;
        for (int i = 0; i < numberOfCups; i++) {
            eval += currentCup.getStones();
            currentCup = currentCup.getNextCup();
        }
        currentCup = player2FirstCup;
        for (int i = 0; i < numberOfCups; i++) {
            eval -= currentCup.getStones();
            currentCup = currentCup.getNextCup();
        }
        eval += player1Mancala.getStones() * 10;
        eval -= player2Mancala.getStones() * 10;
        return eval;
    }

    public void gameOverCheck() {
        Cup currentCup;
        currentCup = player1FirstCup;
        for (int i = 0; i < numberOfCups; i++) {
            if (currentCup.getStones() != 0) {
                break;
            }
            currentCup = currentCup.getNextCup();
        }
        if (currentCup == player1Mancala) {
            gameOver = true;
            return;
        }
        currentCup = player2FirstCup;
        for (int i = 0; i < numberOfCups; i++) {
            if (currentCup.getStones() != 0) {
                break;
            }
            currentCup = currentCup.getNextCup();
        }
        if (currentCup == player2Mancala) {
            gameOver = true;
        } else {
            gameOver = false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame() {
        Cup currentCup = player1FirstCup;
        int stoneCount = 0;
        for (int i = 0; i < numberOfCups; i++) {
            stoneCount += currentCup.getStones();
            currentCup.setStones(0);
            currentCup = currentCup.getNextCup();
        }
        currentCup.addStones(stoneCount);
        currentCup = currentCup.getNextCup();
        stoneCount = 0;
        for (int i = 0; i < numberOfCups; i++) {
            stoneCount += currentCup.getStones();
            currentCup.setStones(0);
            currentCup = currentCup.getNextCup();
        }
        currentCup.addStones(stoneCount);
        currentCup = currentCup.getNextCup();
    }

    public int getWinner() {
        if (player1Mancala.getStones() > player2Mancala.getStones()) {
            return PLAYER1;
        } else {
            return PLAYER2;
        }
    }


    public Cup getCup(int player, int position) {
        if (position < 0 || position > numberOfCups) {
            return null;
        }
        Cup currentCup = null;
        if (player == PLAYER1) {
            currentCup = player1FirstCup;
            for (int i = 0; i < position; i++) {
                currentCup = currentCup.getNextCup();
            }

        } else if (player == PLAYER2) {
            currentCup = player2FirstCup;
            for (int i = 0; i < position; i++) {
                currentCup = currentCup.getNextCup();
            }
        }        
        return currentCup;
    }
    
    public static int getOpponent(int player) {
        if (player == 1) {
            return 2;
        } else if (player == 2) {
            return 1;
        }
        return 0;
    }

    public void print() {
        Cup currentCup = player1FirstCup;
        System.out.print("------");
        for (int i = 0; i < numberOfCups; i++) {
            System.out.print("-----");
        }
        System.out.println("-----");
        System.out.print("|    | ");
        for (int i = numberOfCups - 1; i >= 0; i--) {
            System.out.print(getCup(2, i) + " | ");
        }
        System.out.println("   |");
        System.out.print("| " + player2Mancala + " |");
        for (int i = 0; i < numberOfCups - 1; i++) {
            System.out.print("-----");
        }
        System.out.println("----| " + player1Mancala + " |");
        System.out.print("|    | ");
        for (int i = 0; i < numberOfCups; i++) {
            System.out.print(getCup(1, i) + " | ");
        }
        System.out.println("   |");
        System.out.print("------");
        for (int i = 0; i < numberOfCups; i++) {
            System.out.print("-----");
        }
        System.out.println("-----");
    }

    public int getPlayerTurn() {
        return playerTurn;
    }
    

}
