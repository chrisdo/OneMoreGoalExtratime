package com.chrisdo.omg.extratime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.chrisdo.omg.extratime.data.StatsData;
import com.chrisdo.omg.extratime.data.User;

public  class SetTopScorerActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_topscorer);
    }


    public void setTopScorer(View view) {
        EditText textBox = findViewById(R.id.topscorer);

        if(textBox.length() > 0){
            StatsData.INSTANCE.makeTopscorerTip(textBox.getText().toString(),this);
            Intent intent = new Intent(this, SetManOfTheTournamentActivity.class);
            startActivity(intent);
            finish();
        }else{
            textBox.setError(getResources().getString(R.string.hint_wrong_fanname));
        }
    }
}
