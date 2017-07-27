package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

public class ScorecardActivity extends AppCompatActivity {


    ListView lvBattingStatistics;
    ListView lvBowlingStatistics;
    CustomizedBattingAdapter battingAdapter;
    CustomizedBowlingAdapter bowlingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        HashMap<Integer, BattingStatistics> batsmenStatsMap;
        HashMap<Integer, BowlingStatistics> bowlingStatsMap;
        String firstBatsmanPlayerId, secondBatsmanPlayerId;
        HashMap<Integer, Player> playerHashMap;

        lvBattingStatistics = (ListView) findViewById(R.id.lvBatsman);
        lvBowlingStatistics = (ListView) findViewById(R.id.lvBowler);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            batsmenStatsMap = (HashMap<Integer, BattingStatistics>) bundle.getSerializable(BATTING_STATISTICS);
            bowlingStatsMap = (HashMap<Integer, BowlingStatistics>) bundle.getSerializable(BOWLING_STATISTICS);
            firstBatsmanPlayerId = bundle.getString(PLAYER_ID_BATSMAN_1);
            secondBatsmanPlayerId = bundle.getString(PLAYER_ID_BATSMAN_2);
            playerHashMap = (HashMap<Integer, Player>) bundle.getSerializable(PLAYER_LIST_FULL);

            ArrayList<Integer> activeBatsmenIds = new ArrayList<>();
            activeBatsmenIds.add(atoi(firstBatsmanPlayerId));
            activeBatsmenIds.add(atoi(secondBatsmanPlayerId));
            ArrayList<BattingStatistics> battingStatisticsList = new ArrayList<BattingStatistics> (batsmenStatsMap.values());
            if(battingStatisticsList != null && battingStatisticsList.size() > 0){
                battingAdapter = new CustomizedBattingAdapter(this, battingStatisticsList, activeBatsmenIds, playerHashMap);
                lvBattingStatistics.setAdapter(battingAdapter);
            }

            ArrayList<BowlingStatistics> bowlingStatisticsList = new ArrayList<BowlingStatistics> (bowlingStatsMap.values());
            if(bowlingStatisticsList != null && bowlingStatisticsList.size() > 0){
                bowlingAdapter = new CustomizedBowlingAdapter(this, bowlingStatisticsList);
                lvBowlingStatistics.setAdapter(bowlingAdapter);
            }
        }
        else{
            Log.d("Scorecard: " + LogType.ERROR, "Bundle is null from match detail activity");
        }

    }
}
