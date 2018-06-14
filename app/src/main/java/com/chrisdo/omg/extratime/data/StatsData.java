package com.chrisdo.omg.extratime.data;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.chrisdo.omg.extratime.SetManOfTheTournamentActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatsData implements ValueEventListener {

    public static final StatsData INSTANCE = new StatsData();

    private volatile Collection<User> players;

    private DatabaseReference ref;

    private StatsData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("stats");

       ref.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //called once with initial data
        // and then everytime the data has changed
    Iterable<DataSnapshot> values = dataSnapshot.getChildren();
        //GenericTypeIndicator<Map<String,User>> t = new GenericTypeIndicator<Map<String,User>>(){};

       // Map<String,User> players = dataSnapshot.getValue(t);
        Collection<User> players = new ArrayList<>();

            for(DataSnapshot value : values){
                User u = value.getValue(User.class);

            Log.i("STATS_DATA", u.getName() + ": " + u.getScore().getPoints());
            players.add(u);

        }
        Log.d("STATS_DATA", "onDataChange: something has updated!!");

        this.players = players;
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    Log.e("STATS_DATA", "error getting from database " + databaseError.getDetails());
    }


    public List<User> getAllPlayers(){
        return new ArrayList<>(players);
    }


    public void getAllPlayers2(ValueEventListener listener){
        final List<User> users = new ArrayList<>();
    ref.addListenerForSingleValueEvent(listener);

       // return users;
    }


    public void addNewPlayer(Context context){
        String playername = User.getUsername(context);
        User u = new User(playername, new Score(),"", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("stats").child(playername).setValue(u);



    }

    public void makeTopscorerTip(String scorer, Context context){
        String playername = User.getUsername(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("stats");
        ref.child(playername).child("topScorer").setValue(scorer);

    }

    public void makeChampionTip(String champion, Context context){
        String playername = User.getUsername(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("stats");
        ref.child(playername).child("worldChampion").setValue(champion);

    }

    public boolean waitForData() throws InterruptedException {

        int slept = 0;

        while (this.players == null){
            Thread.sleep(200);
            slept+=200;
            if (slept > 10000) {
                Log.e("STATS_DATA", "Giving up gettingdata");
                return false;
            }
        }

        return true;

    }

    public void makeManOfTheTOurnamentTip(String s, Context context) {
        String playername = User.getUsername(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("stats");
        ref.child(playername).child("manOfTheTournament").setValue(s);

    }
}
