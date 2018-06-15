package com.chrisdo.omg.extratime;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.chrisdo.omg.extratime.data.Bet;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsFragment extends Fragment implements ValueEventListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* ListView list = getView().findViewById(R.id.list_view_stats);

        //add the keyset, we translate it and also get the flag for the list
        ArrayList<Match> matches = (ArrayList<Match>) MatchData.INSTANCE.getFinishedMatches();
        final MatchArrayAdapter adapter = new MatchArrayAdapter(getActivity(), matches.toArray(new Match[]{}));
        list.setAdapter(adapter);

*/
        final ExpandableListView list = getView().findViewById(R.id.list_view_stats);

        //add the keyset, we translate it and also get the flag for the list
        final ArrayList<Match> matches = (ArrayList<Match>) MatchData.INSTANCE.getFinishedMatches();
        final Map<Match,Map<String,Bet>> childList = new HashMap<>();
        for (Match m : matches){
            childList.put(m,m.getPlacedBets());
        }
// Create an ExpandableListAdapter object, this object will be used to provide data to ExpandableListView.
        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getGroupCount() {
                return matches.size();
            }

            @Override
            public int getChildrenCount(int groupIndex) {
                return 1;

            }

            @Override
            public Object getGroup(int groupIndex) {
                return matches.get(groupIndex);
            }

            @Override
            public Object getChild(int groupIndex, int childIndex) {
                Match group = matches.get(groupIndex);
             return  childList.get(group);

            }

            @Override
            public long getGroupId(int groupIndex) {
                return groupIndex;
            }

            @Override
            public long getChildId(int groupIndex, int childIndex) {
                return childIndex;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            // This method will return a View object displayed in group list item.
            @Override
            public View getGroupView(int groupIndex, boolean isExpanded, View view, ViewGroup viewGroup) {
                // Create the group view object.
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.stats_row,viewGroup, false);

                TextView textViewHome = (TextView) rowView.findViewById(R.id.stats_home_name);

                ImageView imageViewHome = (ImageView) rowView.findViewById(R.id.stats_home_flag);

                TextView textViewAway = (TextView) rowView.findViewById(R.id.stats_away_name);

                ImageView imageViewAway = (ImageView) rowView.findViewById(R.id.stats_away_flag);
                TextView textViewResult = (TextView) rowView.findViewById(R.id.stats_row_result);

                textViewHome.setText(CountryLookup.COUNTRY_TABLE.get(matches.get(groupIndex).getHomeTeamName()));
                textViewAway.setText(CountryLookup.COUNTRY_TABLE.get(matches.get(groupIndex).getAwayTeamName()));

                imageViewHome.setImageResource(CountryLookup.getResourceId(matches.get(groupIndex).getHomeTeamName(), getContext()));
                imageViewAway.setImageResource(CountryLookup.getResourceId(matches.get(groupIndex).getAwayTeamName(), getContext()));
                String result = matches.get(groupIndex).getResult().getGoalsHomeTeam() + " : " + matches.get(groupIndex).getResult().getGoalsAwayTeam();
                textViewResult.setText(result);
                TextView textViewOngoing = (TextView) rowView.findViewById(R.id.stats_row_match_ongoing);

                if(matches.get(groupIndex).getStatus().equals(Match.Status.FINISHED)){
                    textViewOngoing.setVisibility(View.GONE);
                }

                return rowView;
            }

            // This method will return a View object displayed in child list item.
            @Override
            public View getChildView(int groupIndex, int childIndex, boolean isLastChild, View view, ViewGroup viewGroup) {
                // First get child text/
                Object childObj = this.getChild(groupIndex, childIndex);

                Map<String,Bet> childText = (Map<String,Bet>)childObj;
                TextView childTextView = new TextView(getContext());
                childTextView.setPadding(40,0,0,0);
                childTextView.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
                StringBuilder sb = new StringBuilder();
                for(Map.Entry<String,Bet> entry : childText.entrySet()){
                    sb.append(entry.getKey() + " - " + entry.getValue().getScoreHome() + ":" + entry.getValue().getScoreAway()+"\n");
                    // Get group image width.

                    // Set child textview offset left. Then it will align to the right of the group image.


                }
                childTextView.setText(sb.toString());
                // Create a TextView to display child text.
                return childTextView;

            }

            @Override
            public boolean isChildSelectable(int groupIndex, int childIndex) {
                return false;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupIndex) {

            }

            @Override
            public void onGroupCollapsed(int groupIndex) {


            }

            @Override
            public long getCombinedChildId(long groupIndex, long childIndex) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupIndex) {
                return 0;
            }
        };

        list.setAdapter(expandableListAdapter);
        //   final MatchArrayAdapter adapter = new MatchArrayAdapter(getActivity(), matches.toArray(new Match[]{}));
        //  list.setAdapter(adapter);
        list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupIndex) {
                // Get total group size.
                int groupListSize = matches.size();

                // Close other expanded group.
                for(int i=0;i < groupListSize; i++) {
                    if(i!=groupIndex) {
                        list.collapseGroup(i);
                    }
                }
            }
        });
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
        final ExpandableListView list = getView().findViewById(R.id.list_view_stats);

        //add the keyset, we translate it and also get the flag for the list
      final ArrayList<Match> matches = (ArrayList<Match>) MatchData.INSTANCE.getFinishedMatches();
       final Map<Match,Map<String,Bet>> childList = new HashMap<>();
       for (Match m : matches){
           childList.put(m,m.getPlacedBets());
       }
