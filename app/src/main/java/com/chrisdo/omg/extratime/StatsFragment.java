package com.chrisdo.omg.extratime;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.chrisdo.omg.extratime.data.CountryLookup;
import com.chrisdo.omg.extratime.data.Match;
import com.chrisdo.omg.extratime.data.MatchData;
import com.chrisdo.omg.extratime.data.StatsData;
import com.chrisdo.omg.extratime.data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatsFragment extends Fragment implements ValueEventListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView list = getView().findViewById(R.id.list_view_stats);

        //add the keyset, we translate it and also get the flag for the list
        ArrayList<Match> matches = (ArrayList<Match>) MatchData.INSTANCE.getFinishedMatches();
        final MatchArrayAdapter adapter = new MatchArrayAdapter(getActivity(), matches.toArray(new Match[]{}));
        list.setAdapter(adapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.stats_fragment, container, false);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //this works, use StatsData.INSTANCE().getPlayers2(this)///
        ListView list = getView().findViewById(R.id.list_view_stats);

        //add the keyset, we translate it and also get the flag for the list
       ArrayList<Match> matches = (ArrayList<Match>) MatchData.INSTANCE.getFinishedMatches();

        final MatchArrayAdapter adapter = new MatchArrayAdapter(getActivity(), matches.toArray(new Match[]{}));
        list.setAdapter(adapter);
        }



    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


    class MatchArrayAdapter extends ArrayAdapter<Match> {

        Context context;
        Match[] values;

        MatchArrayAdapter(Context context, Match[] values){
            super(context, -1, values);
            this.context= context;
            this.values = values;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.stats_row, parent, false);

            TextView textViewHome = (TextView) rowView.findViewById(R.id.stats_home_name);

            ImageView imageViewHome = (ImageView) rowView.findViewById(R.id.stats_home_flag);

            TextView textViewAway = (TextView) rowView.findViewById(R.id.stats_away_name);

            ImageView imageViewAway = (ImageView) rowView.findViewById(R.id.stats_away_flag);
            TextView textViewResult = (TextView) rowView.findViewById(R.id.stats_row_result);


            textViewHome.setText(CountryLookup.COUNTRY_TABLE.get(values[position].getHomeTeamName()));
            textViewAway.setText(CountryLookup.COUNTRY_TABLE.get(values[position].getAwayTeamName()));

            imageViewHome.setImageResource(CountryLookup.getResourceId(values[position].getHomeTeamName(), getContext()));
            imageViewAway.setImageResource(CountryLookup.getResourceId(values[position].getAwayTeamName(), getContext()));
            String result = values[position].getResult().getGoalsHomeTeam() + " : " + values[position].getResult().getGoalsAwayTeam();
textViewResult.setText(result);
            return rowView;
        }
    }

}
