package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;


public class ScoreActivity extends AppCompatActivity {

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

    protected ArrayList<Player> playersListTeam1, playersListTeam2;
    protected HashMap<Integer, Player> playersMap;
    ArrayAdapter<String> adapter, adapter2;
    public int playerIdBt1, playerIdBt2, playerIdBl;

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
        //Toast.makeText(getApplicationContext(), "Total overs: " + itoa(totOver), Toast.LENGTH_SHORT).show();
        StringBuilder sba = new StringBuilder();
        sba.append(strTeam1);
        sba.append(" and ");
        sba.append(strTeam2);

        sba.append(" are playing ");
        sba.append(dtoa(totOver));
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

        if(spinnerBt1.getSelectedItemPosition() == spinnerBt2.getSelectedItemPosition()){
            Toast.makeText(getApplicationContext(), "Select different opening batsmen to start", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), MatchDetail.class);
            //intent.putExtra(PLAYER_LIST_STATUS, result);
            if (strTeam1 != null && Character.isLowerCase(strTeam1.charAt(0))) {
                String firstChar = strTeam1.substring(0, 1);
                String lastPart = strTeam1.substring(1);
                strTeam1 = firstChar.toUpperCase() + lastPart;
            }
            if (strTeam2 != null && Character.isLowerCase(strTeam2.charAt(0))) {
                String firstChar = strTeam2.substring(0, 1);
                String lastPart = strTeam2.substring(1);
                strTeam2 = firstChar.toUpperCase() + lastPart;
            }
            intent.putExtra(TEAM_NAME_1, strTeam1);
            intent.putExtra(TEAM_NAME_2, strTeam2);
            intent.putExtra(TOTAL_OVER, dtoa(totOver));

            Player p1 = null, p2 = null, player = null;
            playersMap = new HashMap<>();

            if (playersListTeam1 == null || playersListTeam1.isEmpty()) {
                Log.d("Error", "Player list of first team is empty");
            } else {
                for (Player p : playersListTeam1) {
                    playersMap.put(p.getPlayerId(), p);
                }
            }
            if (playersListTeam2 == null || playersListTeam2.isEmpty()) {
                Log.d("Error", "Player list of second team is empty");
            } else {
                for (Player p : playersListTeam2) {
                    playersMap.put(p.getPlayerId(), p);
                }
            }

            intent.putExtra(PLAYER_LIST_FIRST, playersListTeam1);
            intent.putExtra(PLAYER_LIST_SECOND, playersListTeam2);
            intent.putExtra(PLAYER_LIST_FULL, playersMap);

            playerIdBt1 = playersListTeam1.get(spinnerBt1.getSelectedItemPosition()).getPlayerId();
            playerIdBt2 = playersListTeam1.get(spinnerBt2.getSelectedItemPosition()).getPlayerId();
            playerIdBl = playersListTeam2.get(spinnerBl.getSelectedItemPosition()).getPlayerId();

            Log.d("Batsman 1", playersListTeam1.get(spinnerBt1.getSelectedItemPosition()).getPlayerName() + " -> " + itoa(playerIdBl));
            Log.d("Batsman 2", playersListTeam1.get(spinnerBt2.getSelectedItemPosition()).getPlayerName() + " -> " + itoa(playerIdBt2));
            Log.d("Bowler", playersListTeam2.get(spinnerBl.getSelectedItemPosition()).getPlayerName() + " -> " + itoa(playerIdBl));


            intent.putExtra(PLAYER_ID_BATSMAN_1, itoa(playerIdBt1));
            intent.putExtra(PLAYER_ID_BATSMAN_2, itoa(playerIdBt2));
            intent.putExtra(PLAYER_ID_BOWLER, itoa(playerIdBl));

            startActivity(intent);
        }

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
            final ArrayList<String> playerNames1 = new ArrayList<>();
            if(playersListTeam1 == null || playersListTeam1.size() == 0){
                Log.d("Error", "Player list of first team is empty");
            }
            else {
                for (Player p : playersListTeam1) {
                    playerNames1.add(p.getPlayerName());
                }
            }
            adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, playerNames1){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent){
                    View v = convertView;
                    if (v == null) {
                        Context mContext = this.getContext();
                        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.row, null);
                    }

                    TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
                    tv.setText(playerNames1.get(position));
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(11);
                    return v;
                }
            };
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(adapter != null) {
                spinnerBt1.setAdapter(adapter);
                spinnerBt2.setAdapter(adapter);
            }

            final ArrayList<String> playerNames2 = new ArrayList<>();
            if(playersListTeam2 == null || playersListTeam2.size() == 0){
                Log.d("Error", "Player list of second team is empty");
            }
            else {
                for (Player p : playersListTeam2) {
                    playerNames2.add(p.getPlayerName());
                }
            }
            adapter2 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, playerNames2){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent){
                    View v = convertView;
                    if (v == null) {
                        Context mContext = this.getContext();
                        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.row, null);
                    }

                    TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
                    tv.setText(playerNames2.get(position));
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(11);
                    return v;
                }
            };
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if(adapter2 != null) {
                spinnerBl.setAdapter(adapter2);
            }
        }

        private String getPlayersFromArcl(String teamName, boolean isFirstTeam ) throws XmlPullParserException, IOException {
            InputStream stream = null;
            StringTokenizer st = new StringTokenizer(teamName);
            StringBuilder sb = new StringBuilder();
            String token = st.nextToken();
            sb.append(token);
            while(st.hasMoreTokens()){
                sb.append("%20");
                token = st.nextToken();
                sb.append(token);
            }
            String formattedTeamName = sb.toString();
            //String urlForPlayers = apiURL + "RegisteredPlayers?teamName=" + teamName+"&seasonid=41";
            String urlForPlayers = apiURL + "RegisteredPlayers?teamName=" + formattedTeamName+"&seasonid=42";
            Log.d(LogType.INFO, "Formatted team name is: " + formattedTeamName);
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
