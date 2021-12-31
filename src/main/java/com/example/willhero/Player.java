package com.example.willhero;

public class Player {
    private String name;
    private int currentscore;
    private int currentcoins;

    public int getCurrentscore() {
        return currentscore;
    }

    public void setCurrentscore() {
        this.currentscore = this.currentscore+1;
    }
    public void setCurrentcoins() {
        this.currentcoins = this.currentcoins+1;
    }

    public void setCurrentcoins(int a) {
        this.currentcoins = this.currentcoins+a;
    }

    public int getCurrentcoins() {
        return currentcoins;
    }

    public Player()
    {
        currentcoins=0;
        currentscore=0;
    }
}
