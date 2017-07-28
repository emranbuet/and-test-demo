package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

public class MainActivity extends AppCompatActivity {

    // View input data
    private EditText etTeam1, etTeam2, etTotOver, etTarget;
    private String strTeam1, strTeam2, strTotOver, strTarget;
    private int totalOvers = 0, targetRuns = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTeam1 = (EditText) findViewById(R.id.etTeamName);
        etTeam2 = (EditText) findViewById(R.id.etTeamName2);
        etTotOver = (EditText) findViewById(R.id.etTotalOvers);
        etTarget = (EditText) findViewById(R.id.etTargetRun);
    }

    public void startNewMatch(View view){
        IS_FIRST_INNINGS = true;
        Intent intent = new Intent(this, ScoreActivity.class);

        strTeam1 = etTeam1.getText().toString();
        strTeam2 = etTeam2.getText().toString();
        strTotOver = etTotOver.getText().toString();
        totalOvers = (TextUtils.isEmpty(strTotOver)) ? 16 : atoi(strTotOver);

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

            intent.putExtra(TOTAL_OVER, totalOvers);

            startActivity(intent);
        }

    }

//    public void startFirstInningsOnly(View v){
//        IS_FIRST_INNINGS = true;
//    }

    public void startSecondInningsOnly(View view){
        IS_FIRST_INNINGS = false;

        Intent intent = new Intent(this, ScoreActivity.class);

        strTeam1 = etTeam1.getText().toString();
        strTeam2 = etTeam2.getText().toString();
        strTotOver = etTotOver.getText().toString();
        totalOvers = (TextUtils.isEmpty(strTotOver)) ? 16 : atoi(strTotOver);
        strTarget = etTarget.getText().toString();
        targetRuns =  (TextUtils.isEmpty(strTarget)) ? 0 :atoi(strTarget);

        if(TextUtils.isEmpty(strTeam1))
            Toast.makeText(getApplicationContext(), "Enter Name of First team", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strTeam2))
            Toast.makeText(getApplicationContext(), "Enter Name of Second team", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(strTarget))
            Toast.makeText(getApplicationContext(), "Enter Target runs for 2nd innings", Toast.LENGTH_SHORT).show();
        else {
            if (strTeam1.length() >= MAX_TEAM_NAME_LENGTH) {
                strTeam1 = strTeam1.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_1, strTeam1);

            if(strTeam2.length() >= MAX_TEAM_NAME_LENGTH){
                strTeam2 = strTeam2.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_2, strTeam2);

            intent.putExtra(TOTAL_OVER, totalOvers);

            intent.putExtra(TARGET_RUNS, targetRuns);

            startActivity(intent);
        }
    }

}
