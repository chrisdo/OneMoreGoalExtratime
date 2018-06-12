package com.chrisdo.omg.extratime;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.chrisdo.omg.extratime.data.Score;
import com.chrisdo.omg.extratime.data.StatsData;
import com.chrisdo.omg.extratime.data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class StandingsFragment extends Fragment implements ValueEventListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         List<User> data =
                StatsData.INSTANCE.getAllPlayers();
         Collections.sort(data, new Comparator<User>() {
             @Override
             public int compare(User o1, User o2) {
                 return o1.getScore().compareTo(o2.getScore());
             }
         });
    //this does not work, i always get the same user name but different points???
        TableLayout table = getView().findViewById(R.id.standingsTable);

        int ctr = 1;

        for (User u : data) {

            Log.i("STATS_DATA_STANDING", u.getName() + ": " + u.getScore().getPoints());
            View tableRow = getLayoutInflater().inflate(R.layout.standings_row,null,false);
            TextView tvNumber = tableRow.findViewById(R.id.standings_row_place);
            TextView tvName = tableRow.findViewById(R.id.standings_row_name);
            TextView tvStats= tableRow.findViewById(R.id.standings_row_stats);
            TextView tvPoints = tableRow.findViewById(R.id.standings_row_points);
            tvNumber.setText(String.valueOf(ctr++));
            tvName.setText(u.getName());
            tvPoints.setText(String.valueOf(u.getScore().getPoints()));
            tvStats.setText(u.getScore().getCorrectBets()+"/"+u.getScore().getCorrectGoalRelations()+"/"+u.getScore().getCorrectTendencies());
            if(ctr%2!=0){
                tableRow.setBackgroundColor(getResources().getColor(R.color.colorRowDark, null));
            }else{
                tableRow.setBackgroundColor(getResources().getColor(R.color.colorRowLight, null));
            }
            table.addView(tableRow);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.standings, container, false);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //this works, use StatsData.INSTANCE().getPlayers2(this)///
        TableLayout table = getView().findViewById(R.id.standingsTable);

        int ctr = 1;

        List<User> users = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    User u = value.getValue(User.class);

                    Log.i("STATS_DATA_2", u.getName() + ": " + u.getScore().getPoints());
                    users.add(u);
                    View tableRow = getLayoutInflater().inflate(R.layout.standings_row,null,false);
                    TextView tvNumber = tableRow.findViewById(R.id.standings_row_place);
                    TextView tvName = tableRow.findViewById(R.id.standings_row_name);
                    TextView tvStats= tableRow.findViewById(R.id.standings_row_stats);
                    TextView tvPoints = tableRow.findViewById(R.id.standings_row_points);
                    tvNumber.setText(String.valueOf(ctr++));
                    tvName.setText(u.getName());
                    tvPoints.setText(String.valueOf(u.getScore().getPoints()));
                    tvStats.setText(u.getScore().getCorrectBets()+"/"+u.getScore().getCorrectGoalRelations()+"/"+u.getScore().getCorrectTendencies());

                    table.addView(tableRow);
                }


            }



    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
