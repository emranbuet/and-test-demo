package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

public class CustomizedBowlingAdapter extends ArrayAdapter<BowlingStatistics>{

    Activity bowlerContext;
    ArrayList<BowlingStatistics> bowlingStats;

    public CustomizedBowlingAdapter(Context context, ArrayList<BowlingStatistics> bowlStats){
        super(context, R.layout.bowler_item_score, bowlStats);
        this.bowlerContext = (Activity) context;
        this.bowlingStats = bowlStats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = null;
        if(convertView == null){
            LayoutInflater inflater=bowlerContext.getLayoutInflater();
            v = inflater.inflate(R.layout.bowler_item_score, null);

            TextView txtBowlerName = (TextView) v.findViewById(R.id.stVNameBowler);
            TextView txtBowlerOver = (TextView) v.findViewById(R.id.stVOverBowler);
            TextView txtBowlerRun = (TextView) v.findViewById(R.id.stVRunBowler);
            TextView txtBowlerWicket = (TextView) v.findViewById(R.id.stVWicketBowler);
            TextView txtBowlerWide = (TextView) v.findViewById(R.id.stVWideBowler);
            TextView txtBowlerNo = (TextView) v.findViewById(R.id.stVNoBowler);

            BowlingStatistics bls = bowlingStats.get(position);
            String bowlerFullName = bls.getBowlerName();
            txtBowlerName.setText(bowlerFullName.length() > NAME_LEN ? bowlerFullName.substring(0, NAME_LEN) : bowlerFullName);
            txtBowlerOver.setText(dtoa(bls.getOversBowled()));
            txtBowlerRun.setText(formatInt2D(bls.getRunsConceded()));
            txtBowlerWicket.setText(formatInt2D(bls.getNumberOfWickets()));
            txtBowlerWide.setText(formatInt2D(bls.getWidesCount()));
            txtBowlerNo.setText(formatInt2D(bls.getNoBallCount()));
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
