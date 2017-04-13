package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.xml.transform.Result;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ScoreActivity extends AppCompatActivity {


    public static String TEAM_NAME_1 = "TEAM_NAME_1";
    public static String TEAM_NAME_2 = "TEAM_NAME_2";
    public static String TOTAL_OVER = "TOTAL_OVER";
    public static String PLAYER_LIST_STATUS = "PLAYER_STATUS";

    private String strTeam1, strTeam2, strContent, strDetail, preStrDetail, preStrContent;
    //private int runA, runB, runR, preRunA, preRunB, preRunR;
    private double overA, overB, overR, totOver, preOverA, preOverB, preOverR;

    private Spinner spinnerBt1, spinnerBt2, spinnerBl;
    /*private int ballsA, ballsB, preBallsA, preBallsB;
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

    // url string for calling arcl web api
    public static String apiURL = "http://arclweb.azurewebsites.net/api/";
    public static String PLAYER_LIST_FIRST = "PlayerListOne";
    public static String PLAYER_LIST_SECOND = "PlayerListTwo";
    protected ArrayList<Player> playersListTeam1, playersListTeam2;
    ArrayAdapter<String> adapter, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        spinnerBt1 = (Spinner) findViewById(R.id.spBt1);
        spinnerBt2 = (Spinner) findViewById(R.id.spBt2);
        spinnerBl = (Spinner) findViewById(R.id.spBl);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            strTeam1 = extras.getString(TEAM_NAME_1);
            strTeam2 = extras.getString(TEAM_NAME_2);
            totOver = extras.getInt(TOTAL_OVER);
        }
        else{
            strTeam1 = "No name found for 1";
            strTeam2 = "No name found for 2";
        }
        //Toast.makeText(getApplicationContext(), "Total overs: " + String.valueOf(totOver), Toast.LENGTH_SHORT).show();
        StringBuilder sba = new StringBuilder();
        sba.append(strTeam1);
        sba.append(" and ");
        sba.append(strTeam2);

        sba.append(" are playing ");
        sba.append(String.valueOf(totOver));
        sba.append(" overs match");

        TextView myTv = (TextView) findViewById(R.id.tvHello);

        myTv.append(sba.toString());

        //adapter = new ArrayAdapter<Player>(this, android.R.layout.simple_spinner_item, playersListTeam1);
        //adapter2 = new ArrayAdapter<Player>(this, android.R.layout.simple_spinner_item, playersListTeam2);

        if(apiURL != null){
            new CallAPI().execute(strTeam1, strTeam2);
        }
/*
        ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this, android.R.layout.simple_spinner_item, playersListTeam1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBt1.setAdapter(adapter);
        spinnerBt2.setAdapter(adapter);

        ArrayAdapter<Player> adapter2 = new ArrayAdapter<Player>(this, android.R.layout.simple_spinner_item, playersListTeam2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBl.setAdapter(adapter);*/
    }

    public void beginScoring(View view){

        Intent intent = new Intent(getApplicationContext(), MatchDetail.class);
        //intent.putExtra(PLAYER_LIST_STATUS, result);
        intent.putExtra(TEAM_NAME_1, strTeam1);
        intent.putExtra(TEAM_NAME_2, strTeam2);
        intent.putExtra(TOTAL_OVER, String.valueOf(totOver));

        Player p1= null, p2 = null;
        if(playersListTeam1 == null || playersListTeam1.isEmpty()){
            if(playersListTeam1 == null)
                playersListTeam1 = new ArrayList<>();
            p1 = new Player("TestPlayer1", -1);
            playersListTeam1.add(p1);
        }
        intent.putExtra(PLAYER_LIST_FIRST, playersListTeam1);

        if(playersListTeam2 == null || playersListTeam2.isEmpty()) {
            if(playersListTeam2 == null)
                playersListTeam2 = new ArrayList<>();
            p2 = new Player("TestPlayer2", -2);
            playersListTeam2.add(p2);
        }
        intent.putExtra(PLAYER_LIST_SECOND, playersListTeam2);
        //Log.d("post execute of second", p2.getPlayerName());
        startActivity(intent);

    }
    private class CallAPI extends AsyncTask<String, String, String> {

        private StringBuilder sb =  null;
        @Override
        protected String doInBackground(String... params) {
            try{
                getPlayersFromArcl(params[0], true);
                getPlayersFromArcl(params[1], false);

                return "Success";
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
            ArrayList<String> playerNames1 = new ArrayList<>();
            for(Player p : playersListTeam1){
                playerNames1.add(p.getPlayerName());
            }
            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, playerNames1);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(adapter != null) {
                spinnerBt1.setAdapter(adapter);
                spinnerBt2.setAdapter(adapter);
            }

            ArrayList<String> playerNames2 = new ArrayList<>();
            for(Player p : playersListTeam2){
                playerNames2.add(p.getPlayerName());
            }
            adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, playerNames2);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(adapter2 != null) {
                spinnerBl.setAdapter(adapter2);
            }
        }

        private String getPlayersFromArcl(String teamName, boolean isFirstTeam ) throws XmlPullParserException, IOException {
            InputStream stream = null;
            String urlForPlayers = apiURL + "RegisteredPlayers?teamName=" + teamName;
            if(sb == null)
                sb = new StringBuilder();
            sb.append(teamName);
            sb.append(" Player list: ");
            try{
                stream = downloadUrl(urlForPlayers);
                JsonReader jsonReader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
                if(isFirstTeam)
                    playersListTeam1 = new ArrayList<>();
                else
                    playersListTeam2 = new ArrayList<>();
                jsonReader.beginArray();
                while(jsonReader.hasNext()){
                    if(isFirstTeam)
                        playersListTeam1.add(readPlayerData(jsonReader));
                    else
                        playersListTeam2.add(readPlayerData(jsonReader));
                }
                jsonReader.endArray();

                /*byte[] buffer = new byte[1024];
                while(stream.read(buffer) > 0){
                    sb.append(new String(buffer));
                }*/
            }
            catch (Exception e){
                Log.d("getPlayersFromArcl", e.toString());
            }
            finally {
                if(stream != null)
                    stream.close();
            }
            return sb.toString();
        }

        private Player readPlayerData(JsonReader reader) throws IOException{
            String pname = "";
            int pid = -1;
            reader.beginObject();
            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("PlayerName")){
                    pname = reader.nextString();
                }
                else if(name.equals("PlayerId")){
                    pid = reader.nextInt();
                }
                else{
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Player(pname, pid);
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
