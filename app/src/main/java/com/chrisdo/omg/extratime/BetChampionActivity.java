package com.chrisdo.omg.extratime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisdo.omg.extratime.data.CountryLookup;
import com.chrisdo.omg.extratime.data.StatsData;
import com.chrisdo.omg.extratime.data.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BetChampionActivity extends AppCompatActivity{

    String selectedChampion = null;
    private int mSelectedItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bet_champion);

        ListView l = findViewById(R.id.list_view_champion);
        final List<String> teams = new ArrayList<>();
    //add the keyset, we translate it and also get the flag for the list
        teams.addAll(CountryLookup.COUNTRY_TABLE.keySet());
        Collections.sort(teams);
        final TeamArrayAdapter adapter = new TeamArrayAdapter(this, teams.toArray(new String[]{}));
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedItem = position;
                
                selectedChampion = teams.get(position);
                ActionBar bar = getSupportActionBar();
                if(bar != null){
                    bar.setTitle("Dein Weltmeister: " + selectedChampion);
                }else{
                    setTitle("Dein Weltmeister: " + selectedChampion);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void betChampion(View view) {
        if(selectedChampion != null){
            //User.setUsername(textBox.getText().toString(),this);
            StatsData.INSTANCE.makeChampionTip(selectedChampion,this);

            Intent intent = new Intent(this, SetTopScorerActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Bitte waehle einen Weltmeister", Toast.LENGTH_SHORT);
        }
    }

     class TeamArrayAdapter extends ArrayAdapter<String>{

        Context context;
        String[] values;

        TeamArrayAdapter(Context context, String[] values){
            super(context, -1, values);
            this.context= context;
            this.values = values;
        }

         @NonNull
         @Override
         public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
             LayoutInflater inflater = (LayoutInflater) context
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             View rowView = inflater.inflate(R.layout.bet_champion_row_layout, parent, false);
             TextView textView = (TextView) rowView.findViewById(R.id.champion_bet_label);
             ImageView imageView = (ImageView) rowView.findViewById(R.id.champion_bet_icon);
             textView.setText(CountryLookup.COUNTRY_TABLE.get(values[position]));
             imageView.setImageResource(CountryLookup.getResourceId(values[position], getContext()));

             //set the icon based on the country
             // change the icon for Windows and iPhone
             /*String s = values[position];
             if (s.startsWith("iPhone")) {
                 imageView.setImageResource(R.drawable.no);
             } else {
                 imageView.setImageResource(R.drawable.ok);
             }*/

             if (position == mSelectedItem) {
                 // set your color
                 rowView.setBackgroundColor(getResources().getColor(R.color.colorAccent, null));
             }


             return rowView;
         }
     }

}
