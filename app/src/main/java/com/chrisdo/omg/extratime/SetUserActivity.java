package com.chrisdo.omg.extratime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.chrisdo.omg.extratime.data.StatsData;
import com.chrisdo.omg.extratime.data.User;

public  class SetUserActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_username);
    }

    public void createUser(View view) {

        EditText textBox = findViewById(R.id.fanname);

        if(textBox.length() > 0){
            User.setUsername(textBox.getText().toString(),this);
            StatsData.INSTANCE.addNewPlayer(this);
            Intent intent = new Intent(this, BetChampionActivity.class);
            startActivity(intent);
            finish();
        }else{
            textBox.setError(getResources().getString(R.string.hint_wrong_fanname));
        }
    }
}
