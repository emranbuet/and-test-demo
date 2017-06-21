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
    public static int NAME_LEN = 12;

    Activity currentContext;
    ArrayList<BattingStatistics> battingStats;

    public CustomizedBattingAdapter(Context context, ArrayList<BattingStatistics> batStats){
        super(context, R.layout.batsman_item_score, batStats);
        this.currentContext = (Activity) context;
        this.battingStats = batStats;
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
            txtBatsmanName.setText(batsmanFullName.length() > NAME_LEN ? batsmanFullName.substring(0, NAME_LEN) : batsmanFullName);
            txtBatsmanRun.setText(String.valueOf(bs.getRunsScored()));
            txtBatsmanBall.setText(String.valueOf(bs.getBallsFaced()));
            txtBatsmanFour.setText(String.valueOf(bs.getNumOf4s()));
            txtBatsmanSix.setText(String.valueOf(bs.getNumOf6s()));
        }
        else{
            v = convertView;
        }
        return v;
    }
}
