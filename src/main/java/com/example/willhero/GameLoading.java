package com.example.willhero;

import java.io.File;
import java.util.ArrayList;

public class GameLoading {
    private ArrayList<String> gameload;
    public GameLoading()
    {
        gameload=new ArrayList<>();
    }

    public ArrayList<String> getGameload() {
        File []folder=new File("src\\main\\GamesSaved").listFiles();
        for(File a:folder)
        {
            gameload.add(a.getName());
        }
        return gameload;
    }
}
