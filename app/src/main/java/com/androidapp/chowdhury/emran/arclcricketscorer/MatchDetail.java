package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.Activity;
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
import java.util.List;

import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

public class MatchDetail extends AppCompatActivity {

    private MatchScorecard matchScorecard;
    private Team teamOne, teamTwo;


    private ArrayList<Player> playersListTeam1, playersListTeam2;
    private HashMap<Integer, Player> playerHashMap;
    private String playerListAll = null;
    private String strTeam1, strTeam2, strTotOver, strContent, strDetail, preStrDetail, preStrContent;
    private int playerIdBt1, playerIdBt2, playerIdBowler;
    private boolean overCompleted = false, swapMayNeed = false;
    //private int totOver;
    private boolean needToChangeBatsman = false;

    // Batsmen, bowler statistics and UI mapping of batsmen, strikers
    List<Player> batsmenList;
    int battingPosition;
    HashMap<Integer, Player> battingOrder;
    HashMap<String, OutType> outTypeHashMap;
    HashMap<Integer, BattingStatistics> preBatsmenStatsMap, batsmenStatsMap;
    HashMap<Integer, BowlingStatistics> preBowlingStatsMap, bowlingStatsMap;
    CurrentBatsmanInfo[] preActiveBatsmen, activeBatsmen;
    BowlingStatistics currentBowlerStat;

    // Related to scoring
    private static String strOver = "0";
    private static int overOnly = 0;
    private static int ball = 0;
    private static int run = 0;
    private static int runConcededByBowler = 0;
    private static int runConcededByTeam = 0;
    private static int numOfWicketByBowler = 0;
    private static boolean sameActivity = true;
    private static boolean isLastBall = false;
    private static boolean isWide = false;
    private static boolean isNo = false;
    private int runA, runR, preRunA, preRunR;
    private double overA, overR, totOver, preOverA, preOverR;
    private int ballsA, ballsB, preBallsA, preBallsB;
    private int wickA, preWickA;
    private boolean firstBatting = true;
    private Button btnContent;
    private TextView tvTeam1, tvRunA, tvWickA, tvOverA, tvRunR, tvOverR, tvRunDetail, tvStatus;
    private TextView tvNameBt1,  tvRunBt1, tvBallBt1, tvFourBt1, tvSixBt1;
    private TextView tvNameBt2, tvRunBt2, tvBallBt2, tvFourBt2, tvSixBt2;
    private TextView tvNameBowler, tvOverBowler, tvRunBowler, tvWicketBowler, tvWideBowler, tvNoBowler;
    private TextView tvTextBy, tvTextIn, tvTextOver;
    private LinearLayout llFirst, llRequired;
    private LinearLayout llBt1, llBt2, llBowler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        matchScorecard = new MatchScorecard(MATCH_ID);

        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRunR = (TextView)findViewById(R.id.tVRunR);
        tvOverR = (TextView)findViewById(R.id.tVOverR);
        tvRunDetail = (TextView)findViewById(R.id.tVRunDetail);
        tvTeam1 = (TextView)findViewById(R.id.tVNameTeam);
        tvNameBt1 = (TextView)findViewById(R.id.tVNameBt1);
        tvNameBt2 = (TextView)findViewById(R.id.tVNameBt2);
        tvNameBowler = (TextView)findViewById(R.id.tVNameBl);

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
        tvOverBowler = (TextView)findViewById(R.id.tVOverBl);
        tvRunBowler = (TextView)findViewById(R.id.tVRunBl);
        tvWicketBowler = (TextView)findViewById(R.id.tVWicketBl);
        tvWideBowler = (TextView)findViewById(R.id.tVWideBl);
        tvNoBowler = (TextView)findViewById(R.id.tVNoBl);

        tvTextBy = (TextView)findViewById(R.id.tVTextBy);
        tvTextIn = (TextView)findViewById(R.id.tVTextIn);
        tvTextOver = (TextView)findViewById(R.id.tVTextOver);
        llBt1 = (LinearLayout) findViewById(R.id.llBt1);
        llBt2 = (LinearLayout) findViewById(R.id.llBt2);
        llBowler = (LinearLayout) findViewById(R.id.llBl);
        setInitial();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            strTeam1 = bundle.getString(TEAM_NAME_1);
            strTeam2 = bundle.getString(TEAM_NAME_2);
            strTotOver = bundle.getString(TOTAL_OVER);
            playersListTeam1 = (ArrayList<Player>)bundle.getSerializable(PLAYER_LIST_FIRST);
            playersListTeam2 = (ArrayList<Player>) bundle.getSerializable(PLAYER_LIST_SECOND);

            playerHashMap = (HashMap<Integer, Player>) bundle.getSerializable(PLAYER_LIST_FULL);

