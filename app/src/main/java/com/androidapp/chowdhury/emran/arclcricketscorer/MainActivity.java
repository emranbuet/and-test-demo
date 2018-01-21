package com.androidapp.chowdhury.emran.arclcricketscorer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.androidapp.chowdhury.emran.arclcricketscorer.ScoringUtility.*;

public class MainActivity extends AppCompatActivity {

    // View input data
    private EditText etTeam1, etTeam2, etTotOver, etTarget;
    private String strTeam1, strTeam2, strTotOver, strTarget;
    private int totalOvers = 0, targetRuns = 0;

    protected ArrayList<Season> seasonList;
    ArrayAdapter<String> seasonAdapter;
    private Spinner spinnerSeason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTeam1 = (EditText) findViewById(R.id.etTeamName);
        etTeam2 = (EditText) findViewById(R.id.etTeamName2);
        etTotOver = (EditText) findViewById(R.id.etTotalOvers);
        etTarget = (EditText) findViewById(R.id.etTargetRun);

        spinnerSeason = (Spinner) findViewById(R.id.spSeason);
        if (apiURL != null) {
            new CallAPI().execute("Calling getSeasonIds");
        }
    }

    public void startNewMatch(View view) {
        IS_FIRST_INNINGS = true;
        Intent intent = new Intent(this, ScoreActivity.class);

        strTeam1 = etTeam1.getText().toString();
        strTeam2 = etTeam2.getText().toString();
        strTotOver = etTotOver.getText().toString();
        totalOvers = (TextUtils.isEmpty(strTotOver)) ? 16 : atoi(strTotOver);

        SEASON_ID = seasonList.get(spinnerSeason.getSelectedItemPosition()).getId();

        if (TextUtils.isEmpty(strTeam1))
            Toast.makeText(getApplicationContext(), "Enter Name of First team", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(strTeam2))
            Toast.makeText(getApplicationContext(), "Enter Name of Second team", Toast.LENGTH_SHORT).show();
        else {
            if (strTeam1.length() >= MAX_TEAM_NAME_LENGTH) {
                strTeam1 = strTeam1.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_1, strTeam1);

            if (strTeam2.length() >= MAX_TEAM_NAME_LENGTH) {
                strTeam2 = strTeam2.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_2, strTeam2);

            intent.putExtra(TOTAL_OVER, totalOvers);

            startActivity(intent);
        }

    }

//    public void startFirstInningsOnly(View v){
//        IS_FIRST_INNINGS = true;
//    }

    public void startSecondInningsOnly(View view) {
        IS_FIRST_INNINGS = false;

        Intent intent = new Intent(this, ScoreActivity.class);

        strTeam1 = etTeam1.getText().toString();
        strTeam2 = etTeam2.getText().toString();
        strTotOver = etTotOver.getText().toString();
        totalOvers = (TextUtils.isEmpty(strTotOver)) ? 16 : atoi(strTotOver);
        strTarget = etTarget.getText().toString();
        targetRuns = (TextUtils.isEmpty(strTarget)) ? 0 : atoi(strTarget);

        SEASON_ID = seasonList.get(spinnerSeason.getSelectedItemPosition()).getId();

        if (TextUtils.isEmpty(strTeam1))
            Toast.makeText(getApplicationContext(), "Enter Name of First team", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(strTeam2))
            Toast.makeText(getApplicationContext(), "Enter Name of Second team", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(strTarget))
            Toast.makeText(getApplicationContext(), "Enter Target runs for 2nd innings", Toast.LENGTH_SHORT).show();
        else {
            if (strTeam1.length() >= MAX_TEAM_NAME_LENGTH) {
                strTeam1 = strTeam1.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_1, strTeam1);

            if (strTeam2.length() >= MAX_TEAM_NAME_LENGTH) {
                strTeam2 = strTeam2.substring(0, MAX_TEAM_NAME_LENGTH);
            }
            intent.putExtra(TEAM_NAME_2, strTeam2);

            intent.putExtra(TOTAL_OVER, totalOvers);

            intent.putExtra(TARGET_RUNS, targetRuns);

            startActivity(intent);
        }
    }

    private class CallAPI extends AsyncTask<String, String, String> {

        private StringBuilder sb = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                getSeasonsFromArcl();

                return "Success";
            } catch (IOException e) {
                return "Connection Error";
            } catch (Exception e) {
                return e.toString();
            }
        }

        protected void onPostExecute(String result) {
            if (result == null) {
                result = "THERE WAS AN ERROR";
            }
            final ArrayList<String> seasonNames = new ArrayList<>();
            if (seasonList == null || seasonList.size() == 0) {
                Log.d("Error", "Season Id is empty");
            } else {
                int seasonCounter = 0; // will be used to display to last 3 seasons only
                for (Season s : seasonList) {
                    seasonCounter++;
                    seasonNames.add(s.getName());
                    if(seasonCounter == 3)
                        break;
                }
            }
            seasonAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, seasonNames) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = convertView;
                    if (v == null) {
                        Context mContext = this.getContext();
                        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = vi.inflate(R.layout.row, null);
                    }

                    TextView tv = (TextView) v.findViewById(R.id.spinnerTarget);
                    tv.setText(seasonNames.get(position));
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(11);
                    return v;
                }
            };
            seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if (seasonAdapter != null) {
                spinnerSeason.setAdapter(seasonAdapter);
            }

        }

        private String getSeasonsFromArcl() throws XmlPullParserException, IOException {
            InputStream stream = null;
            String urlForSeasons = apiURL + "seasons";
            try {
                stream = downloadUrl(urlForSeasons);
                JsonReader jsonReader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
                seasonList = new ArrayList<Season>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    seasonList.add(readSeasonData(jsonReader));
                }
                jsonReader.endArray();

            } catch (Exception e) {
                Log.d("getSeasonsFromArcl", e.toString());
            } finally {
                if (stream != null)
                    stream.close();
            }
            return "done";
        }


        private Season readSeasonData(JsonReader reader) throws IOException {
            String sName = "";
            int sId = -1;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("Name")) {
                    sName = reader.nextString();
                } else if (name.equals("Id")) {
                    sId = reader.nextInt();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Season(sName, sId);
        }

        private InputStream downloadUrl(String urlString) throws IOException {
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
