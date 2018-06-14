package com.chrisdo.omg.extratime.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Match implements Comparable<Match>{

    public Match(){

    }

    public boolean canBet() {
        boolean can = Calendar.getInstance().before(Date);
        return can;
    }
    public boolean isToday(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) == Date.get(Calendar.YEAR)
                &&  now.get(Calendar.DAY_OF_YEAR) == Date.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public int compareTo(@NonNull Match o) {
        if(o.getDate().after(this.Date)){
            return -1;
        }if(o.getDate().before(this.Date)){
            return 1;
        }
        return 0;
    }

    public enum Status{
        TIMED,SCHEDULED,POSTPONED,IN_PLAY,FINISHED
    }

    private long id;

    private String AwayTeamName;

    private String HomeTeamName;

    private int Matchday;

    private Calendar Date;

    private Status Status;

    private Result Result;


    private String Location = "";

    private Map<String,Bet> bets = new HashMap<>();

    private DatabaseReference ref;

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final static TimeZone SOURCE_TZ = TimeZone.getTimeZone("UTC");


    private Calendar parse(String date){
        Calendar result = null;
FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Calendar c = Calendar.getInstance(SOURCE_TZ);

            c.setTime(FORMAT.parse(date));
            result = c;

        } catch (ParseException e) {
            Log.w("jsonDate", "Wrong Date Format from Database", e);
        }

        return result;
    }


   public Match(long id, String awayTeamName, String homeTeamName, int matchDay, String date,
                Result score, Map<String,Bet> bets, String location, DatabaseReference ref, String status){
        this.AwayTeamName = awayTeamName;
        this.HomeTeamName = homeTeamName;
        this.Matchday = matchDay;
        this.id = id;
    this.Result = score;
    this.bets = bets;
    this.Location = location;
        this.Date = parse(date);
        this.Status = Status.valueOf(status.toUpperCase());
        this.ref = ref;
    }

    public void placeBet(Bet bet, Context context){
        //check again
        if(canBet()) {
            DatabaseReference betRef = ref.child("bets").child(User.getUsername(context));//.push();
            betRef.setValue(bet);
        }
    }

    public void setResult(Result result){
this.Result = result;
    }

    public Bet getUserBet(Context context){
        Bet result = null;
        return bets.get(User.getUsername(context));
    }

    public String getLocation() {
        return Location;
    }

    public Map<String,Bet> getPlacedBets() {
        if(!canBet()){ //meaning its after start
            return bets;
        }
        return Collections.emptyMap(); //can only see bets after it has started
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", awayTeamName='" + AwayTeamName + '\'' +
                ", homeTeamName='" + HomeTeamName + '\'' +
                ", matchDay=" + Matchday +
                ", date=" + Date +
                ", status=" + Status +
                ", location=" + Location +
                ", result=" + Result +
                ", bets=" + bets +
                ", ref=" + ref +
                '}';
    }

    public int getMatchDay() {
        return Matchday;
    }

    public Calendar getDate(){
        return Date;
    }

    public long getId() {
        return id;
    }

    public Bet getBet(Context context){

        if(bets != null) {
            return bets.get(User.getUsername(context));
        }
        return null;
    }

    public Result getResult() {
        return Result;
    }

    public Status getStatus() {
        return Status;
    }

    public String getAwayTeamName() {
        return AwayTeamName;
    }

    public String getHomeTeamName() {
        return HomeTeamName;
    }


}
