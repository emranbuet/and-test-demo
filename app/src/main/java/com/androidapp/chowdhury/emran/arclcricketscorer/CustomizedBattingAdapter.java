package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomizedBattingAdapter extends ArrayAdapter<BattingStatistics>{
    private static int BATSMAN_NAME_LEN = 12;

    Activity currentContext;
    ArrayList<BattingStatistics> battingStats;
    ArrayList<Integer> activeBatsmenPlayerIds;

    public CustomizedBattingAdapter(Context context, ArrayList<BattingStatistics> batStats, ArrayList<Integer> activeBatsmenPlayerId){
        super(context, R.layout.batsman_item_score, batStats);
        this.currentContext = (Activity) context;
        this.battingStats = batStats;
        this.activeBatsmenPlayerIds = activeBatsmenPlayerId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = null;
        if(convertView == null){
            LayoutInflater inflater=currentContext.getLayoutInflater();
            v = inflater.inflate(R.layout.batsman_item_score, null);

            TextView txtBatsmanName = (TextView) v.findViewById(R.id.stVNameBt);
            TextView txtBatsmanRun = (TextView) v.findViewById(R.id.stVRunBt);
            TextView txtBatsmanBall = (TextView) v.findViewById(R.id.stVBallBt);
            TextView txtBatsmanFour = (TextView) v.findViewById(R.id.stVFourBt);
            TextView txtBatsmanSix = (TextView) v.findViewById(R.id.stVSixBt);

            BattingStatistics bs = battingStats.get(position);
            String batsmanFullName = bs.getBatsmanName();
            String batsmanPartialName = batsmanFullName.length() > BATSMAN_NAME_LEN ? batsmanFullName.substring(0, BATSMAN_NAME_LEN) : batsmanFullName;
            if(activeBatsmenPlayerIds.contains(bs.getBatsmanPlayerId()))
                batsmanPartialName = batsmanPartialName + "*";
            txtBatsmanName.setText(batsmanPartialName);
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
        String strValue = String.valueOf(value);
        return (strValue.length() > 1) ? strValue: ("0" + strValue);
    }
}
