package com.chrisdo.omg.extratime.data;

public class Result {


    private MatchResult ExtraTime;
    private MatchResult HalfTime;
    private MatchResult PenaltyShootout;

    private int GoalsHomeTeam;
    private int GoalsAwayTeam;


    public Result(){

    }

    @Override
    public String toString() {
        return "Result{" +
                "ExtraTime=" + ExtraTime +
                ", HalfTime=" + HalfTime +
                ", PenaltyShootout=" + PenaltyShootout +
                ", GoalsHomeTeam=" + GoalsHomeTeam +
                ", GoalsAwayTeam=" + GoalsAwayTeam +
                '}';
    }

    public MatchResult getExtraTime() {
        return ExtraTime;
    }

    public int getGoalsAwayTeam() {
        return GoalsAwayTeam;
    }

    public int getGoalsHomeTeam() {
        return GoalsHomeTeam;
    }

    public MatchResult getHalfTime() {
        return HalfTime;
    }

    public MatchResult getPenaltyShootout() {
        return PenaltyShootout;
    }
}
