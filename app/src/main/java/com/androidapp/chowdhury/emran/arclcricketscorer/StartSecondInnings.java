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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

public class StartSecondInnings extends AppCompatActivity {

    private Spinner spinner2Bt1, spinner2Bt2, spinner2Bl;
    private Button btnStartSecondInnings;

    private ArrayList<Player> playerListBatting = null;
    private ArrayList<Player> playerListFielding = null;
    private HashMap<Integer, Player> playerHashMap;
    private static int playerIdBt1, playerIdBt2, playerIdBl;
    ArrayAdapter<String> adapterBatting, adapterFielding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_second_innings);

        spinner2Bt1 = (Spinner) findViewById(R.id.sp2Bt1);
        spinner2Bt2 = (Spinner) findViewById(R.id.sp2Bt2);
        spinner2Bl = (Spinner) findViewById(R.id.sp2Bl);
        btnStartSecondInnings = (Button) findViewById(R.id.btnStart2ndInnings);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            playerListBatting = (ArrayList<Player>) bundle.getSerializable(PLAYER_LIST_FIRST);
            playerListFielding = (ArrayList<Player>) bundle.getSerializable(PLAYER_LIST_SECOND);
            playerHashMap = (HashMap<Integer, Player>) bundle.getSerializable(PLAYER_LIST_FULL);
        } else {
            Log.d("Start2ndInning:" + LogType.ERROR, "Bundle is null from match detail activity");
        }

        final ArrayList<String> playerNames1 = new ArrayList<>();
        if (playerListBatting == null || playerListBatting.size() == 0) {
            Log.d("Error", "Player list of Batting team is empty");
        } else {
            for (Player p : playerListBatting) {
                playerNames1.add(p.getPlayerName());
            }
        }
        adapterBatting = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, playerNames1) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }

                TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
                tv.setText(playerNames1.get(position));
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(11);
                return v;
            }
        };
        adapterBatting.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (adapterBatting != null) {
            spinner2Bt1.setAdapter(adapterBatting);
            spinner2Bt2.setAdapter(adapterBatting);
        }

        final ArrayList<String> playerNames2 = new ArrayList<>();
        if (playerListFielding == null || playerListFielding.size() == 0) {
            Log.d("Error", "Player list of Fielding team is empty");
        } else {
            for (Player p : playerListFielding) {
                playerNames2.add(p.getPlayerName());
            }
        }
        adapterFielding = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, playerNames2) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }

                TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
                tv.setText(playerNames2.get(position));
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(11);
                return v;
            }
        };
        adapterFielding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (adapterFielding != null) {
            spinner2Bl.setAdapter(adapterFielding);
        }


        btnStartSecondInnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner2Bt1.getSelectedItemPosition() == spinner2Bt2.getSelectedItemPosition()) {
                    Toast.makeText(getApplicationContext(), "Select different opening batsmen to start", Toast.LENGTH_SHORT).show();
                } else {
                    Intent return2ndInningsIntent = new Intent();
                    playerIdBt1 = playerListBatting.get(spinner2Bt1.getSelectedItemPosition()).getPlayerId();
                    playerIdBt2 = playerListBatting.get(spinner2Bt2.getSelectedItemPosition()).getPlayerId();
                    playerIdBl = playerListFielding.get(spinner2Bl.getSelectedItemPosition()).getPlayerId();

                    return2ndInningsIntent.putExtra(PLAYER_ID_BATSMAN_1, itoa(playerIdBt1));
                    return2ndInningsIntent.putExtra(PLAYER_ID_BATSMAN_2, itoa(playerIdBt2));
                    return2ndInningsIntent.putExtra(PLAYER_ID_BOWLER, itoa(playerIdBl));

                    setResult(Activity.RESULT_OK, return2ndInningsIntent);
                    Log.d("Start2ndInnings:" + LogType.TEST, "Bt1, Bt2 & Bowler Ids: " + itoa(playerIdBt1) + ", " + itoa(playerIdBt2) + ", " + itoa(playerIdBl));
                    finish();
                }
            }
        });
    }
}
