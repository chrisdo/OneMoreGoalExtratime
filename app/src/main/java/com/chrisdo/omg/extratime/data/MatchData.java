package com.chrisdo.omg.extratime.data;

import android.util.Log;

import com.chrisdo.omg.extratime.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MatchData implements ValueEventListener {


    public static final MatchData INSTANCE = new MatchData();


    private volatile Collection<Match> matches;

    private MatchData(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("matches");

        dbRef.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> snapshots = dataSnapshot.getChildren();

        Collection<Match> matches = new ArrayList<>();
GenericTypeIndicator<Map<String,Bet>> genericTypeBets = new GenericTypeIndicator<Map<String,Bet>>(){};

        for (DataSnapshot match : snapshots){
            //Match m = match.getValue(Match.class);
            Match m = new Match(match.child("id").getValue(Long.class),match.child("AwayTeamName").getValue(String.class),match.child("HomeTeamName").getValue(String.class),
                    match.child("Matchday").getValue(Integer.class),match.child("Date").getValue(String.class),
                    match.child("Result").getValue(Result.class), match.child("bets").getValue(genericTypeBets), match.child("Location").getValue(String.class),
                    match.getRef(), match.child("Status").getValue(String.class));

            matches.add(m);
            Log.i("MATCH", m.toString());
        }

        this.matches = matches;

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    Log.w("MATCH_DATA", databaseError.getDetails());
    }

    public Collection<Match> getMatches() {
        return new ArrayList<>(matches);
    }


    public Collection<Match> getFinishedMatches(){
        if (matches == null){
            return Collections.emptyList();
        }
        List<Match> result = new ArrayList<>();
        for (Match m : getMatches()){
            if(m.getStatus().equals(Match.Status.FINISHED) || !m.canBet()){
                result.add(m);
            }
        }
        Collections.sort(result, new Comparator<Match>() {

            @Override
            public int compare(Match o1, Match o2) {
                if(o1.getDate().before(o2.getDate())){
                    return -1;
                }else if(o1.getDate().after(o2.getDate())){
                    return 1;
                }return 0;
            }
        });
        return result;
    }

    public Collection<Match> getMatchesToBet(){
        if (this.matches == null) {
            Log.i("MATCH_DATA", "matches null");

            return Collections.emptyList();
        }
        List<Match> result = new ArrayList<Match>();
        for (Match m : getMatches()) {
            if (m.canBet()) {
                result.add(m);
            }
        }
        Collections.sort(result, new Comparator<Match>() {

            @Override
            public int compare(Match o1, Match o2) {
               if(o1.getDate().before(o2.getDate())){
                   return -1;
               }else if(o1.getDate().after(o2.getDate())){
                   return 1;
               }return 0;
            }
        });
        Log.i("MATCH_DATA", "matches to bet collections contains " + result.size() + " elements");
        //should be sorted by date
        return result;
    }

    public boolean waitForData() throws InterruptedException {

        int slept = 0;

        while (matches == null){
            Thread.sleep(200);
            slept+=200;
            if (slept > 10000) {
                return false;
            }
        }

        return true;

    }
}
