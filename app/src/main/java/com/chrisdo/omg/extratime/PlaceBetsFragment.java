package com.chrisdo.omg.extratime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.chrisdo.omg.extratime.data.Bet;
import com.chrisdo.omg.extratime.data.CountryLookup;
import com.chrisdo.omg.extratime.data.Match;
import com.chrisdo.omg.extratime.data.MatchData;
import com.chrisdo.omg.extratime.data.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class PlaceBetsFragment extends Fragment implements View.OnClickListener {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private TextView matchDate;
    private TextView matchPlace;

    private TextView group;
    private TextView teamHome;
    private TextView teamAway;
    private ImageView flagHome;
    private ImageView flagAway;
    private NumberPicker scoreHome;
    private NumberPicker scoreAway;

    private Button nextButton;
    private Iterator<Match> matchesIterator;
    private Match currentMatch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* Collection<Match> toBet = MatchData.INSTANCE.getMatchesToBet();

        matchesIterator = toBet.iterator();

        if(matchesIterator.hasNext()){
            showNextMatch();
        }*/

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      /*  Collection<Match> toBet = MatchData.INSTANCE.getMatchesToBet();

        matchesIterator = toBet.iterator();
*/
        Collection<Match> toBet = MatchData.INSTANCE.getMatchesToBet();

        matchesIterator = toBet.iterator();


        matchDate = view.findViewById(R.id.match_date);
        matchPlace = view.findViewById(R.id.match_place);

        group = view.findViewById(R.id.match_group);
        teamAway = view.findViewById(R.id.away_name);
        teamHome = view.findViewById(R.id.home_name);
        flagAway = view.findViewById(R.id.away_flag);
        flagHome = view.findViewById(R.id.home_flag);
        flagHome.getLayoutParams().width = (int) getResources().getDimension(R.dimen.flag_width);
        flagHome.getLayoutParams().height = (int) getResources().getDimension(R.dimen.flag_height);
        flagAway.getLayoutParams().width = (int) getResources().getDimension(R.dimen.flag_width);
        flagAway.getLayoutParams().height = (int) getResources().getDimension(R.dimen.flag_height);

        nextButton = view.findViewById(R.id.placeBet);
        scoreAway = view.findViewById(R.id.score_away);
        scoreHome = view.findViewById(R.id.score_home);
        scoreHome.setMinValue(0);
        scoreHome.setMaxValue(10);
        scoreAway.setMinValue(0);
        scoreAway.setMaxValue(10);
        nextButton.setOnClickListener(this);


        if (matchesIterator.hasNext()) {
            showNextMatch();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.match_bet_layout, container, false);

/*        matchDate = view.findViewById(R.id.match_date);
        group = view.findViewById(R.id.match_group);
        teamAway = view.findViewById(R.id.away_name);
        teamHome = view.findViewById(R.id.home_name);
        flagAway = view.findViewById(R.id.away_flag);
        flagHome = view.findViewById(R.id.home_flag);
        nextButton = view.findViewById(R.id.placeBet);
        scoreAway = view.findViewById(R.id.score_away);
        scoreHome = view.findViewById(R.id.score_home);

        nextButton.setOnClickListener(this);*/
        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        Bet bet = new Bet(scoreHome.getValue(), scoreAway.getValue());
        currentMatch.placeBet(bet, getContext());

        if (matchesIterator.hasNext()) {
            showNextMatch();
        }

    }

    private void showNextMatch() {
        currentMatch = matchesIterator.next();

        teamHome.setText(CountryLookup.COUNTRY_TABLE.get(currentMatch.getHomeTeamName()));

        teamAway.setText(CountryLookup.COUNTRY_TABLE.get(currentMatch.getAwayTeamName()));
matchPlace.setText(currentMatch.getLocation());
        FORMAT.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
matchDate.setText(FORMAT.format(currentMatch.getDate().getTime()));
        flagHome.setImageResource(CountryLookup.getResourceId(currentMatch.getHomeTeamName(), getContext()));
        flagAway.setImageResource(CountryLookup.getResourceId(currentMatch.getAwayTeamName(), getContext()));


        Bet b = currentMatch.getBet(getContext());
        if (b != null) {
            scoreAway.setValue(b.getScoreAway());
            scoreHome.setValue(b.getScoreHome());
        } else {
            scoreAway.setValue(0);
            scoreHome.setValue(0);
        }
        switch (currentMatch.getMatchDay()) {

            case 4:
                group.setText("Achtelfinale");
                break;
            case 5:
                group.setText("Viertelfinale");
                break;
            case 6:
                group.setText("Halbfinale");
                break;
            case 7:
                group.setText("Spiel um Platz 3");
                break;
            case 8:
                group.setText("Finale");
                break;
            default:
                group.setText("Gruppenphase Spieltag " + currentMatch.getMatchDay());
                break;
        }


    }


}
