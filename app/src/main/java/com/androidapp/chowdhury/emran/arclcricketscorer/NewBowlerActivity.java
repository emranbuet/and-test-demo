package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

public class NewBowlerActivity extends AppCompatActivity {

    private Spinner spNewBowler;
    private Button btnNewBowler;
    private TextView tvTeamName, tvCurrentRun, tvCurrentWicket, tvCurrentOver;
    private int playerIdNewBowler;
    private String teamNameCurrent, runCurrent, wickCurrent;;
    private double overCurrent;
    private ArrayAdapter<String> bowlerAdapter;
    private ArrayList<Player> playerListFielding = null;
    private HashMap<Integer, Player> playerHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bowler);

        spNewBowler = (Spinner) findViewById(R.id.spNewBowler);
        btnNewBowler = (Button) findViewById(R.id.btnNewBowler);
        tvTeamName = (TextView) findViewById(R.id.tVNameTeamInBowler);
        tvCurrentRun = (TextView) findViewById(R.id.tVRunAInBowler);
        tvCurrentWicket = (TextView) findViewById(R.id.tVWicketAInBowler);
        tvCurrentOver = (TextView) findViewById(R.id.tVOverAInBowler);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            playerListFielding = (ArrayList<Player>) bundle.getSerializable(PLAYER_LIST_BOWLERS);
            playerHashMap = (HashMap<Integer, Player>) bundle.getSerializable(PLAYER_LIST_FULL);
            teamNameCurrent = bundle.getString(TEAM_NAME_1);
            runCurrent = bundle.getString(CURRENT_TOTAL_RUN);
            wickCurrent = bundle.getString(CURRENT_TOTAL_WICKET);
            overCurrent = Double.parseDouble(bundle.getString(CURRENT_TOTAL_OVER));
        }
        else{
            Log.d("NewBowler: " + LogType.ERROR, "Bundle is null from match detail activity");
        }

        tvTeamName.setText(teamNameCurrent);
        tvCurrentRun.setText(runCurrent);
        tvCurrentWicket.setText(wickCurrent);
        tvCurrentOver.setText(dtoa(overCurrent));

        final ArrayList<String> playerNamesBowlers = new ArrayList<>();
        for(Player p : playerListFielding){
            playerNamesBowlers.add(p.getPlayerName());
        }
        bowlerAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, playerNamesBowlers){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }

                TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
                tv.setText(playerNamesBowlers.get(position));
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(11);
                return v;
            }
        };
        bowlerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(bowlerAdapter != null) {
            spNewBowler.setAdapter(bowlerAdapter);
        }

        btnNewBowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnBowlerIdIntent = new Intent();
                Player bowler = playerListFielding.get(spNewBowler.getSelectedItemPosition());
                playerIdNewBowler = bowler.getPlayerId();
                returnBowlerIdIntent.putExtra(NEW_BOWLER_ID, itoa(playerIdNewBowler));
                setResult(Activity.RESULT_OK, returnBowlerIdIntent);
                Log.d("NewBowler: " + LogType.TEST, bowler.getPlayerName() +" Bowler is selected as bowler whose id is: " + playerIdNewBowler);
                finish();
            }
        });
    }
}
