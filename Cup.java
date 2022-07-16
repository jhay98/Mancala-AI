class Cup {

    private int stones;
    private Cup nextCup;
    private int playerSide;
    private int index;

    public Cup(int stones, int playerSide, int index) {
        this.stones = stones;
        this.playerSide = playerSide;
        this.index = index;
    }
    
    public int getPlayerSide() {
        return playerSide;
    }

    public int getIndex() {
        return index;
    }

    public void addStone() {
        this.stones++;
    }

    public void addStones(int stones) {
        this.stones += stones;
    }

    public Cup getNextCup() {
        return nextCup;
    }

    public int getStones() {
        return stones;
    }

    public void setNextCup(Cup nextCup) {
        this.nextCup = nextCup;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public String toString() {
        if (stones < 10) {
            return " " + stones;
        }
        return ""  + stones;
    }

    public Cup getCopy() {
        return new Cup(stones, playerSide, index);
    }

}