package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.itoa;

public class CustomizedBattingAdapter extends ArrayAdapter<BattingStatistics>{
    private static int BATSMAN_NAME_LEN = 12;
    private static int BOWLER_NAME_LEN = 6;
    private static int FIELDER_NAME_LEN = 6;

    Activity currentContext;
    ArrayList<BattingStatistics> battingStats;
    ArrayList<Integer> activeBatsmenPlayerIds;
    HashMap<OutType, String> shortOutTypeStr;
    HashMap<Integer, Player> playerHashMap;

    public CustomizedBattingAdapter(Context context, ArrayList<BattingStatistics> batStats, ArrayList<Integer> activeBatsmenPlayerId, HashMap<Integer, Player> playerHashMap){
        super(context, R.layout.batsman_item_score, batStats);
        this.currentContext = (Activity) context;
        this.battingStats = batStats;
        this.activeBatsmenPlayerIds = activeBatsmenPlayerId;
        this.playerHashMap = playerHashMap;
        this.shortOutTypeStr = new HashMap<>();
        shortOutTypeStr.put(OutType.Bowled, "BL");
        shortOutTypeStr.put(OutType.Caught, "CT");
        shortOutTypeStr.put(OutType.RunOut, "RO");
        shortOutTypeStr.put(OutType.Stumped, "ST");
        shortOutTypeStr.put(OutType.Other, "OR");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = null;
        if(convertView == null){
            LayoutInflater inflater=currentContext.getLayoutInflater();
            v = inflater.inflate(R.layout.batsman_item_score, null);

            TextView txtBatsmanName = (TextView) v.findViewById(R.id.stVNameBt);
            TextView txtOutType = (TextView) v.findViewById(R.id.stVOutTypeBt);
            TextView txtFielderName = (TextView) v.findViewById(R.id.stVByFielderBt);
            TextView txtBowlerName = (TextView) v.findViewById(R.id.stVByBowlerBt);
            TextView txtBatsmanRun = (TextView) v.findViewById(R.id.stVRunBt);
            TextView txtBatsmanBall = (TextView) v.findViewById(R.id.stVBallBt);
            TextView txtBatsmanFour = (TextView) v.findViewById(R.id.stVFourBt);
            TextView txtBatsmanSix = (TextView) v.findViewById(R.id.stVSixBt);

            BattingStatistics bs = battingStats.get(position);
            String batsmanFullName = bs.getBatsmanName();
            String batsmanPartialName = batsmanFullName.length() > BATSMAN_NAME_LEN ? batsmanFullName.substring(0, BATSMAN_NAME_LEN) : batsmanFullName;
            String bowlerFullName = "";
            String bowlerPartialName = "   ---   ";
            String fielderFullName = "";
            String fielderPartialName = "   ---   ";
            if(activeBatsmenPlayerIds.contains(bs.getBatsmanPlayerId())) {
                batsmanPartialName = batsmanPartialName + "*";
            }
            else{
                int outByBowlerId = bs.getOutByBowlerId();
                Player bowler = playerHashMap.get(outByBowlerId);
                bowlerFullName = (bowler == null) ? "Tempo" : bowler.getPlayerName();
                bowlerPartialName = bowlerFullName.length() > BOWLER_NAME_LEN ? bowlerFullName.substring(0, BOWLER_NAME_LEN) : bowlerFullName;

                int outByFielderId = bs.getCaughtByPlayerId();
                Player fielder = playerHashMap.get(outByFielderId);
                fielderFullName = (fielder == null) ? "  N/A  " : fielder.getPlayerName();
                fielderPartialName = fielderFullName.length() > FIELDER_NAME_LEN ? fielderFullName.substring(0, FIELDER_NAME_LEN) : fielderFullName;
            }
            txtBatsmanName.setText(batsmanPartialName);
            String strOutType = shortOutTypeStr.get(bs.getHowOut()) == null ? " - " : shortOutTypeStr.get(bs.getHowOut());
            txtOutType.setText(strOutType);
            txtFielderName.setText(fielderPartialName);
            txtBowlerName.setText(bowlerPartialName);
            txtBatsmanRun.setText(formatInt2D(bs.getRunsScored()));
            txtBatsmanBall.setText(formatInt2D(bs.getBallsFaced()));
            txtBatsmanFour.setText(formatInt2D(bs.getNumOf4s()));
            txtBatsmanSix.setText(formatInt2D(bs.getNumOf6s()));
        }
        else{
            v = convertView;
        }
        return v;
    }

    private String formatInt2D(int value){
        String strValue = itoa(value);
        return (strValue.length() > 1) ? strValue: ("0" + strValue);
    }
}
