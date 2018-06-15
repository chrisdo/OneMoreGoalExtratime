package com.chrisdo.omg.extratime.data;

import android.content.Context;

public class User {

    private static final String PREFERENCES_FILE = "extratime_user_pref";
    private static final String PREF_KEY_USERNAME = "name";

    private String Name;


    private Score Score;

    private String TopScorer;

    private String WorldChampion;



    public User(){

    }

    public User(String name, Score score, String topScorer, String worldChampion){
        this.Name = name;
        this.Score = score;
        this.TopScorer = topScorer;
        this.WorldChampion = worldChampion;

    }


    public static String getUsername(Context context){

        String name = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE).getString(PREF_KEY_USERNAME, null);
        return name;
    }

    public static void setUsername(String username, Context context){
        context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE).edit().putString(PREF_KEY_USERNAME, username).commit();

    }


//    public static void setTopScorer(String scorer) {
////
////        User.topScorer = topScorer;
////
////    }
////
////    public static void setWorldChampion(String champ){
////        User.worldChampion = champ;
////    }
//    }

    public String getName(){
        return this.Name;
    }

    public Score getScore() {
        return this.Score;
    }

    public String getTopScorer() {
        return this.TopScorer;
    }

    public String getWorldChampion(){
        return this.WorldChampion;
    }

    public void setScore(Score score){
        this.Score = score;
    }

}
