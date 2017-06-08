package com.adaptionsoft.games.trivia.runner;

/**
 * Created by tmac on 2017-06-07.
 */
public class Player {

    public String Name;
    public int purseValue = 0;
    public boolean isInPenaltyBox = false;
    public int currentPosition = 0;

    public Player(String playerName){
        this.Name = playerName;
    }

    public void move(int rollValue){
        this.currentPosition = (this.currentPosition+rollValue) % 12;
    }

    public boolean isAWinner(){
        return this.purseValue == 6;
    }
}