            playerIdBt1 = atoi(bundle.getString(PLAYER_ID_BATSMAN_1));
            playerIdBt2 = atoi(bundle.getString(PLAYER_ID_BATSMAN_2));
            playerIdBowler = atoi(bundle.getString(PLAYER_ID_BOWLER));
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Bundle is null from previous activity");
        }

        // Batting statistics and bowling statistics for players
        batsmenList = new ArrayList<>();
        battingOrder = new HashMap<>();
        outTypeHashMap = new HashMap<>();
        outTypeHashMap.put("Bowled", OutType.Bowled);
        outTypeHashMap.put("Caught", OutType.Caught);
        outTypeHashMap.put("RunOut", OutType.RunOut);
        outTypeHashMap.put("Stumped", OutType.Stumped);
        outTypeHashMap.put("Other", OutType.Other);
        batsmenStatsMap = new HashMap<>();
        preBatsmenStatsMap = new HashMap<>();
        bowlingStatsMap = new HashMap<>();
        preBowlingStatsMap = new HashMap<>();
        activeBatsmen = new CurrentBatsmanInfo[2];
        preActiveBatsmen = new CurrentBatsmanInfo[2];

        // Set the initial team and batsman, bowler name in UI
        String strPlayerNameBt1, strPlayerNameBt2, strPlayerNameBowler;
        strPlayerNameBt1 = playerHashMap.get(playerIdBt1).getPlayerName();
        strPlayerNameBt2 = playerHashMap.get(playerIdBt2).getPlayerName();
        strPlayerNameBowler = playerHashMap.get(playerIdBowler).getPlayerName();
        String strTeamPartial1="", strTeamPartial2="", strBatsmanPartial1="", strBatsmanPartial2="", strBowlerPartial="";
        if(strTeam1 != null) {
            teamOne = new Team(strTeam1, TEAM_ID_1);
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
            teamTwo = new Team(strTeam2, TEAM_ID_2);
            //strTeamPartial2 = (strTeam2.length() > NAME_LEN) ? strTeam2.substring(0, NAME_LEN) : strTeam2;
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Second Team name is null");
        }
        if(strPlayerNameBt1 != null) {
            strBatsmanPartial1 = (strPlayerNameBt1.length() > NAME_LEN) ? strPlayerNameBt1.substring(0, NAME_LEN) : strPlayerNameBt1;
            // Add first batsman's initial statistics in the map
            Player thisBatsman = playerHashMap.get(playerIdBt1);
            battingPosition = 1;
            batsmenList.add(thisBatsman);
            battingOrder.put(battingPosition, thisBatsman);
            if(!batsmenStatsMap.containsKey(playerIdBt1)){
                BattingStatistics batStat1 = new BattingStatistics(playerIdBt1, thisBatsman.getPlayerName());
                batsmenStatsMap.put(playerIdBt1, batStat1);
            }
            CurrentBatsmanInfo btInfo1 = new CurrentBatsmanInfo(playerIdBt1, true, true);
            activeBatsmen[0] = btInfo1;
            // Set the first batsman info in UI with green color
            tvNameBt1.setText(strBatsmanPartial1);
            tvNameBt1.setTextColor(Color.BLACK);
            tvNameBt1.setTypeface(null, Typeface.BOLD);
            llBt1.setBackgroundColor(Color.parseColor("#00cccc"));
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "First Batsman name is null");
        }
        if(strPlayerNameBt2 != null) {
            strBatsmanPartial2 = (strPlayerNameBt2.length() > NAME_LEN) ? strPlayerNameBt2.substring(0, NAME_LEN) : strPlayerNameBt2;
            // Add second batsman's initial statistics in the map
            Player secondBatsman = playerHashMap.get(playerIdBt2);
            battingPosition = 2;
            batsmenList.add(secondBatsman);
            battingOrder.put(battingPosition, secondBatsman);
            if(!batsmenStatsMap.containsKey(playerIdBt2)){
                BattingStatistics batStat2 = new BattingStatistics(playerIdBt2, secondBatsman.getPlayerName());
                batsmenStatsMap.put(playerIdBt2, batStat2);
            }
            CurrentBatsmanInfo btInfo2 = new CurrentBatsmanInfo(playerIdBt2, false, false);
            activeBatsmen[1] = btInfo2;
            // Set the second batsman info in UI
            tvNameBt2.setText(strBatsmanPartial2);
            tvNameBt2.setTextColor(Color.BLACK);
            tvNameBt2.setTypeface(null, Typeface.NORMAL);
            llBt2.setBackgroundColor(Color.parseColor("#e5e5e5"));
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Second Batsman name is null");
        }
        if(strPlayerNameBowler != null) {
            strBowlerPartial = (strPlayerNameBowler.length() > NAME_LEN) ? strPlayerNameBowler.substring(0, NAME_LEN) : strPlayerNameBowler;
            // Add the bowler's initial statistics in the map
            if(!bowlingStatsMap.containsKey(playerIdBowler)){
                BowlingStatistics bowlerStat = new BowlingStatistics(playerIdBowler, playerHashMap.get(playerIdBowler).getPlayerName());
                bowlingStatsMap.put(playerIdBowler, bowlerStat);
            }
            currentBowlerStat = bowlingStatsMap.get(playerIdBowler);
            // Set the bowler info in UI with green color
            tvNameBowler.setText(strBowlerPartial);
            tvNameBowler.setTextColor(Color.BLACK);
            tvNameBowler.setTypeface(null, Typeface.BOLD);
            llBowler.setBackgroundColor(Color.parseColor("#00cccc"));
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Bowler name is null");
        }
    }
    private void setInitial(){
        btnContent.setText("");
        strContent = btnContent.getText().toString();
        tvTeam1.setTypeface(null, Typeface.BOLD);
        tvTeam1.setTextColor(Color.parseColor("#ff3e54ff"));
        tvRunA.setTextColor(Color.parseColor("#ff000000"));
        tvTextBy.setTextColor(Color.parseColor("#ff000000"));
        tvWickA.setTextColor(Color.parseColor("#ff000000"));
        tvTextIn.setTextColor(Color.parseColor("#ff000000"));
        tvOverA.setTextColor(Color.parseColor("#ff000000"));
        tvTextOver.setTextColor(Color.parseColor("#ff000000"));

        overCompleted = false;
        swapMayNeed = false;
    }
    // Check the activeBatsmen info to return the first UI label of batsman's index in activeBatsmen Array
    private int getFirstUILabelBatsmanIndexInArray(){
        return ((activeBatsmen[0].isDisplayedFirst())? 0 : 1);
    }
    // Check the activeBatsmen info to return the striker's index in activeBatsmen Array
    private int getStrikerBatsmanIndexInArray(){
        return ((activeBatsmen[0].isStriker())? 0 : 1);
    }
    private void swapBatsmen(){
        int strikerIndex = getStrikerBatsmanIndexInArray();
        activeBatsmen[strikerIndex].setStriker(false);
        activeBatsmen[1 - strikerIndex].setStriker(true);
        if(activeBatsmen[strikerIndex].isDisplayedFirst()){
            tvNameBt1.setTypeface(null, Typeface.NORMAL);
            llBt1.setBackgroundColor(Color.parseColor("#e5e5e5"));
            tvNameBt2.setTypeface(null, Typeface.BOLD);
            llBt2.setBackgroundColor(Color.parseColor("#00cccc"));
        }
        else{
            tvNameBt2.setTypeface(null, Typeface.NORMAL);
            llBt2.setBackgroundColor(Color.parseColor("#e5e5e5"));
            tvNameBt1.setTypeface(null, Typeface.BOLD);
            llBt1.setBackgroundColor(Color.parseColor("#00cccc"));
        }
    }
    // Following function is for swapping batsmen from UI if something is wrong after runout
    public void swapBatsmenByButton(View v){
        int strikerIndex = getStrikerBatsmanIndexInArray();
        activeBatsmen[strikerIndex].setStriker(false);
        activeBatsmen[1 - strikerIndex].setStriker(true);
        if(activeBatsmen[strikerIndex].isDisplayedFirst()){
            tvNameBt1.setTypeface(null, Typeface.NORMAL);
            llBt1.setBackgroundColor(Color.parseColor("#e5e5e5"));
            tvNameBt2.setTypeface(null, Typeface.BOLD);
            llBt2.setBackgroundColor(Color.parseColor("#00cccc"));
        }
        else{
            tvNameBt2.setTypeface(null, Typeface.NORMAL);
            llBt2.setBackgroundColor(Color.parseColor("#e5e5e5"));
            tvNameBt1.setTypeface(null, Typeface.BOLD);
            llBt1.setBackgroundColor(Color.parseColor("#00cccc"));
        }
    }
    private int updateRunsInBatting(int currentTeamRun, int run){
        int strikerIndex = getStrikerBatsmanIndexInArray();
        int strikerPlayerId = activeBatsmen[strikerIndex].getBatsmanPlayerId();
        // update striker batsmen information
        BattingStatistics strikerStat = batsmenStatsMap.get(strikerPlayerId);
        strikerStat.setBallsFaced(strikerStat.getBallsFaced() + 1);
        strikerStat.setRunsScored(strikerStat.getRunsScored() + run);
        if(run == 4 || run == 6){
            if(run == 4){
                strikerStat.setNumOf4s(strikerStat.getNumOf4s() + 1);
            }
            else if(run == 6){
                strikerStat.setNumOf6s(strikerStat.getNumOf6s() + 1);
            }
        }
        batsmenStatsMap.put(strikerPlayerId, strikerStat);
        return (currentTeamRun + run);
    }
    private void updateBowlingStat(int runConcededByBowler, int numOfWicket, boolean isWide, boolean isNo, int runConcededByTeam){
        double oversBowledByBowler = currentBowlerStat.getOversBowled();
        int overAndBallCountDecimal = (int)(oversBowledByBowler * 10);
        int ballOnlyBowler = overAndBallCountDecimal%10;
        int overOnlyBowler = overAndBallCountDecimal/10;
        if(!isWide && !isNo){
            ballOnlyBowler++;
        }
        currentBowlerStat.setRunsConceded(currentBowlerStat.getRunsConceded() + runConcededByBowler);
        currentBowlerStat.setNumberOfWickets(currentBowlerStat.getNumberOfWickets() + numOfWicket);
        if(isWide){
            currentBowlerStat.setWidesCount(currentBowlerStat.getWidesCount() + runConcededByBowler);
        }
        if(isNo){
            currentBowlerStat.setNoBallCount(currentBowlerStat.getNoBallCount() + 1);
        }
        if(ballOnlyBowler == 6){
            overOnlyBowler++;
            ballOnlyBowler = 0;
        }
        double currentOversForBowler = overOnlyBowler + (ballOnlyBowler/10.0);
        currentBowlerStat.setOversBowled(currentOversForBowler);
    }
    // Change after each over
    private void changeCurrentBowler(){
        sameActivity = false;
        bowlingStatsMap.put(currentBowlerStat.getBowlerPlayerId(), currentBowlerStat);
        // Get the new bowler id from UI dialog
        Log.d("NewBowler: " + LogType.TEST, "Calling a new bowler activity");
        Intent newBowlerIntent = new Intent(getApplicationContext(), NewBowlerActivity.class);
        ArrayList<Player> bowlerListAvailable = new ArrayList<Player>();
        for(Player p: playersListTeam2){
            if(p.getPlayerId() != currentBowlerStat.getBowlerPlayerId()){
                if(bowlingStatsMap.containsKey(p.getPlayerId())) {
                    int oversBowled = (int) bowlingStatsMap.get(p.getPlayerId()).getOversBowled();
                    if (oversBowled == 4) {
                        continue;
                    }
                }
                bowlerListAvailable.add(p);
            }
        }
        newBowlerIntent.putExtra(PLAYER_LIST_BOWLERS, bowlerListAvailable);
        newBowlerIntent.putExtra(PLAYER_LIST_FULL, playerHashMap);
        startActivityForResult(newBowlerIntent, NEW_BOWLER_ACTIVITY_REQ_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_BOWLER_ACTIVITY_REQ_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String bowlerIdStr = data.getStringExtra(NEW_BOWLER_ID);
                int newBowlerPlayerId = atoi(bowlerIdStr);
                BowlingStatistics newBowlerStat = null;
                if(!bowlingStatsMap.containsKey(newBowlerPlayerId)) {
                    newBowlerStat = new BowlingStatistics(newBowlerPlayerId, playerHashMap.get(newBowlerPlayerId).getPlayerName());
                    bowlingStatsMap.put(newBowlerPlayerId, newBowlerStat);
                }
                currentBowlerStat = bowlingStatsMap.get(newBowlerPlayerId);
                Log.d("NewBowler: " + LogType.TEST, "Current Bowler name:" + playerHashMap.get(currentBowlerStat.getBowlerPlayerId()).getPlayerName());
                String bowlerFullName = playerHashMap.get(currentBowlerStat.getBowlerPlayerId()).getPlayerName();
                String strBowlerNamePartial = (bowlerFullName.length() > NAME_LEN) ? bowlerFullName.substring(0, NAME_LEN) : bowlerFullName;
                tvNameBowler.setText(strBowlerNamePartial);
                tvOverBowler.setText(Double.toString(currentBowlerStat.getOversBowled()));
                tvRunBowler.setText(itoa(currentBowlerStat.getRunsConceded()));
                tvWicketBowler.setText(itoa(currentBowlerStat.getNumberOfWickets()));
                tvWideBowler.setText(itoa(currentBowlerStat.getWidesCount()));
                tvNoBowler.setText(itoa(currentBowlerStat.getNoBallCount()));
                updateScreen();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "You need to select next bowler to continue", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == NEW_BATSMAN_ACTIVITY_REQ_CODE) {
            if(resultCode == Activity.RESULT_OK){
                numOfWicketByBowler = 1;
                String batsmanIdIdStr = data.getStringExtra(NEW_BATSMAN_ID);
                int newBatsmanPlayerId = atoi(batsmanIdIdStr);
                BattingStatistics newBatsmanStat = null;
                Player newBatsmanPlayer = playerHashMap.get(newBatsmanPlayerId);
                batsmenList.add(newBatsmanPlayer);
                battingOrder.put(battingPosition, newBatsmanPlayer);
                if(!batsmenStatsMap.containsKey(newBatsmanPlayerId)) {
                    newBatsmanStat = new BattingStatistics(newBatsmanPlayerId, newBatsmanPlayer.getPlayerName());
                    batsmenStatsMap.put(newBatsmanPlayerId, newBatsmanStat);
                }
                String outTypeStr = data.getStringExtra(OUT_TYPE_STR);
                Log.d("OUT_TYPE: " + LogType.TEST, " Batsman was:" + outTypeStr);
                boolean isStrikerGotOut = true;
                int strikerIndex = getStrikerBatsmanIndexInArray();
                int strikerPlayerId = activeBatsmen[strikerIndex].getBatsmanPlayerId();
                String batsmanFullName = playerHashMap.get(newBatsmanPlayerId).getPlayerName();
                String strBatsmanNamePartial = (batsmanFullName.length() > NAME_LEN) ? batsmanFullName.substring(0, NAME_LEN) : batsmanFullName;
                if(!batsmenStatsMap.containsKey(newBatsmanPlayerId)){
                    BattingStatistics batStatNew = new BattingStatistics(newBatsmanPlayerId, playerHashMap.get(newBatsmanPlayerId).getPlayerName());
                    batsmenStatsMap.put(newBatsmanPlayerId, batStatNew);
                }
                BattingStatistics bsNew = batsmenStatsMap.get(newBatsmanPlayerId);
                if(outTypeStr.equals("RunOut")){
                    //TODO: Get the batsman id who was got out
                    numOfWicketByBowler = 0;
                    String batsmanIdOfOut = data.getStringExtra(PLAYER_ID_BATSMAN_OUT);
                    Log.d("Batsman Out: " + LogType.TEST, " Batsman was run out: " + playerHashMap.get(atoi(batsmanIdOfOut)).getPlayerName());
                    isStrikerGotOut = (strikerPlayerId == atoi(batsmanIdOfOut))? true : false;
                }
                if(isStrikerGotOut){
                    // Striker was out in the previous ball
                    int outBatsmanPlayerId = activeBatsmen[strikerIndex].getBatsmanPlayerId();
                    BattingStatistics bs = batsmenStatsMap.get(outBatsmanPlayerId);
                    bs.setOutByBowlerId(currentBowlerStat.getBowlerPlayerId());
                    bs.setHowOut(outTypeHashMap.get(outTypeStr));
                    batsmenStatsMap.put(outBatsmanPlayerId, bs);
                    boolean strikerDisplayedFirst = activeBatsmen[strikerIndex].isDisplayedFirst();
                    CurrentBatsmanInfo btInfoNew = new CurrentBatsmanInfo(newBatsmanPlayerId, true, strikerDisplayedFirst);
                    activeBatsmen[strikerIndex] = btInfoNew;
                    if(activeBatsmen[strikerIndex].isDisplayedFirst()){
                        tvNameBt1.setText(strBatsmanNamePartial);
                        tvRunBt1.setText(itoa(bsNew.getRunsScored()));
                        tvBallBt1.setText(itoa(bsNew.getBallsFaced()));
                        tvFourBt1.setText(itoa(bsNew.getNumOf4s()));
                        tvSixBt1.setText(itoa(bsNew.getNumOf6s()));
                    }
                    else{
                        tvNameBt2.setText(strBatsmanNamePartial);
                        tvRunBt2.setText(itoa(bsNew.getRunsScored()));
                        tvBallBt2.setText(itoa(bsNew.getBallsFaced()));
                        tvFourBt2.setText(itoa(bsNew.getNumOf4s()));
                        tvSixBt2.setText(itoa(bsNew.getNumOf6s()));
                    }
                }
                else{
                    // Non-striker got run-out in this case while taking run
                    int outBatsmanPlayerId = activeBatsmen[1 - strikerIndex].getBatsmanPlayerId();
                    BattingStatistics bs2 = batsmenStatsMap.get(outBatsmanPlayerId);
                    bs2.setOutByBowlerId(currentBowlerStat.getBowlerPlayerId());
                    bs2.setHowOut(outTypeHashMap.get(outTypeStr));
                    batsmenStatsMap.put(outBatsmanPlayerId, bs2);
                    boolean NonStrikerDisplayedFirst = activeBatsmen[1 - strikerIndex].isDisplayedFirst();
                    CurrentBatsmanInfo btInfoNew = new CurrentBatsmanInfo(newBatsmanPlayerId, true, NonStrikerDisplayedFirst);
                    activeBatsmen[1 - strikerIndex] = btInfoNew;
                    if(activeBatsmen[1 - strikerIndex].isDisplayedFirst()){
                        tvNameBt1.setText(strBatsmanNamePartial);
                        tvRunBt1.setText(itoa(bsNew.getRunsScored()));
                        tvBallBt1.setText(itoa(bsNew.getBallsFaced()));
                        tvFourBt1.setText(itoa(bsNew.getNumOf4s()));
                        tvSixBt1.setText(itoa(bsNew.getNumOf6s()));
                    }
                    else{
                        tvNameBt2.setText(strBatsmanNamePartial);
                        tvRunBt2.setText(itoa(bsNew.getRunsScored()));
                        tvBallBt2.setText(itoa(bsNew.getBallsFaced()));
                        tvFourBt2.setText(itoa(bsNew.getNumOf4s()));
                        tvSixBt2.setText(itoa(bsNew.getNumOf6s()));
                    }
                }
                updateBowlingStat(runConcededByBowler, numOfWicketByBowler, isWide, isNo, runConcededByTeam);
                Log.d("NewBatsman: " + LogType.TEST, " New Batsman name:" + playerHashMap.get(newBatsmanPlayerId).getPlayerName());
                if(isLastBall){
                    changeCurrentBowler();
                }
                else{
                    updateScreen();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "You need to select next batsman to continue", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == START_SECOND_INNINGS_REQ_CODE){
            // TODO: after returning from start second innings activity works
            String strPlayerIdBt1 = data.getStringExtra(PLAYER_ID_BATSMAN_1);
            String strPlayerIdBt2 = data.getStringExtra(PLAYER_ID_BATSMAN_2);
            String strPlayerIdBowler = data.getStringExtra(PLAYER_ID_BOWLER);
            if(strPlayerIdBt1 != null) {
                playerIdBt1 = atoi(strPlayerIdBt1);
                String strPlayerNameBt1 = playerHashMap.get(playerIdBt1).getPlayerName();
                String strBatsmanPartial1 = (strPlayerNameBt1.length() > NAME_LEN) ? strPlayerNameBt1.substring(0, NAME_LEN) : strPlayerNameBt1;
                // Add first batsman's initial statistics in the map
                Player thisBatsman = playerHashMap.get(playerIdBt1);
                batsmenList.add(thisBatsman);
                battingPosition = 1;
                battingOrder.put(battingPosition, thisBatsman);
                if(!batsmenStatsMap.containsKey(playerIdBt1)){
                    BattingStatistics batStat1 = new BattingStatistics(playerIdBt1, thisBatsman.getPlayerName());
                    batsmenStatsMap.put(playerIdBt1, batStat1);
                }
                CurrentBatsmanInfo btInfo1 = new CurrentBatsmanInfo(playerIdBt1, true, true);
                activeBatsmen[0] = btInfo1;
                // Set the first batsman info in UI with green color
                tvNameBt1.setText(strBatsmanPartial1);
                tvNameBt1.setTextColor(Color.BLACK);
                tvNameBt1.setTypeface(null, Typeface.BOLD);
                llBt1.setBackgroundColor(Color.parseColor("#00cccc"));
                BattingStatistics bs = batsmenStatsMap.get(playerIdBt1);
                tvRunBt1.setText(itoa(bs.getRunsScored()));
                tvBallBt1.setText(itoa(bs.getBallsFaced()));
                tvFourBt1.setText(itoa(bs.getNumOf4s()));
                tvSixBt1.setText(itoa(bs.getNumOf6s()));
            }
            else{
                Log.d("MatchDetail: " + LogType.ERROR, "First Batsman name is null");
            }
            if(strPlayerIdBt2 != null) {
                playerIdBt2 = atoi(strPlayerIdBt2);
                String strPlayerNameBt2 = playerHashMap.get(playerIdBt2).getPlayerName();
                String strBatsmanPartial2 = (strPlayerNameBt2.length() > NAME_LEN) ? strPlayerNameBt2.substring(0, NAME_LEN) : strPlayerNameBt2;
                // Add second batsman's initial statistics in the map
                Player secondBatsman = playerHashMap.get(playerIdBt2);
                batsmenList.add(secondBatsman);
                battingPosition = 2;
                battingOrder.put(battingPosition, secondBatsman);
                if(!batsmenStatsMap.containsKey(playerIdBt2)){
                    BattingStatistics batStat2 = new BattingStatistics(playerIdBt2, secondBatsman.getPlayerName());
                    batsmenStatsMap.put(playerIdBt2, batStat2);
                }
                CurrentBatsmanInfo btInfo2 = new CurrentBatsmanInfo(playerIdBt2, false, false);
                activeBatsmen[1] = btInfo2;
                // Set the second batsman info in UI
                tvNameBt2.setText(strBatsmanPartial2);
                tvNameBt2.setTextColor(Color.BLACK);
                tvNameBt2.setTypeface(null, Typeface.NORMAL);
                llBt2.setBackgroundColor(Color.parseColor("#e5e5e5"));
                BattingStatistics bs = batsmenStatsMap.get(playerIdBt2);
                tvRunBt2.setText(itoa(bs.getRunsScored()));
                tvBallBt2.setText(itoa(bs.getBallsFaced()));
                tvFourBt2.setText(itoa(bs.getNumOf4s()));
                tvSixBt2.setText(itoa(bs.getNumOf6s()));
            }
            else{
                Log.d("MatchDetail: " + LogType.ERROR, "Second Batsman name is null");
            }
            if(strPlayerIdBowler != null) {
                playerIdBowler = atoi(strPlayerIdBowler);
                String strPlayerNameBowler = playerHashMap.get(playerIdBowler).getPlayerName();
                String strBowlerPartial = (strPlayerNameBowler.length() > NAME_LEN) ? strPlayerNameBowler.substring(0, NAME_LEN) : strPlayerNameBowler;
                // Add the bowler's initial statistics in the map
                if(!bowlingStatsMap.containsKey(playerIdBowler)){
                    BowlingStatistics bowlerStat = new BowlingStatistics(playerIdBowler, playerHashMap.get(playerIdBowler).getPlayerName());
                    bowlingStatsMap.put(playerIdBowler, bowlerStat);
                }
                currentBowlerStat = bowlingStatsMap.get(playerIdBowler);
                // Set the bowler info in UI with green color
                tvNameBowler.setText(strBowlerPartial);
                tvNameBowler.setTextColor(Color.BLACK);
                tvNameBowler.setTypeface(null, Typeface.BOLD);
                llBowler.setBackgroundColor(Color.parseColor("#00cccc"));
                tvOverBowler.setText(Double.toString(currentBowlerStat.getOversBowled()));
                tvRunBowler.setText(itoa(currentBowlerStat.getRunsConceded()));
                tvWicketBowler.setText(itoa(currentBowlerStat.getNumberOfWickets()));
                tvWideBowler.setText(itoa(currentBowlerStat.getWidesCount()));
                tvNoBowler.setText(itoa(currentBowlerStat.getNoBallCount()));
            }
            else{
                Log.d("MatchDetail: " + LogType.ERROR, "Bowler name is null");
            }
        }
    }
    // Change after batsman got out
    private void changeBatsman(){
        sameActivity = false;
        battingPosition++;
        Log.d("NewBatsman: " + LogType.TEST, " Calling a new batsman activity");
        Intent newBatsmanIntent = new Intent(getApplicationContext(), NewBatsmanActivity.class);
        ArrayList<Player> battingListAvailable = new ArrayList<Player>();
        for(Player p: playersListTeam1){
            if((p.getPlayerId() != activeBatsmen[0].getBatsmanPlayerId()) && (p.getPlayerId() != activeBatsmen[1].getBatsmanPlayerId())){
                battingListAvailable.add(p);
            }
        }
        int strikerIndex = getStrikerBatsmanIndexInArray();
        int strikerPlayerId = activeBatsmen[strikerIndex].getBatsmanPlayerId();
        int nonStrikerPlayerId = activeBatsmen[1 - strikerIndex].getBatsmanPlayerId();
        newBatsmanIntent.putExtra(PLAYER_LIST_BATSMEN, battingListAvailable);
        newBatsmanIntent.putExtra(PLAYER_ID_BATSMAN_1, itoa(strikerPlayerId));
        newBatsmanIntent.putExtra(PLAYER_ID_BATSMAN_2, itoa(nonStrikerPlayerId));
        newBatsmanIntent.putExtra(PLAYER_LIST_FULL, playerHashMap);
        startActivityForResult(newBatsmanIntent, NEW_BATSMAN_ACTIVITY_REQ_CODE);
    }
    public void viewScorecard(View v){
        Intent intent = new Intent(this, ScorecardActivity.class);
        intent.putExtra(BATTING_STATISTICS, batsmenStatsMap);
        intent.putExtra(PLAYER_ID_BATSMAN_1, itoa(activeBatsmen[0].getBatsmanPlayerId()));
        intent.putExtra(PLAYER_ID_BATSMAN_2, itoa(activeBatsmen[1].getBatsmanPlayerId()));
        intent.putExtra(BOWLING_STATISTICS, bowlingStatsMap);
        intent.putExtra(PLAYER_LIST_FULL, playerHashMap);
        startActivity(intent);
    }
    public void hitOK(View v){  //write the code after each ball, update total r
        strDetail = tvRunDetail.getText().toString();
        runConcededByBowler = 0;
        runConcededByTeam = 0;
        sameActivity = true;
        isLastBall = false;
        isWide = false;
        isNo = false;
        if(strContent.length() > 0) {
            preStrContent = strContent;
            preStrDetail = strDetail;
            preRunA = runA;
            preRunR = runR;
            preOverA = overA;
            preOverR = overR;
            preWickA = wickA;
            // store the current info for undo later
            for(int i = 0; i < 2; i++){
                int batsmanId = activeBatsmen[i].getBatsmanPlayerId();
                preBatsmenStatsMap.put(batsmanId, batsmenStatsMap.get(batsmanId));
            }
            int bowlerId = currentBowlerStat.getBowlerPlayerId();
            preBowlingStatsMap.put(bowlerId, bowlingStatsMap.get(bowlerId));
            preActiveBatsmen[0] = activeBatsmen[0];
            preActiveBatsmen[1] = activeBatsmen[1];
            run = 0;
            // Since only batting team overs are calculated, we should get the over information from overA irrespective to batting first/second
            int fullOver10 = (int) (overA * 10);
            overOnly = fullOver10 / 10;
            ball = fullOver10 % 10;
            // Run scored: 0, 1, 2, 3, 4, 6 -> strike need to be changed for 1 & 3 runs
            if (strContent.length() == 1) {
                if (TextUtils.isDigitsOnly(strContent)) {
                    run = atoi(strContent);
                    // Update the batsmen runs
                    runA = updateRunsInBatting(runA, run);
                    if(run == 1 || run == 3){
                        swapBatsmen();
                    }
                    updateBowlingStat(run, 0, false, false, 0);
                    if (ball <= 4) {
                        ball++;
                        strDetail += "  ";
                        strDetail += strContent;
                    } else {
                        ball = 0;
                        overOnly++;
                        strDetail = "";
                        isLastBall = true;
                    }
                }
            } else {
                char ch = strContent.charAt(0);
                run = 0;
                switch (ch) {
                    case 'n':   // For no-ball calculation: nb+d
                        if (strContent.length() > 3) {
                            String strRunCon = strContent.substring(3);
                            if(TextUtils.isDigitsOnly(strRunCon)){
                                run = atoi(strRunCon);// batsman scored runs in no-ball
                                runA = updateRunsInBatting(runA, run);
                                if(run == 1 || run == 3){
                                    swapBatsmen();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else
                            strContent = strContent.substring(0, 2);
                        run++;
                        runA++;
                        updateBowlingStat(run, 0, false, true, 0);
                        strDetail += "  ";
                        strDetail += strContent;
                        break;
                    case 'w':
                        if (strContent.length() > 3){
                            String strRunCon = strContent.substring(3);
                            if(TextUtils.isDigitsOnly(strRunCon)){
                                run = atoi(strRunCon);//batsmen ran but these are wide runs, strike might change here
                                if(run == 1 || run == 3){
                                    swapBatsmen();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else
                            strContent = strContent.substring(0, 2);
                        run++;
                        runA+=run;
                        updateBowlingStat(run, 0, true, false, 0);
                        strDetail += "  ";
                        strDetail += strContent;
                        break;
                    case 'b':
                        if (strContent.length() > 1){
                            String strRunCon = strContent.substring(1);
                            if(TextUtils.isDigitsOnly(strRunCon)){
                                run = atoi(strRunCon); // batsmen ran but these are bye runs, strike might change here
                                runA = updateRunsInBatting(runA, 0);   //This run is not for batsman, this function is called to add dot ball for the batsman
                                if(run == 1 || run == 3){
                                    swapBatsmen();
                                }
                                updateBowlingStat(0, 0, false, false, run);
                                runA+=run;
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
                            isLastBall = true;
                        } else {
                            ball++;
                            strDetail += "  ";
                            strDetail += strContent;
                        }
                        break;
                    case 'W':
                        wickA++;
                        if (strContent.length() > 2) {
                            switch (strContent.charAt(2)) {
                                case 'n':
                                    isNo = true;
                                    if (strContent.length() > 5){
                                        String strRunCon = strContent.substring(5); // batsmen can only be out by runout in no-ball
                                        if(TextUtils.isDigitsOnly(strRunCon)){
                                            run = atoi(strRunCon);
                                            runA = updateRunsInBatting(runA, run);
                                            if(run == 1 || run == 3){
                                                swapBatsmen();
                                            }
                                            swapMayNeed = true;
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    else
                                        strContent = strContent.substring(0, 4);
                                    run++;
                                    runA++;
                                    runConcededByBowler = run;
                                    runConcededByTeam = 0;
                                    strDetail += "  ";
                                    strDetail += strContent;
                                    //TODO: Add feature for getting new batsman
                                    sameActivity = false;
                                    changeBatsman();
                                    break;
                                case 'w':
                                    isWide = true;
                                    if (strContent.length() > 5){
                                        String strRunCon = strContent.substring(5); // batsmen can only be run-out in wide ball
                                        if(TextUtils.isDigitsOnly(strRunCon)){
                                            run = atoi(strRunCon);
                                            if(run == 1 || run == 3){
                                                swapBatsmen();
                                            }
                                            swapMayNeed = true;
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Extra combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                    else
                                        strContent = strContent.substring(0, 4);
                                    run++;
                                    runA+=run;
                                    runConcededByBowler = run;
                                    runConcededByTeam = 0;
                                    strDetail += "  ";
                                    strDetail += strContent;
                                    sameActivity = false;
                                    //TODO: Add feature for getting new batsman
                                    changeBatsman();
                                    break;
                                case 'b':
                                    if (strContent.length() > 3){
                                        String strRunCon = strContent.substring(3); // batsmen can only be run-out while taking run in bye
                                        if(TextUtils.isDigitsOnly(strRunCon)){
                                            runA = updateRunsInBatting(runA, 0);   //This run is not for batsman, this function is called to add dot ball for the batsman
                                            run = atoi(strRunCon);
                                            if(run == 1 || run == 3){
                                                swapBatsmen();
                                            }
                                            //TODO: Add feature for getting new batsman
                                            runConcededByBowler = 0;
                                            runConcededByTeam = run;
                                            swapMayNeed = true;
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
                                        isLastBall = true;
                                    } else {
                                        ball++;
                                        strDetail += "  ";
                                        strDetail += strContent;
                                    }
                                    sameActivity = false;
                                    changeBatsman();
                                    break;
                                default:
                                    //run = atoi(strContent.substring(2));
                                    String strRunCon = strContent.substring(2);
                                    if(TextUtils.isDigitsOnly(strRunCon)){
                                        run = atoi(strRunCon); //  batsmen can only be run-out while taking run in wicket ball
                                        runA = updateRunsInBatting(runA, run);
                                        if(run == 1 || run == 3){
                                            swapBatsmen();
                                        }
                                        //TODO: Add feature for getting new batsman
                                        runConcededByBowler = run;
                                        runConcededByTeam = 0;
                                        swapMayNeed = true;
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Wicket and run combination is not allowed. Press Clear to update it.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (ball == 5) {
                                        overOnly++;
                                        ball = 0;
                                        strDetail = "";
                                        isLastBall = true;
                                    } else {
                                        ball++;
                                        strDetail += "  ";
                                        strDetail += strContent;
                                    }
                                    sameActivity = false;
                                    changeBatsman();
                                    break;
                            }
                        } else {//Only wicket fallen in this ball
                            if (ball == 5) {
                                overOnly++;
                                ball = 0;
                                strDetail = "";
                                isLastBall = true;
                            } else {
                                ball++;
                                strDetail += "  ";
                                strContent = strContent.substring(0, 1); // Only Wicket fallen
                                strDetail += strContent;
                            }
                            runConcededByBowler = 0;
                            runConcededByTeam = 0;
                            //TODO: Add feature for getting new batsman for only wicket fallen
                            sameActivity = false;
                            changeBatsman();
                        }
                        break;
                }
            }
            if(sameActivity) {
                if (isLastBall) {
                    Toast.makeText(getApplicationContext(), "Over Completed!", Toast.LENGTH_SHORT).show();
                    sameActivity = false;
                    changeCurrentBowler();
                }
                else{
                    updateScreen();
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter 0 for dot ball", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateScreen(){
        strOver = overOnly + "." + ball;
        overA = overOnly + (ball / 10.0);
        tvRunA.setText(itoa(runA));
        tvWickA.setText(itoa(wickA));
        tvOverA.setText(strOver);
        runR = runA + 1; // runA needs to be updated above
        tvRunR.setText(itoa(runR));

        // Update the personal batting scores based on FirstUILabelPositionBatsmanIndex
        int index = getFirstUILabelBatsmanIndexInArray();
        int playerIdFirst = activeBatsmen[index].getBatsmanPlayerId();
        BattingStatistics bs1 = batsmenStatsMap.get(playerIdFirst);
        tvRunBt1.setText(itoa(bs1.getRunsScored()));
        tvBallBt1.setText(itoa(bs1.getBallsFaced()));
        tvFourBt1.setText(itoa(bs1.getNumOf4s()));
        tvSixBt1.setText(itoa(bs1.getNumOf6s()));

        int playerIdSecond = activeBatsmen[1 - index].getBatsmanPlayerId();
        BattingStatistics bs2 = batsmenStatsMap.get(playerIdSecond);
        tvRunBt2.setText(itoa(bs2.getRunsScored()));
        tvBallBt2.setText(itoa(bs2.getBallsFaced()));
        tvFourBt2.setText(itoa(bs2.getNumOf4s()));
        tvSixBt2.setText(itoa(bs2.getNumOf6s()));

        tvOverBowler.setText(Double.toString(currentBowlerStat.getOversBowled()));
        tvRunBowler.setText(itoa(currentBowlerStat.getRunsConceded()));
        tvWicketBowler.setText(itoa(currentBowlerStat.getNumberOfWickets()));
        tvWideBowler.setText(itoa(currentBowlerStat.getWidesCount()));
        tvNoBowler.setText(itoa(currentBowlerStat.getNoBallCount()));

        tvRunDetail.setText(strDetail);
        btnContent.setText("");
        if(swapMayNeed){
            Toast.makeText(getApplicationContext(), "Click Swap Batsman if requires due to run-out", Toast.LENGTH_LONG).show();
            swapMayNeed = false;
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
        tvRunA.setText(itoa(runA));
        tvWickA.setText(itoa(wickA));
        tvOverA.setText(Double.toString(overA));
        tvRunR.setText(itoa(runR));
        tvOverR.setText(Double.toString(overR));
        tvRunDetail.setText(strDetail);

        //update personal information
        for(int i = 0; i < 2; i++){
            int batsmanId = activeBatsmen[i].getBatsmanPlayerId();
            batsmenStatsMap.put(batsmanId, preBatsmenStatsMap.get(batsmanId));
        }
        int bowlerId = currentBowlerStat.getBowlerPlayerId();
        bowlingStatsMap.put(bowlerId, preBowlingStatsMap.get(bowlerId));
        activeBatsmen[0] = preActiveBatsmen[0];
        activeBatsmen[1] = preActiveBatsmen[1];

        int index = getFirstUILabelBatsmanIndexInArray();
        int playerIdFirst = activeBatsmen[index].getBatsmanPlayerId();
        BattingStatistics bs1 = batsmenStatsMap.get(playerIdFirst);
        tvRunBt1.setText(itoa(bs1.getRunsScored()));
        tvBallBt1.setText(itoa(bs1.getBallsFaced()));
        tvFourBt1.setText(itoa(bs1.getNumOf4s()));
        tvSixBt1.setText(itoa(bs1.getNumOf6s()));

        int playerIdSecond = activeBatsmen[1 - index].getBatsmanPlayerId();
        BattingStatistics bs2 = batsmenStatsMap.get(playerIdSecond);
        tvRunBt2.setText(itoa(bs2.getRunsScored()));
        tvBallBt2.setText(itoa(bs2.getBallsFaced()));
        tvFourBt2.setText(itoa(bs2.getNumOf4s()));
        tvSixBt2.setText(itoa(bs2.getNumOf6s()));

        tvOverBowler.setText(Double.toString(currentBowlerStat.getOversBowled()));
        tvRunBowler.setText(itoa(currentBowlerStat.getRunsConceded()));
        tvWicketBowler.setText(itoa(currentBowlerStat.getNumberOfWickets()));
        tvWideBowler.setText(itoa(currentBowlerStat.getWidesCount()));
        tvNoBowler.setText(itoa(currentBowlerStat.getNoBallCount()));
    }

    public void inningsComplete(View v){
        getConfirmation();
    }

    private void updateUIForSecondInnings(){
        if(totOver < overA) {
            overR = overA;
            totOver = overA;
            tvOverR.setText(Double.toString(overR));
        }
        if(firstBatting)
            inningsCompleteChange();
        firstBatting = false;
    }
    private void inningsCompleteChange(){
        // TODO: Save the current batting and bowling information and get the new batsmen, bowler name to start new innings
        // save first innings data here
        teamOne.setFirstBatting(true);
        teamOne.setTotalRuns(runA);
        teamOne.setTotalWickets(wickA);
        teamOne.setOversPlayed(overA);

        // TODO: save team information in match scorecard
        matchScorecard.setTeam1(teamOne);
        matchScorecard.setBatstatsTeam1(batsmenStatsMap);
        matchScorecard.setPlayersOfTeam1(batsmenList);
        matchScorecard.setBattingOrderTeam1(battingOrder);
        matchScorecard.setBowlstatsTeam2(bowlingStatsMap);

        // Check everything is saved correctly in matchScorecard
        for(int pos: matchScorecard.getBattingOrderTeam1().keySet()){
            Player p = matchScorecard.getBattingOrderTeam1().get(pos);
            BattingStatistics bs = matchScorecard.getBatstatsTeam1().get(p.getPlayerId());
            Player bowler = playerHashMap.get(bs.getOutByBowlerId());
            if(bowler != null) {
                Log.d("ScoreBoard: " + LogType.TEST, itoa(pos) + "-> " + bs.getBatsmanName() + ", " + bs.getRunsScored() + ", "
                        + bs.getBallsFaced() + ", " + bowler.getPlayerId() + ", " + bowler.getPlayerName());
            }
            else{
                Log.d("ScoreBoard: " + LogType.TEST, itoa(pos) + "-> " + bs.getBatsmanName() + ", " + bs.getRunsScored() + ", "
                        + bs.getBallsFaced() + " Not out");
            }
        }

        tvTeam1.setTypeface(null, Typeface.NORMAL);
        tvTeam1.setTextColor(Color.parseColor("#ff000000"));
        tvStatus.setText(strTeam2 + " is Batting");
        String strTeamPartial1 = (strTeam2.length() > NAME_LEN) ? strTeam2.substring(0, NAME_LEN) : strTeam2;
        tvTeam1.setText(strTeamPartial1);
        tvRunA.setText("0");
        tvWickA.setText("0");
        tvOverA.setText("0.0");
        runA = 0;
        overA = 0;
        wickA = 0;
        ballsA = 0;
        battingPosition = 0;

        preStrDetail = strDetail;
        strDetail = "";
        tvRunDetail.setText(strDetail);
        startSecondInnings();
    }

    private void startSecondInnings(){

        // Get the new batsmen, bowler list by calling start second innings activity
        ArrayList<Player> tempPlayerList = playersListTeam1;
        playersListTeam1 = playersListTeam2;
        playersListTeam2 = tempPlayerList;
        Log.d("Start2ndInning: " + LogType.TEST, "Calling start 2nd innings activity");
        Intent changeInningsIntent = new Intent(getApplicationContext(), StartSecondInnings.class);
        changeInningsIntent.putExtra(PLAYER_LIST_FIRST, playersListTeam1);
        changeInningsIntent.putExtra(PLAYER_LIST_SECOND, playersListTeam2);
        changeInningsIntent.putExtra(PLAYER_LIST_FULL, playerHashMap);
        batsmenList.clear();
        battingOrder.clear();
        batsmenStatsMap.clear();
        bowlingStatsMap.clear();
        preBatsmenStatsMap.clear();
        preBowlingStatsMap.clear();
        batsmenList = new ArrayList<>();
        battingOrder = new HashMap<>();
        batsmenStatsMap = new HashMap<>();
        bowlingStatsMap = new HashMap<>();
        preBatsmenStatsMap = new HashMap<>();
        preBowlingStatsMap = new HashMap<>();
        startActivityForResult(changeInningsIntent, START_SECOND_INNINGS_REQ_CODE);
    }

    public void getConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning...");
        //builder.setMessage("Are you sure? All score will be lost!");
        builder.setMessage("First innings completed?");
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                updateUIForSecondInnings();
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

    //    public void matchSave(View v){
//        Toast.makeText(getApplicationContext(), "Match Can not be saved now", Toast.LENGTH_SHORT).show();
//    }
//    public void newMatch(View v){
//        confirmation();
//    }

    @Override
    public void onBackPressed() {
        return;
    }
}
