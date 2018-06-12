package com.chrisdo.omg.extratime.data;

public class Bet {

   private int scoreAway;
   private int scoreHome;


    public Bet(){

    }

    public Bet( int scoreHome, int scoreAway){
        this.scoreAway = scoreAway;
        this.scoreHome = scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    public int getScoreHome() {

        return this.scoreHome;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "scoreAway=" + scoreAway +
                ", scoreHome=" + scoreHome +
                '}';
    }
}
