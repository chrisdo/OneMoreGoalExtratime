package com.chrisdo.omg.extratime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chrisdo.omg.extratime.data.User;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = null;

        if(User.getUsername(this)==null){
            //go to new user activity
            intent = new Intent(this, SetUserActivity.class);
        }else{
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();

    }

}
