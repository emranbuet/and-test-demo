package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MatchDetail extends AppCompatActivity {

    public static String PLAYER_LIST_FIRST = "PlayerListOne";
    public static String PLAYER_LIST_SECOND = "PlayerListTwo";

    public static String TEAM_NAME_1 = "TEAM_NAME_1";
    public static String TEAM_NAME_2 = "TEAM_NAME_2";
    public static String TOTAL_OVER = "TOTAL_OVER";
    public static String PLAYER_LIST_STATUS = "PLAYER_STATUS";

    private ArrayList<Player> playersListTeam1, playersListTeam2;
    private String playerListAll = null;
    private String strTeam1, strTeam2, strTotOver;
    private int totOver;

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
        }
        if(strTotOver != null)
            totOver = (int)Double.parseDouble(strTotOver);
        StringBuilder playerStringDump = new StringBuilder();
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

        playerStringDump = new StringBuilder();
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
}
