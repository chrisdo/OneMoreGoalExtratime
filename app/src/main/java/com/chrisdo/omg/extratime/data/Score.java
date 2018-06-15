package com.chrisdo.omg.extratime.data;

import android.support.annotation.NonNull;

public class Score implements Comparable<Score>{

   private int Points;

    private int CorrectBets;
    private int CorrectTendencies;
    private int CorrectGoalRelation;

    public Score(){

    }

    Score(int points, int correctBets, int correctTendencies, int correctGoalRelation){
        this.Points = points;
        this.CorrectBets = correctBets;
        this.CorrectTendencies = correctTendencies;
        this.CorrectGoalRelation = correctGoalRelation;
    }

    public int getCorrectBets() {
        return CorrectBets;
    }

    public int getCorrectGoalRelation() {
        return CorrectGoalRelation;
    }

    public int getCorrectTendencies() {
        return CorrectTendencies;
    }

    public int getPoints() {
        return Points;
    }

    @Override
    public int compareTo(@NonNull Score o) {
        if (o.getPoints() < Points ) {
            return -1;
        } else if (o.getPoints() == Points){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Score{" +
                "points=" + Points +
                ", correctBets=" + CorrectBets +
                ", correctTendencies=" + CorrectTendencies +
                ", correctGoalRelation=" + CorrectGoalRelation +
                '}';
    }

    public void setPoints(int points){
        this.Points = points;
    }

    public void setCorrectBets(int correctBets) {
        CorrectBets = correctBets;
    }

    public void setCorrectGoalRelation(int correctGoalRelations) {
        CorrectGoalRelation = correctGoalRelations;
    }

    public void setCorrectTendencies(int correctTendencies) {
        CorrectTendencies = correctTendencies;
    }
}
