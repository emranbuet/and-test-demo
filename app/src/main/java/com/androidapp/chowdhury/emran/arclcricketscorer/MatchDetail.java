package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
    private String strTeam1, strTeam2, strTotOver;
    private int playerIdBt1, playerIdBt2, playerIdBl;
    //private int totOver;

    // Related to scoring
    private int runA, runB, runR, preRunA, preRunB, preRunR;
    private double overA, overB, overR, totOver, preOverA, preOverB, preOverR;
    private int ballsA, ballsB, preBallsA, preBallsB;
    private int wickA, wickB, preWickA, preWickB;
    private boolean firstBatting = true;
    //private Button btnContent;
    private TextView tvTeam1, tvTeam2, tvRunA, tvRunB, tvWickA, tvWickB, tvOverA, tvOverB, tvRunR, tvOverR, tvRunDetail, tvStatus;
    private TextView tvNameBt1,  tvRunBt1, tvBallBt1, tvFourBt1, tvSixBt1;
    private TextView tvNameBt2, tvRunBt2, tvBallBt2, tvFourBt2, tvSixBt2;
    private TextView tvNameBl, tvOverBl, tvRunBl, tvWicketBl, tvWideBl, tvNoBl;
    private TextView tvTextBy, tvTextIn, tvTextOver, tvTextBy2, tvTextIn2, tvTextOver2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
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
            playerIdBl = Integer.parseInt(bundle.getString(PLAYER_ID_BOWLER));
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
        Log.d("MatchDetail: " + LogType.TEST, "Id " + playerIdBl + " -> " + playerHashMap.get(playerIdBl).getPlayerName());

        //Toast.makeText(getApplicationContext(), "Player Map: " + playerHashMap.get(99) , Toast.LENGTH_SHORT).show();

        // Get the initial text view from UI
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvRunR = (TextView)findViewById(R.id.tVRunR);
        tvOverR = (TextView)findViewById(R.id.tVOverR);
        tvTeam1 = (TextView)findViewById(R.id.tVNameTeam);
        tvNameBt1 = (TextView)findViewById(R.id.tVNameBt1);
        tvNameBt2 = (TextView)findViewById(R.id.tVNameBt2);
        tvNameBl = (TextView)findViewById(R.id.tVNameBl);

        // Set the initial team and batsman, bowler name in UI
        String strPlayerNameBt1, strPlayerNameBt2, strPlayerNameBowler;
        strPlayerNameBt1 = playerHashMap.get(playerIdBt1).getPlayerName();
        strPlayerNameBt2 = playerHashMap.get(playerIdBt2).getPlayerName();
        strPlayerNameBowler = playerHashMap.get(playerIdBl).getPlayerName();

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
            tvNameBt1.setText(strBatsmanPartial1);
            tvNameBt1.setTextColor(Color.GREEN);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "First Batsman name is null");
        }
        if(strPlayerNameBt2 != null) {
            strBatsmanPartial2 = (strPlayerNameBt2.length() > NAME_LEN) ? strPlayerNameBt2.substring(0, NAME_LEN) : strPlayerNameBt2;
            tvNameBt2.setText(strBatsmanPartial2);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Second Batsman name is null");
        }
        if(strPlayerNameBowler != null) {
            strBowlerPartial = (strPlayerNameBowler.length() > NAME_LEN) ? strPlayerNameBowler.substring(0, NAME_LEN) : strPlayerNameBowler;
            tvNameBl.setText(strBowlerPartial);
            tvNameBl.setTextColor(Color.GREEN);
        }
        else{
            Log.d("MatchDetail: " + LogType.ERROR, "Bowler name is null");
        }

        // Get all the UI items view to start the match
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
        tvOverBl = (TextView)findViewById(R.id.tVOverBl);
        tvRunBl = (TextView)findViewById(R.id.tVRunBl);
        tvWicketBl = (TextView)findViewById(R.id.tVWicketBl);
        tvWideBl = (TextView)findViewById(R.id.tVWideBl);
        tvNoBl = (TextView)findViewById(R.id.tVNoBl);

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

    @Override
    public void onBackPressed() {
        return;
    }
}
