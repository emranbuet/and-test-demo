package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.androidapp.chowdhury.emran.arclcricketscorer.R.id.btnContent;
import static com.androidapp.chowdhury.emran.arclcricketscorer.R.id.llFirst;

public class MatchDetail extends AppCompatActivity {

    public static String PLAYER_LIST_FIRST = "PlayerListOne";
    public static String PLAYER_LIST_SECOND = "PlayerListTwo";

    public static String TEAM_NAME_1 = "TEAM_NAME_1";
    public static String TEAM_NAME_2 = "TEAM_NAME_2";
    public static String TOTAL_OVER = "TOTAL_OVER";
    public static String PLAYER_LIST_FULL = "PLAYER_LIST_FULL";

    public static String PLAYER_LIST_STATUS = "PLAYER_STATUS";
    public static String PLAYER_ID_BATSMAN_1 = "Batsman1";
    public static String PLAYER_ID_BATSMAN_2 = "Batsman2";
    public static String PLAYER_ID_BOWLER = "Bowler";

    public static int NAME_LEN = 12;

    private ArrayList<Player> playersListTeam1, playersListTeam2;
    private HashMap<Integer, Player> playerHashMap;
    private String playerListAll = null;
    private String strTeam1, strTeam2, strTotOver, strContent, strDetail, preStrDetail, preStrContent;
    private int playerIdBt1, playerIdBt2, playerIdBowler;
    //private int totOver;

    // Related to scoring
    private int runA, runR, preRunA, preRunR;
    private double overA, overR, totOver, preOverA, preOverR;
    private int ballsA, ballsB, preBallsA, preBallsB;
    private int wickA, preWickA;
    private boolean firstBatting = true;
    private Button btnContent;
    private TextView tvTeam1, tvRunA, tvWickA, tvOverA, tvRunR, tvOverR, tvRunDetail, tvStatus;
    private TextView tvNameBt1,  tvRunBt1, tvBallBt1, tvFourBt1, tvSixBt1;
    private TextView tvNameBt2, tvRunBt2, tvBallBt2, tvFourBt2, tvSixBt2;
    private TextView tvNameBl, tvOverBl, tvRunBl, tvWicketBl, tvWideBl, tvNoBl;
    private TextView tvTextBy, tvTextIn, tvTextOver;
    private LinearLayout llFirst, llRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRunR = (TextView)findViewById(R.id.tVRunR);
        tvOverR = (TextView)findViewById(R.id.tVOverR);
        tvRunDetail = (TextView)findViewById(R.id.tVRunDetail);
        tvTeam1 = (TextView)findViewById(R.id.tVNameTeam);
        tvNameBt1 = (TextView)findViewById(R.id.tVNameBt1);
        tvNameBt2 = (TextView)findViewById(R.id.tVNameBt2);
        tvNameBl = (TextView)findViewById(R.id.tVNameBl);

        llFirst = (LinearLayout)findViewById(R.id.llFirst);
        llRequired = (LinearLayout)findViewById(R.id.llRequired);

        tvRunA = (TextView)findViewById(R.id.tVRunA);
        tvWickA = (TextView)findViewById(R.id.tVWicketA);
        tvOverA = (TextView)findViewById(R.id.tVOverA);
        tvRunBt1 = (TextView)findViewById(R.id.tVRunBt1);
        tvBallBt1 = (TextView)findViewById(R.id.tVBallBt1);
        tvFourBt1 = (TextView)findViewById(R.id.tVFourBt1);
        tvSixBt1 = (TextView)findViewById(R.id.tVSixBt1);
        tvRunBt2 = (TextView)findViewById(R.id.tVRunBt2);
        tvBallBt2 = (TextView)findViewById(R.id.tVBallBt2);
        tvFourBt2 = (TextView)findViewById(R.id.tVFourBt2);
        tvSixBt2 = (TextView)findViewById(R.id.tVSixBt2);
        tvRunDetail = (TextView)findViewById(R.id.tVRunDetail);
        btnContent = (Button) findViewById(R.id.btnContent);
        tvOverBl = (TextView)findViewById(R.id.tVOverBl);
        tvRunBl = (TextView)findViewById(R.id.tVRunBl);
        tvWicketBl = (TextView)findViewById(R.id.tVWicketBl);
        tvWideBl = (TextView)findViewById(R.id.tVWideBl);
        tvNoBl = (TextView)findViewById(R.id.tVNoBl);

