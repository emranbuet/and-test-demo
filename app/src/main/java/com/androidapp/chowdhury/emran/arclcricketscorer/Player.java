package com.androidapp.chowdhury.emran.arclcricketscorer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable{
    private String playerName;
    private int playerId;

    public Player(String playerName, int playerId) {
        this.playerName = playerName;
        this.playerId = playerId;
    }
    public String getPlayerName() {

        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

}