// Create an ExpandableListAdapter object, this object will be used to provide data to ExpandableListView.
        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getGroupCount() {
                return matches.size();
            }

            @Override
            public int getChildrenCount(int groupIndex) {
               return 1;
            }

            @Override
            public Object getGroup(int groupIndex) {
                return matches.get(groupIndex);
            }

            @Override
            public Object getChild(int groupIndex, int childIndex) {
                Match group = matches.get(groupIndex);
                return  childList.get(group);

            }

            @Override
            public long getGroupId(int groupIndex) {
                return groupIndex;
            }

            @Override
            public long getChildId(int groupIndex, int childIndex) {
                return childIndex;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            // This method will return a View object displayed in group list item.
            @Override
            public View getGroupView(int groupIndex, boolean isExpanded, View view, ViewGroup viewGroup) {
                // Create the group view object.
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.stats_row,viewGroup, false);

                TextView textViewHome = (TextView) rowView.findViewById(R.id.stats_home_name);

                ImageView imageViewHome = (ImageView) rowView.findViewById(R.id.stats_home_flag);

                TextView textViewAway = (TextView) rowView.findViewById(R.id.stats_away_name);

                ImageView imageViewAway = (ImageView) rowView.findViewById(R.id.stats_away_flag);
                TextView textViewResult = (TextView) rowView.findViewById(R.id.stats_row_result);
                TextView textViewOngoing = (TextView) rowView.findViewById(R.id.stats_row_match_ongoing);

                textViewHome.setText(CountryLookup.COUNTRY_TABLE.get(matches.get(groupIndex).getHomeTeamName()));
                textViewAway.setText(CountryLookup.COUNTRY_TABLE.get(matches.get(groupIndex).getAwayTeamName()));

                imageViewHome.setImageResource(CountryLookup.getResourceId(matches.get(groupIndex).getHomeTeamName(), getContext()));
                imageViewAway.setImageResource(CountryLookup.getResourceId(matches.get(groupIndex).getAwayTeamName(), getContext()));
                String result = matches.get(groupIndex).getResult().getGoalsHomeTeam() + " : " + matches.get(groupIndex).getResult().getGoalsAwayTeam();
                textViewResult.setText(result);

                if(matches.get(groupIndex).getStatus().equals(Match.Status.FINISHED)){
                    textViewOngoing.setVisibility(View.GONE);
                }
                return rowView;
            }

            // This method will return a View object displayed in child list item.
            @Override
            public View getChildView(int groupIndex, int childIndex, boolean isLastChild, View view, ViewGroup viewGroup) {
                // First get child text/
                Object childObj = this.getChild(groupIndex, childIndex);
                Map<String,Bet> childText = (Map<String,Bet>)childObj;
                TextView childTextView = new TextView(getContext());
                childTextView.setPadding(20,0,0,0);
                childTextView.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
StringBuilder sb = new StringBuilder();
                for(Map.Entry<String,Bet> entry : childText.entrySet()){
    sb.append(entry.getKey() + " - " + entry.getValue().getScoreHome() + ":" + entry.getValue().getScoreAway()+"\n");
    // Get group image width.

    // Set child textview offset left. Then it will align to the right of the group image.


}
childTextView.setText(sb.toString());
                // Create a TextView to display child text.
                return childTextView;

            }

            @Override
            public boolean isChildSelectable(int groupIndex, int childIndex) {
                return false;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupIndex) {

            }

            @Override
            public void onGroupCollapsed(int groupIndex) {


            }

            @Override
            public long getCombinedChildId(long groupIndex, long childIndex) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupIndex) {
                return 0;
            }
        };

    list.setAdapter(expandableListAdapter);
     //   final MatchArrayAdapter adapter = new MatchArrayAdapter(getActivity(), matches.toArray(new Match[]{}));
      //  list.setAdapter(adapter);
        list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupIndex) {
                // Get total group size.
                int groupListSize = matches.size();

                // Close other expanded group.
                for(int i=0;i < groupListSize; i++) {
                    if(i!=groupIndex) {
                        list.collapseGroup(i);
                    }
                }
            }
        });
    }



    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


/*
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


*/
/*
            LinearLayout rowLayout = rowView.findViewById(R.id.stats_row_bets);
            for(Map.Entry<String,Bet> entry : values[position].getPlacedBets().entrySet()){
                TextView t = new TextView(getContext());
                t.setText(entry.getKey() + " - " + entry.getValue().getScoreHome() + ":" + entry.getValue().getScoreAway());
                rowLayout.addView(t);
            }
*//*


            return rowView;
        }
    }
*/

}
