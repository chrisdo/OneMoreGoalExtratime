package com.chrisdo.omg.extratime.data;

import android.support.annotation.NonNull;

public class Score implements Comparable<Score>{

   private int points;

    private int correctBets;
    private int correctTendencies;
    private int correctGoalRelations;

    public Score(){

    }

    Score(int points, int correctBets, int correctTendencies, int correctGoalRelations){
        this.points = points;
        this.correctBets = correctBets;
        this.correctTendencies = correctTendencies;
        this.correctGoalRelations = correctGoalRelations;
    }

    public int getCorrectBets() {
        return correctBets;
    }

    public int getCorrectGoalRelations() {
        return correctGoalRelations;
    }

    public int getCorrectTendencies() {
        return correctTendencies;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(@NonNull Score o) {
        if (o.getPoints() < points ) {
            return -1;
        } else if (o.getPoints() == points){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Score{" +
                "points=" + points +
                ", correctBets=" + correctBets +
                ", correctTendencies=" + correctTendencies +
                ", correctGoalRelations=" + correctGoalRelations +
                '}';
    }
}
