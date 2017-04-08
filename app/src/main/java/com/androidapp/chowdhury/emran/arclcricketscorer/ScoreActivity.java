package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.xml.transform.Result;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ScoreActivity extends ListActivity {

    private String strTeam1, strTeam2, strContent, strDetail, preStrDetail, preStrContent;
    /*private int runA, runB, runR, preRunA, preRunB, preRunR;
    private double overA, overB, overR, totOver, preOverA, preOverB, preOverR;
    private int ballsA, ballsB, preBallsA, preBallsB;
    private int wickA, wickB, preWickA, preWickB;
    private boolean firstBatting = true;
    private Button btnContent;
    private TextView tvTeam1, tvTeam2, tvRunA, tvRunB, tvWickA, tvWickB, tvOverA, tvOverB, tvRunR, tvOverR, tvRunD, tvStatus;
    private TextView tvTextBy, tvTextIn, tvTextOver, tvTextBy2, tvTextIn2, tvTextOver2;
    private LinearLayout llFirst, llSecond, llRequired;*/

    // data source
    //String[] playerList = {"PlayerA", "PlayerB", "PlayerC", "PlayerD", "PlayerE"};
    //ArrayList<String> playerArrayList = new ArrayList<>(Arrays.asList(playerList));
    //List<String> playerArrayList;

    // url strings
    String apiURL = "http://arclweb.azurewebsites.net/api/";

    // adapter object
    //ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_score);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            strTeam1 = extras.getString("strTeamName1");
            strTeam2 = extras.getString("strTeamName2");
            //totOver = extras.getInt("totalOvers");
        }


       /* playerArrayList.add(strTeam1);
        playerArrayList.add(strTeam2);*/

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerArrayList);
        /*adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerArrayList);
        setListAdapter(adapter);*/

        //String urlString = apiURL + "RegisteredPlayers?teamName=" + strTeam1;
        String urlString ="http://arclweb.azurewebsites.net/api/RegisteredPlayers?teamName=toofun";
        if(urlString != null){
            new CallAPI().execute(urlString);
        }

    }

    /*public void startNewMatch(View v){
        //Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);

    }*/

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // This is the method that is called when the submit button is clicked
    public void verifyDetails(View view) {
        //EditText schidEditText = (EditText) findViewById(R.id.sch_id);
        //String schid = schidEditText.getText().toString();
        String schid = "";

        if( schid != null && !schid.isEmpty()) {
            String urlString = apiURL + "schid:" + schid.toString();
            //String urlString = apiURL + "LicenseInfo.RegisteredUser.UserID=" + strikeIronUserName + "&LicenseInfo.RegisteredUser.Password=" + strikeIronPassword + "&VerifyEmail.Email=" + email + "&VerifyEmail.Timeout=30";
            new CallAPI().execute(urlString);
        }
    }*/

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try{
                return getPlayersFromArcl(params[0]);
            }
            catch (IOException e){
                return "Connection Error";
            }
            catch (Exception e){
                return e.toString();
            }
        }

        protected void onPostExecute(String result) {
            if(result == null) {
                result = "THERE WAS AN ERROR";
            }
            Toast.makeText(getApplicationContext(), "First Team is: " + strTeam1, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Second Team is: " + strTeam2, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MatchDetail.class);
            intent.putExtra("PlayerList1", result);
            startActivity(intent);
        }

        private String getPlayersFromArcl(String urlString ) throws XmlPullParserException, IOException {
            InputStream stream = null;
            StringBuilder sb = new StringBuilder();
            sb.append("Player list: ");
            try{
                stream = downloadUrl(urlString);
                byte[] buffer = new byte[1024];
                while(stream.read(buffer) > 0){
                    sb.append(new String(buffer));
                }
            }
            finally {
                if(stream != null)
                    stream.close();
            }
            return sb.toString();
        }
        private InputStream downloadUrl(String urlString) throws IOException{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            return conn.getInputStream();
        }
    }
}
