package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // View input data
    private EditText etTeam1, etTeam2;
    private String strTeam1, strTeam2;
    private int totalOvers = 16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTeam1 = (EditText) findViewById(R.id.etTeamName);
        etTeam2 = (EditText) findViewById(R.id.etTeamName2);
    }

    public void startNewMatch(View view){
        Intent intent = new Intent(this, ScoreActivity.class);

        strTeam1 = etTeam1.getText().toString();
        strTeam2 = etTeam2.getText().toString();

        if(TextUtils.isEmpty(strTeam1))
            Toast.makeText(getApplicationContext(), "Enter Name of First team", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strTeam2))
            Toast.makeText(getApplicationContext(), "Enter Name of Second team", Toast.LENGTH_SHORT).show();
        else {
            if (strTeam1.length() >= 10) {
                strTeam1 = strTeam1.substring(0, 10);
            }
            intent.putExtra("strTeamName1", strTeam1);

            if(strTeam2.length() >= 10){
                strTeam2 = strTeam2.substring(0, 10);
            }
            intent.putExtra("strTeamName2", strTeam2);

            intent.putExtra("intTotalOvers", totalOvers);
            startActivity(intent);
        }

    }
}
