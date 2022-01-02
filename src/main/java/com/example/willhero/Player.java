package com.example.willhero;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Serializable {
    private int currentscore;
    private int currentcoins;
    private int games_played;
    private ArrayList<Game_Objects>gameObjects;
    private Hero hero;
    private Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }

    private ArrayList<Chests>chests;
    private static Random random=new Random();

    public ArrayList<Game_Objects> getGameObjects() {
        return gameObjects;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Chests> getChests() {
        return chests;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public Player() {
        this.currentscore = 0;
        this.currentcoins =0;
        this.games_played = 0;
        gameObjects=new ArrayList<>();
        chests=new ArrayList<>();
        islands=new ArrayList<>();
    }

    private ArrayList<Island>islands;

    public int getGames_played() {
        return games_played;
    }

    public void setGames_played(int games_played) {
        this.games_played = games_played;
    }

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

    private static int name=random.nextInt(10);


    public static int getName() {
        return name;
    }

    public void name_increase()
    {
        name++;
    }

    public void add_objects_to_player(ArrayList<Game_Objects>gameObjects,Hero hero,ArrayList<Island>islands,ArrayList<Chests>chests,Weapon weapon)
    {
        for(int i =0;i<gameObjects.size();i++)
        {
            this.gameObjects.add(gameObjects.get(i));
        }

        this.hero=hero;

        for(int j=0;j<islands.size();j++)
        {
            this.islands.add(islands.get(j));
        }
        for(int  k=0;k<chests.size();k++)
        {
            this.chests.add(chests.get(k));
        }
        this.weapon=weapon;
    }
    public void setcoin(int a)
    {
        this.currentcoins=a;
    }
}
