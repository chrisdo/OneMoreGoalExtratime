package com.chrisdo.omg.extratime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chrisdo.omg.extratime.data.MatchData;
import com.chrisdo.omg.extratime.data.StatsData;
import com.chrisdo.omg.extratime.data.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String[] SPRUECHE = new String[]{"Mach ihn rein, ", "Mach dich warm, ", "Ab unter die Dusche, ", "Hauptsache Italien, ", "We have a grandios saison gespielt, "};
Random random = new Random();

    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;

    ProgressBar mMainProgress;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Bundle b;
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    fragment = new StandingsFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    fragment = new PlaceBetsFragment();
                    switchFragment(fragment);
                    return true;
               case R.id.navigation_notifications:
                    fragment = new StatsFragment();
                    switchFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setTitle(SPRUECHE[random.nextInt(SPRUECHE.length)] + User.getUsername(this));
        }else{
            setTitle(SPRUECHE[random.nextInt(SPRUECHE.length)] + User.getUsername(this));
        }
        mMainProgress = (ProgressBar) findViewById(R.id.main_progress);

       navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.GONE);


        AsyncTask<Void, Void, Void> task2 = new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    MatchData.INSTANCE.waitForData();


                } catch (InterruptedException e) {
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                showProgress(true);
                Log.i("MAIN_ACTIVITY", "onPreExecute");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                showProgress(false);
                Log.i("MAIN_ACTIVITY", "onPostExecute");
                fragment = new PlaceBetsFragment();
                switchFragment(fragment);


            }
        };

        task2.execute();

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    StatsData.INSTANCE.waitForData();


                } catch (InterruptedException e) {
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                showProgress(true);
                Log.i("MAIN_ACTIVITY", "onPreExecute");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                showProgress(false);
                Log.i("MAIN_ACTIVITY", "onPostExecute");



            }
        };

        task.execute();


    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            navigation.setVisibility(show ? View.GONE : View.VISIBLE);
            if(show){
                fragment = new PlaceBetsFragment();
                switchFragment(fragment);
                navigation.setSelectedItemId(R.id.navigation_dashboard);
            }

            mMainProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mMainProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMainProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mMainProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            if(show){
                fragment = new PlaceBetsFragment();
                switchFragment(fragment);
                navigation.setSelectedItemId(R.id.navigation_dashboard);
            }
        }

        if (show) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }

    }



}
