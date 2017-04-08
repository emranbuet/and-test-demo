package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class ScheduleActivity extends ListActivity {

    // data source
    String[] osList = {"Ubuntu", "Linux", "Android", "Mac", "Windows"};

    // adapter object
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osList);
        setListAdapter(adapter);
        //setContentView(R.layout.activity_schedule);
    }

    public void startLogIn(View v){
        Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);

    }
}
