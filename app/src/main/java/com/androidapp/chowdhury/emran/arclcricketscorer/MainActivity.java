package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int MAX_TEAM_NAME_LENGTH = 20;
    public static String TEAM_NAME_1 = "TEAM_NAME_1";
    public static String TEAM_NAME_2 = "TEAM_NAME_2";
    public static String TOTAL_OVER = "TOTAL_OVER";

    // View input data
    private EditText etTeam1, etTeam2, etTotOver;
    private String strTeam1, strTeam2, strTotOver;
    private int totalOvers = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTeam1 = (EditText) findViewById(R.id.etTeamName);
        etTeam2 = (EditText) findViewById(R.id.etTeamName2);
        etTotOver = (EditText) findViewById(R.id.etTotalOvers);
    }

    public void startNewMatch(View view){
        Intent intent = new Intent(this, ScoreActivity.class);

        strTeam1 = etTeam1.getText().toString();
        strTeam2 = etTeam2.getText().toString();
        strTotOver = etTotOver.getText().toString();
        totalOvers = (TextUtils.isEmpty(strTotOver)) ? 16 : Integer.parseInt(strTotOver);

        if(TextUtils.isEmpty(strTeam1))
            Toast.makeText(getApplicationContext(), "Enter Name of First team", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strTeam2))
            Toast.makeText(getApplicationContext(), "Enter Name of Second team", Toast.LENGTH_SHORT).show();
        else {
            if (strTeam1.length() >= MAX_TEAM_NAME_LENGTH) {
                strTeam1 = strTeam1.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_1, strTeam1);

            if(strTeam2.length() >= MAX_TEAM_NAME_LENGTH){
                strTeam2 = strTeam2.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_2, strTeam2);
            //Toast.makeText(getApplicationContext(), "Total overs: " + String.valueOf(totalOvers), Toast.LENGTH_SHORT).show();
            intent.putExtra(TOTAL_OVER, totalOvers);

            startActivity(intent);
        }

    }
}
