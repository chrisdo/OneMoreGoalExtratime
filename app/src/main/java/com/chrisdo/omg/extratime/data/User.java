package com.chrisdo.omg.extratime.data;

import android.content.Context;

public class User {

    private static final String PREFERENCES_FILE = "extratime_user_pref";
    private static final String PREF_KEY_USERNAME = "name";

    private String name;


    private Score score;

    private String topScorer;

    private String worldChampion;



    public User(){

    }

    public User(String name, Score score, String topScorer, String worldChampion){
        this.name = name;
        this.score = score;
        this.topScorer = topScorer;
        this.worldChampion = worldChampion;

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
        return this.name;
    }

    public Score getScore() {
        return this.score;
    }

    public String getTopScorer() {
        return this.topScorer;
    }

    public String getWorldChampion(){
        return this.worldChampion;
    }


}
