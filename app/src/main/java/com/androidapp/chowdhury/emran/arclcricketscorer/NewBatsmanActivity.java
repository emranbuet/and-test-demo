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

import java.util.ArrayList;

public class NewBatsmanActivity extends AppCompatActivity {

    private static String PLAYER_LIST_BATSMEN = "PlayerListAvailableBatsmen";

    private Spinner spNewBatsman;
    private Button btnNewBatsman;
    private int playerIdNewBatsman;
    private ArrayAdapter<String> batsmanAdapter;
    private ArrayList<Player> playerListBatting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batsman);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            playerListBatting = (ArrayList<Player>) bundle.getSerializable(PLAYER_LIST_BATSMEN);
        }
        else{
            Log.d("NewBatsman: " + LogType.ERROR, "Bundle is null from match detail activity");
        }
        spNewBatsman = (Spinner) findViewById(R.id.spNewBatsman);
        btnNewBatsman = (Button) findViewById(R.id.btnNewBatsman);

        final ArrayList<String> playerNamesBatsmen = new ArrayList<>();
        for(Player p : playerListBatting){
            playerNamesBatsmen.add(p.getPlayerName());
        }
        batsmanAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, playerNamesBatsmen){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }

                TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
                tv.setText(playerNamesBatsmen.get(position));
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(11);
                return v;
            }
        };
        batsmanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(batsmanAdapter != null) {
            spNewBatsman.setAdapter(batsmanAdapter);
        }

        btnNewBatsman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnBatsmanIntent = new Intent();
                Player batsman = playerListBatting.get(spNewBatsman.getSelectedItemPosition());
                playerIdNewBatsman = batsman.getPlayerId();
                returnBatsmanIntent.putExtra("newBatsmanId", String.valueOf(playerIdNewBatsman));
                returnBatsmanIntent.putExtra("wasRunOut", "false");
                setResult(Activity.RESULT_OK, returnBatsmanIntent);
                Log.d("NewBatsman: " + LogType.TEST, batsman.getPlayerName() +"Batsman is selected as new batsman whose id is: " + playerIdNewBatsman);
                finish();
            }
        });
    }
}
