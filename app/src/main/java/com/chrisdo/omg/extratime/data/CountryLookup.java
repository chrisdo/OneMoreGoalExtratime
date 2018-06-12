package com.chrisdo.omg.extratime.data;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public final class CountryLookup {

    public static Map<String,String> COUNTRY_TABLE = new HashMap<>();

    static{
        COUNTRY_TABLE.put("Egypt","Aegypten");
                COUNTRY_TABLE.put("Nigeria","Nigeria");
                COUNTRY_TABLE.put("Senegal","Senegal");
                COUNTRY_TABLE.put("Morocco","Marokko");
                COUNTRY_TABLE.put("Tunisia","Tunesien");
                COUNTRY_TABLE.put("Australia","Australien");
                COUNTRY_TABLE.put("Iran","Iran");
                COUNTRY_TABLE.put("Japan","Japan");
                COUNTRY_TABLE.put("Korea Republic","Suedkorea");
                COUNTRY_TABLE.put("Saudi Arabia","Saudi Arabien");
                COUNTRY_TABLE.put("Belgium","Belgien");
                COUNTRY_TABLE.put("Croatia","Kroatien");
                COUNTRY_TABLE.put("Denmark","Daenemark");
                COUNTRY_TABLE.put("England","England");
                COUNTRY_TABLE.put("France","Frankreich");
                COUNTRY_TABLE.put("Germany","Deutschland");
        COUNTRY_TABLE.put("Iceland","Island");
        COUNTRY_TABLE.put("Poland","Polen");
        COUNTRY_TABLE.put("Portugal","Portugal");
        COUNTRY_TABLE.put("Russia","Russland");
        COUNTRY_TABLE.put("Serbia","Serbien");
        COUNTRY_TABLE.put("Spain","Spanien");
        COUNTRY_TABLE.put("Sweden","Schweden");
        COUNTRY_TABLE.put("Switzerland","Schweiz");
        COUNTRY_TABLE.put("Costa Rica","Costa Rica");
        COUNTRY_TABLE.put("Mexico", "Mexiko");
        COUNTRY_TABLE.put("Germany","Deutschland");
        COUNTRY_TABLE.put("Panama","Panama");
        COUNTRY_TABLE.put("Argentina","Argentinien");
        COUNTRY_TABLE.put("Brazil","Brasilien");
        COUNTRY_TABLE.put("Colombia","Kolumbien");
        COUNTRY_TABLE.put("Peru","Peru");
        COUNTRY_TABLE.put("Uruguay","Uruguay");

    }
    public static int getResourceId(String countryName, Context context){
        return context.getResources().getIdentifier(countryName.toLowerCase().replaceAll(" ",""),"drawable", context.getPackageName());
    }

}
