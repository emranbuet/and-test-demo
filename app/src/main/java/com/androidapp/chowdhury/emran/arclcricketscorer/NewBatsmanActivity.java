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
import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NewBatsmanActivity extends AppCompatActivity {

    private Spinner spOutType;
    private Spinner spCurrentBatsman;
    private Spinner spNewBatsman;
    private Button btnNewBatsman;
    private int playerIdNewBatsman;
    private ArrayAdapter<String> outTypeAdapter;
    private ArrayAdapter<String> currentBatsmanAdapter;
    private ArrayAdapter<String> batsmanAdapter;
    private ArrayList outTypeList = new ArrayList(Arrays.asList(new String[]{"Bowled", "Caught", "RunOut"}));
    private ArrayList<Player> currentBatsmanList = null;
    private ArrayList<String> currentBatsmenName = null;
    private ArrayList<Player> playerListBatting = null;
    private HashMap<Integer, Player> playerHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_batsman);

        String firstBatsmanPlayerId = null, secondBatsmanPlayerId = null;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            playerListBatting = (ArrayList<Player>) bundle.getSerializable(PLAYER_LIST_BATSMEN);
            firstBatsmanPlayerId = bundle.getString(PLAYER_ID_BATSMAN_1);
            secondBatsmanPlayerId = bundle.getString(PLAYER_ID_BATSMAN_2);
            playerHashMap = (HashMap<Integer, Player>) bundle.getSerializable(PLAYER_LIST_FULL);
        }
        else{
            Log.d("NewBatsman: " + LogType.ERROR, "Bundle is null from match detail activity");
        }
        spOutType = (Spinner) findViewById(R.id.spOutType);
        spCurrentBatsman = (Spinner) findViewById(R.id.spSelectOutBatsman);

        outTypeAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, outTypeList);
        outTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOutType.setAdapter(outTypeAdapter);

        currentBatsmanList = new ArrayList<>();
        currentBatsmanList.add(playerHashMap.get(Integer.parseInt(firstBatsmanPlayerId)));
        currentBatsmanList.add(playerHashMap.get(Integer.parseInt(secondBatsmanPlayerId)));

        currentBatsmenName = new ArrayList<>();
        for(Player p : currentBatsmanList){
            String strBatsmanFullName = p.getPlayerName();
            String strBatsmanNamePartial = (strBatsmanFullName.length() > NAME_LEN) ? strBatsmanFullName.substring(0, NAME_LEN) : strBatsmanFullName;
            currentBatsmenName.add(strBatsmanNamePartial);
        }

        spNewBatsman = (Spinner) findViewById(R.id.spNewBatsman);
        btnNewBatsman = (Button) findViewById(R.id.btnNewBatsman);

        final ArrayList<String> playerNamesBatsmen = new ArrayList<>();
        for(Player p : playerListBatting){
            String strBatsmanFullName = p.getPlayerName();
            //String strBatsmanNamePartial = (strBatsmanFullName.length() > NAME_LEN) ? strBatsmanFullName.substring(0, NAME_LEN) : strBatsmanFullName;
            playerNamesBatsmen.add(strBatsmanFullName);
        }

        currentBatsmanAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, currentBatsmenName);
        currentBatsmanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrentBatsman.setAdapter(currentBatsmanAdapter);

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
                returnBatsmanIntent.putExtra(NEW_BATSMAN_ID, String.valueOf(playerIdNewBatsman));

                String outTypeStr = (String)outTypeList.get(spOutType.getSelectedItemPosition());
                returnBatsmanIntent.putExtra(OUT_TYPE_STR, outTypeStr);
                String playerIdOfRunOut = String.valueOf(currentBatsmanList.get(spCurrentBatsman.getSelectedItemPosition()).getPlayerId());
                returnBatsmanIntent.putExtra(PLAYER_ID_BATSMAN_OUT, playerIdOfRunOut);

                setResult(Activity.RESULT_OK, returnBatsmanIntent);
                Log.d("NewBatsman: " + LogType.TEST, batsman.getPlayerName() +" is selected as new batsman whose id is: " + playerIdNewBatsman);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