        tvTextBy = (TextView)findViewById(R.id.tVTextBy);
        tvTextIn = (TextView)findViewById(R.id.tVTextIn);
        tvTextOver = (TextView)findViewById(R.id.tVTextOver);
//        tvTextBy2 = (TextView)findViewById(R.id.tVTextBy2);
//        tvTextIn2 = (TextView)findViewById(R.id.tVTextIn2);
//        tvTextOver2 = (TextView)findViewById(R.id.tVTextOver2);
        setInitial();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            //playerListAll= bundle.getString("PlayerListAll");
            strTeam1 = bundle.getString(TEAM_NAME_1);
            strTeam2 = bundle.getString(TEAM_NAME_2);
            strTotOver = bundle.getString(TOTAL_OVER);
            playersListTeam1 = (ArrayList<Player>)bundle.getSerializable(PLAYER_LIST_FIRST);
            playersListTeam2 = (ArrayList<Player>) bundle.getSerializable(PLAYER_LIST_SECOND);

            playerHashMap = (HashMap<Integer, Player>) bundle.getSerializable(PLAYER_LIST_FULL);

            playerIdBt1 = Integer.parseInt(bundle.getString(PLAYER_ID_BATSMAN_1));
            playerIdBt2 = Integer.parseInt(bundle.getString(PLAYER_ID_BATSMAN_2));
            playerIdBowler = Integer.parseInt(bundle.getString(PLAYER_ID_BOWLER));
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Bundle is null from previous activity");
        }

        // Testing
        if(playerHashMap != null){
            for(int id: playerHashMap.keySet()) {
                Player player = playerHashMap.get(id);
                Log.d("MatchDetail: " + LogType.TEST, player.getPlayerName() + " with id: " + player.getPlayerId());
            }
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Player HashMap is null");
        }

        Log.d("MatchDetail: " + LogType.TEST, "Id " + playerIdBt1 + " -> " + playerHashMap.get(playerIdBt1).getPlayerName());
        Log.d("MatchDetail: " + LogType.TEST, "Id " + playerIdBt2 + " -> " + playerHashMap.get(playerIdBt2).getPlayerName());
        Log.d("MatchDetail: " + LogType.TEST, "Id " + playerIdBowler + " -> " + playerHashMap.get(playerIdBowler).getPlayerName());

        //Toast.makeText(getApplicationContext(), "Player Map: " + playerHashMap.get(99) , Toast.LENGTH_SHORT).show();

        // Batting statistics and bowling statistics for players
        HashMap<Integer, BattingStatistics> batsmenStatsMap = new HashMap<>();
        HashMap<Integer, BowlingStatistics> bowlingStatsMap = new HashMap<>();
        CurrentBatsmanInfo[] activeBatsmen = new CurrentBatsmanInfo[2];

        // Set the initial team and batsman, bowler name in UI
        String strPlayerNameBt1, strPlayerNameBt2, strPlayerNameBowler;
        strPlayerNameBt1 = playerHashMap.get(playerIdBt1).getPlayerName();
        strPlayerNameBt2 = playerHashMap.get(playerIdBt2).getPlayerName();
        strPlayerNameBowler = playerHashMap.get(playerIdBowler).getPlayerName();
        String strTeamPartial1="", strTeamPartial2="", strBatsmanPartial1="", strBatsmanPartial2="", strBowlerPartial="";
        if(strTeam1 != null) {
            tvStatus.setText(strTeam1 + " is Batting");
            strTeamPartial1 = (strTeam1.length() > NAME_LEN) ? strTeam1.substring(0, NAME_LEN) : strTeam1;
            tvTeam1.setText(strTeamPartial1);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "First Team name is null");
        }
        if(strTotOver != null) {
            totOver = Double.parseDouble(strTotOver);
            tvOverR.setText(strTotOver);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Match total over is null");
        }
        if(strTeam2 != null) {
            strTeamPartial2 = (strTeam2.length() > NAME_LEN) ? strTeam2.substring(0, NAME_LEN) : strTeam2;
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Second Team name is null");
        }
        if(strPlayerNameBt1 != null) {
            strBatsmanPartial1 = (strPlayerNameBt1.length() > NAME_LEN) ? strPlayerNameBt1.substring(0, NAME_LEN) : strPlayerNameBt1;
            // Add first batsman's initial statistics in the map
            if(!batsmenStatsMap.containsKey(playerIdBt1)){
                BattingStatistics batStat1 = new BattingStatistics(playerIdBt1);
                batsmenStatsMap.put(playerIdBt1, batStat1);
            }
            CurrentBatsmanInfo btInfo1 = new CurrentBatsmanInfo(playerIdBt1, true, true);
            activeBatsmen[0] = btInfo1;
            tvNameBt1.setText(strBatsmanPartial1);
            tvNameBt1.setTextColor(Color.GREEN);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "First Batsman name is null");
        }
        if(strPlayerNameBt2 != null) {
            strBatsmanPartial2 = (strPlayerNameBt2.length() > NAME_LEN) ? strPlayerNameBt2.substring(0, NAME_LEN) : strPlayerNameBt2;
            // Add second batsman's initial statistics in the map
            if(!batsmenStatsMap.containsKey(playerIdBt2)){
                BattingStatistics batStat2 = new BattingStatistics(playerIdBt2);
                batsmenStatsMap.put(playerIdBt2, batStat2);
            }
            CurrentBatsmanInfo btInfo2 = new CurrentBatsmanInfo(playerIdBt2, false, false);
            activeBatsmen[1] = btInfo2;
            tvNameBt2.setText(strBatsmanPartial2);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Second Batsman name is null");
        }
        if(strPlayerNameBowler != null) {
            strBowlerPartial = (strPlayerNameBowler.length() > NAME_LEN) ? strPlayerNameBowler.substring(0, NAME_LEN) : strPlayerNameBowler;
            // Add the bowler's initial statistics in the map
            if(!bowlingStatsMap.containsKey(playerIdBowler)){
                BowlingStatistics bowlerStat = new BowlingStatistics(playerIdBowler);
                bowlingStatsMap.put(playerIdBowler, bowlerStat);
            }
            tvNameBl.setText(strBowlerPartial);
            tvNameBl.setTextColor(Color.GREEN);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Bowler name is null");
        }

        // Get all the UI items view to start the match

        // Previous Test Code for testing text views
        /*StringBuilder playerStringDump = new StringBuilder();
        playerStringDump.append(strTeam1);
        playerStringDump.append(" players are: ");
        playerStringDump.append(System.lineSeparator());
        if(playersListTeam1 != null) {
            for (int c = 0; c < playersListTeam1.size(); c++) {
                Player p = playersListTeam1.get(c);
                playerStringDump.append(p.getPlayerName());
                playerStringDump.append(": ");
                playerStringDump.append(p.getPlayerId());
                playerStringDump.append(System.lineSeparator());
            }
        }
        else{
            playerStringDump.append("player list 1 is null");
        }
        /*if(playersListTeam1 != null) {
            for (Player p : playersListTeam1) {
                 playerStringDump.append(p.getPlayerName());
                 playerStringDump.append(", ");
            }
        }
        else{
            playerStringDump.append("player list 1 is null");
        }*/
        /*TextView myTextView = (TextView) findViewById(R.id.tvPlayers);
        myTextView.append(playerStringDump.toString());*/

        /*playerStringDump = new StringBuilder();
        playerStringDump.append(strTeam2);
        playerStringDump.append(" players are: ");
        playerStringDump.append(System.lineSeparator());
        if(playersListTeam2 != null) {
            for (Player p : playersListTeam2) {
                playerStringDump.append(p.getPlayerName());
                playerStringDump.append(": ");
                playerStringDump.append(p.getPlayerId());
                playerStringDump.append(System.lineSeparator());
            }
        }
        else{
            playerStringDump.append("player list 2 is null");
        }

        //Toast.makeText(getApplicationContext(), "Total overs: " + strTotOver, Toast.LENGTH_SHORT).show();

        /*TextView myTextView2 = (TextView) findViewById(R.id.tvPlayers2);
        myTextView2.append(playerStringDump.toString());

        // Write player list in third box from stringbuilder
        TextView myTextView3 = (TextView) findViewById(R.id.tvPlayers3);
        myTextView3.append("Teams are playing ");
        myTextView3.append(String.valueOf(totOver));
        myTextView3.append(" overs match");*/
    }
    private void setInitial(){

        strContent = btnContent.getText().toString();
        tvTeam1.setTypeface(null, Typeface.BOLD);
        tvTeam1.setTextColor(Color.parseColor("#ff3e54ff"));

        tvRunA.setTextColor(Color.parseColor("#ff000000"));
        tvTextBy.setTextColor(Color.parseColor("#ff000000"));
        tvWickA.setTextColor(Color.parseColor("#ff000000"));
        tvTextIn.setTextColor(Color.parseColor("#ff000000"));
        tvOverA.setTextColor(Color.parseColor("#ff000000"));
        tvTextOver.setTextColor(Color.parseColor("#ff000000"));


        //llFirst.setBackgroundColor(Color.parseColor("#ffe5e4e2"));

//        tvStatus.setText(strTeam1 + " is Batting");
//        runA = 0;
//        runB = 0;
//        runR = runA - runB + 1;
//        overA = 0;
//        overB = 0;
//        overR = totOver - overB;
//        ballsA = 0;
//        ballsB = 0;
//        wickA = 0;
//        wickB = 0;
//        tvTeam1.setText(strTeam1 + ":");
//        tvTeam2.setText(strTeam2 + ":");
//        tvRunA.setText(Integer.toString(runA));
//        tvWickA.setText(Integer.toString(wickA));
//        tvOverA.setText(Double.toString(overA));
//
//        tvRunB.setText(Integer.toString(runB));
//        tvWickB.setText(Integer.toString(wickB));
//        tvOverB.setText(Double.toString(overB));
//
//        tvRunR.setText(Integer.toString(runR));
//        tvOverR.setText(Double.toString(overR));
    }

    public void hitOK(View v){  //write the code after each ball, update total runs, wicket, overs
        strContent = btnContent.getText().toString();
        strDetail = tvRunDetail.getText().toString();

        if(strContent.length() > 0) {
            preStrContent = strContent;
            preStrDetail = strDetail;
            preRunA = runA;
            //preRunB = runB;
            preRunR = runR;
            preOverA = overA;
            //preOverB = overB;
            preOverR = overR;
            preWickA = wickA;
            //preWickB = wickB;

            int run = 0;
            int fullOver10 = (int)totOver;
//            if (firstBatting) {
//                fullOver10 = (int) (overA * 10);
//            } else {
//                //fullOver10 = (int) (overB * 10);
//                //TODO: Check fullOver10 value here
//                fullOver10 = (int) (overA * 10);
//            } 
            int overOnly = fullOver10 / 10;
            int ball = fullOver10 % 10;

            if (strContent.length() == 1) {
                if (TextUtils.isDigitsOnly(strContent)) {
                    run = Integer.parseInt(strContent);
                    if (ball <= 4) {
                        ball++;
                        strDetail += "  ";
                        strDetail += strContent;
                    } else {
                        ball = 0;
                        overOnly++;
                        strDetail = "";
                        Toast.makeText(getApplicationContext(), "Over Completed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                char ch = strContent.charAt(0);
                run = 0;
                switch (ch) {
                    case 'n':
                        if (strContent.length() > 3) {
                            String strRunCon = strContent.substring(3);
                            if(TextUtils.isDigitsOnly(strRunCon)){
                                run = Integer.parseInt(strRunCon);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else
                            strContent = strContent.substring(0, 2);
                        run++;
                        strDetail += "  ";
                        strDetail += strContent;
                        break;
                    case 'w':
                        if (strContent.length() > 3){
                            String strRunCon = strContent.substring(3);
                            if(TextUtils.isDigitsOnly(strRunCon)){
                                run = Integer.parseInt(strRunCon);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else
                            strContent = strContent.substring(0, 2);
                        run++;
                        strDetail += "  ";
                        strDetail += strContent;
                        break;
                    case 'b':
                        if (strContent.length() > 1){
                            String strRunCon = strContent.substring(1);
                            if(TextUtils.isDigitsOnly(strRunCon)){
                                run = Integer.parseInt(strRunCon);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (ball == 5) {
                            overOnly++;
                            ball = 0;
                            strDetail = "";
                            Toast.makeText(getApplicationContext(), "Over Completed!", Toast.LENGTH_SHORT).show();
                        } else {
                            ball++;
                            strDetail += "  ";
                            strDetail += strContent;
                        }
                        break;
                    case 'W':
                        if (strContent.length() > 2) {
                            switch (strContent.charAt(2)) {
                                case 'n':
                                    if (strContent.length() > 5){
                                        String strRunCon = strContent.substring(5);
                                        if(TextUtils.isDigitsOnly(strRunCon)){
                                            run = Integer.parseInt(strRunCon);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    else
                                        strContent = strContent.substring(0, 4);
                                    run++;
                                    strDetail += "  ";
                                    strDetail += strContent;
                                    break;
                                case 'w':
                                    if (strContent.length() > 5){
                                        String strRunCon = strContent.substring(5);
                                        if(TextUtils.isDigitsOnly(strRunCon)){
                                            run = Integer.parseInt(strRunCon);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    else
                                        strContent = strContent.substring(0, 4);
                                    run++;
                                    strDetail += "  ";
                                    strDetail += strContent;
                                    break;
                                case 'b':
                                    if (strContent.length() > 3){
                                        String strRunCon = strContent.substring(3);
                                        if(TextUtils.isDigitsOnly(strRunCon)){
                                            run = Integer.parseInt(strRunCon);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    if (ball == 5) {
                                        overOnly++;
                                        ball = 0;
                                        strDetail = "";
                                        Toast.makeText(getApplicationContext(), "Over Completed!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        ball++;
                                        strDetail += "  ";
                                        strDetail += strContent;
                                    }
                                    break;
                                default:
                                    //run = Integer.parseInt(strContent.substring(2));
                                    String strRunCon = strContent.substring(2);
                                    if(TextUtils.isDigitsOnly(strRunCon)){
                                        run = Integer.parseInt(strRunCon);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Wicket and run combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (ball == 5) {
                                        overOnly++;
                                        ball = 0;
                                        strDetail = "";
                                        Toast.makeText(getApplicationContext(), "Over Completed!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        ball++;
                                        strDetail += "  ";
                                        strDetail += strContent;
                                    }
                                    break;
                            }
                        } else {
                            if (ball == 5) {
                                overOnly++;
                                ball = 0;
                                strDetail = "";
                                Toast.makeText(getApplicationContext(), "Over Completed!", Toast.LENGTH_SHORT).show();
                            } else {
                                ball++;
                                strDetail += "  ";
                                strContent = strContent.substring(0, 1); // Only Wicket fallen
                                strDetail += strContent;
                            }
                        }
                        if (firstBatting)
                            wickA++;
                        else
                            //wickB++;
                            //TODO
                        break;
                }
            }
            String strOver = overOnly + "." + ball;
            if (firstBatting) {
                runA += run;
                overA = overOnly + (ball / 10.0);
                tvRunA.setText(Integer.toString(runA));
                tvWickA.setText(Integer.toString(wickA));
                tvOverA.setText(strOver);
                runR = runA + 1;
                tvRunR.setText(Integer.toString(runR));
                if(overA == totOver){
                    Toast.makeText(getApplicationContext(), strTeam1 + "'s Batting Finished. Press Innings Finished to start 2nd Innings!", Toast.LENGTH_LONG).show();
                }
            } else {
                //TODO: runB += run;
                if(overA > totOver){
                    totOver = overA;
                    overR = overA;
                }
                //TODO: overB = overOnly + (ball / 10.0);
                //TODO: tvRunB.setText(Integer.toString(runB));
                //TODO: tvWickB.setText(Integer.toString(wickB));
                //TODO: tvOverB.setText(strOver);
//                if (runB > runA) {
//                    tvRunR.setText(Integer.toString(0));
//                    tvStatus.setText(strTeam2 + " won");
//                    Toast.makeText(getApplicationContext(), strTeam2 + " won the match", Toast.LENGTH_LONG).show();
//                }
                else {
                    //runR =  runA - runB + 1;
                    tvRunR.setText(Integer.toString(runR));
                }
                if (ball == 0) {
                    //double remOver = totOver - overB;
                    //overR = remOver;
                    //tvOverR.setText(Double.toString(remOver));
//                    if(overB == totOver){
//                        if(runA > runB){
//                            tvStatus.setText(strTeam1 + " won");
//                            Toast.makeText(getApplicationContext(), strTeam1 + " won the match by " + (runA - runB)+" runs", Toast.LENGTH_LONG).show();
//                        }
//                        else if(runA == runB){
//                            tvStatus.setText("Match Tie");
//                            Toast.makeText(getApplicationContext(), "Match Tie", Toast.LENGTH_LONG).show();
//                        }
//                    }
                } else {
                    int remOverOnly = (int) totOver - overOnly - 1;
                    int ballOnly = 6 - ball;
                    double remOver = remOverOnly + (ballOnly / 10.0);
                    overR = remOver;
                    tvOverR.setText(Double.toString(remOver));
                }
            }
            tvRunDetail.setText(strDetail);
            btnContent.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter 0 for dot ball", Toast.LENGTH_SHORT).show();
        }
    }
    public void clearContent(View v){
        btnContent.setText("");
    }
    public void buttonClicked(View v){
        int id = v.getId();
        strContent = btnContent.getText().toString();
        if(strContent == ""){
            switch(id){
                case R.id.btnZero:
                    strContent = "0";
                    break;
                case R.id.btnOne:
                    strContent = "1";
                    break;
                case R.id.btnTwo:
                    strContent = "2";
                    break;
                case R.id.btnThree:
                    strContent = "3";
                    break;
                case R.id.btnFour:
                    strContent = "4";
                    break;
                case R.id.btnFive:
                    strContent = "5";
                    break;
                case R.id.btnSix:
                    strContent = "6";
                    break;
                case R.id.btnSeven:
                    strContent = "7";
                    break;
                case R.id.btnNo:
                    strContent = "nb+";
                    break;
                case R.id.btnWide:
                    strContent = "wd+";
                    break;

                case R.id.btnBye:
                    strContent = "b";
                    break;
                case R.id.btnOut:
                    strContent = "W+";
                    break;
                default:
                    break;
            }
            btnContent.setText(strContent);
        }
        else {
            if (!TextUtils.isDigitsOnly(strContent)) {
                switch (id) {
                    case R.id.btnZero:
                        strContent += "0";
                        break;
                    case R.id.btnOne:
                        strContent += "1";
                        break;
                    case R.id.btnTwo:
                        strContent += "2";
                        break;
                    case R.id.btnThree:
                        strContent += "3";
                        break;
                    case R.id.btnFour:
                        strContent += "4";
                        break;
                    case R.id.btnFive:
                        strContent += "5";
                        break;
                    case R.id.btnSix:
                        strContent += "6";
                        break;
                    case R.id.btnSeven:
                        strContent += "7";
                        break;
                    case R.id.btnWide:
                        strContent += "wd+";
                        break;
                    case R.id.btnNo:
                        strContent += "nb+";
                        break;
                    case R.id.btnBye:
                        strContent += "b";
                        break;
                    default:
                        break;
                }
                btnContent.setText(strContent);
            }
        }
    }
    public void undoBall(View v){
        //Toast.makeText(getApplicationContext(), "Cant undo last ball now", Toast.LENGTH_SHORT).show();
        strContent = preStrContent;
        strDetail = preStrDetail;
        runA = preRunA;
        //runB = preRunB;
        runR = preRunR;
        overA = preOverA;
        //overB = preOverB;
        overR = preOverR;
        wickA = preWickA;
        //wickB = preWickB;
        if(firstBatting){
            tvRunA.setText(Integer.toString(runA));
            tvWickA.setText(Integer.toString(wickA));
            tvOverA.setText(Double.toString(overA));
        }
        else{
//            tvRunB.setText(Integer.toString(runB));
//            tvWickB.setText(Integer.toString(wickB));
//            tvOverB.setText(Double.toString(overB));
        }
        tvRunR.setText(Integer.toString(runR));
        tvOverR.setText(Double.toString(overR));
        tvRunDetail.setText(strDetail);
    }

    public void inningsComplete(View v){

        if(totOver < overA) {
            overR = overA;
            totOver = overA;
            tvOverR.setText(Double.toString(overR));
        }
        if(firstBatting)
            inningsCompleteChange(v);
        firstBatting = false;
    }
    private void inningsCompleteChange(View v){
        tvTeam1.setTypeface(null, Typeface.NORMAL);
        tvTeam1.setTextColor(Color.parseColor("#ff000000"));
        /*tvRunA.setTextColor(Color.parseColor("#ff000000"));
        tvTextBy.setTextColor(Color.parseColor("#ff000000"));
        tvWickA.setTextColor(Color.parseColor("#ff000000"));
        tvTextIn.setTextColor(Color.parseColor("#ff000000"));
        tvOverA.setTextColor(Color.parseColor("#ff000000"));
        tvTextOver.setTextColor(Color.parseColor("#ff000000"));*/

        //llFirst.setBackgroundColor(Color.TRANSPARENT);

        tvStatus.setText(strTeam2 + " is Batting");
        preStrDetail = strDetail;
        strDetail = "";
        tvRunDetail.setText(strDetail);
        //tvTeam2.setTextColor(Color.parseColor("#ff3e54ff"));
        //tvTeam2.setTypeface(null, Typeface.BOLD);

        //tvRunB.setTextColor(Color.parseColor("#ff000000"));
        //tvTextBy2.setTextColor(Color.parseColor("#ff000000"));
        //tvWickB.setTextColor(Color.parseColor("#ff000000"));
        //tvTextIn2.setTextColor(Color.parseColor("#ff000000"));
        //tvOverB.setTextColor(Color.parseColor("#ff000000"));
        //tvTextOver2.setTextColor(Color.parseColor("#ff000000"));

        //llSecond.setBackgroundColor(Color.parseColor("#ffe5e4e2"));
    }
    public void matchSave(View v){
        Toast.makeText(getApplicationContext(), "Match Can not be saved now", Toast.LENGTH_SHORT).show();
    }
    public void newMatch(View v){
        confirmation();
    }
    public void confirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning...");
        builder.setMessage("Are you sure? All score will be lost!");
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                gotoNewMatch();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void gotoNewMatch(){
        //super.onBackPressed();
        //Intent i = new Intent(this, New_Match.class);
        //finish();
        //startActivity(i);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
