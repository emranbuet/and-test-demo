package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MatchDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        Bundle extras = getIntent().getExtras();
        String msg = "";
        if(extras != null){
            msg = extras.getString("PlayerList1");
        }
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        TextView myTextView = (TextView) findViewById(R.id.tvPlayers);
        myTextView.append(msg);
    }
}
