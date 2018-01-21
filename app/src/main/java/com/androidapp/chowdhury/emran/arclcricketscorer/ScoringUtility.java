package com.androidapp.chowdhury.emran.arclcricketscorer;

public class ScoringUtility {
    public static int MATCH_ID = 999;
    public static int TEAM_ID_1 = 111;
    public static int TEAM_ID_2 = 555;
    public static int SEASON_ID = -1;

    public static int SUBSTITUTE_PLAYER_ID = 101;
    public static String SUBSTITUTE_PLAYER_NAME = "Substitute";
    public static int NO_FIELDER_PLAYER_ID = 202;
    public static String NO_FIELDER_PLAYER_NAME = "N/A";

    public static boolean IS_FIRST_INNINGS = true;

    public static String PLAYER_LIST_FULL = "PLAYER_LIST_FULL";
    public static String PLAYER_LIST_FIRST = "PlayerListOne";
    public static String PLAYER_LIST_SECOND = "PlayerListTwo";
    public static String PLAYER_LIST_BOWLERS = "PlayerListAvailableBowlers";
    public static String PLAYER_LIST_FIELDERS = "PlayerListAvailableFielders";
    public static String PLAYER_LIST_BATSMEN = "PlayerListAvailableBatsmen";

    public static String TEAM_NAME_1 = "TEAM_NAME_1";
    public static String TEAM_NAME_2 = "TEAM_NAME_2";
    public static String TOTAL_OVER = "TOTAL_OVER";
    public static String TARGET_RUNS = "TARGET_RUNS";

    public static String PLAYER_ID_BATSMAN_1 = "Batsman1";
    public static String PLAYER_ID_BATSMAN_2 = "Batsman2";
    public static String PLAYER_ID_BOWLER = "Bowler";
    public static String PLAYER_ID_FIELDER = "Fielder";

    public static String CURRENT_TOTAL_RUN = "CurrentTotalRun";
    public static String CURRENT_TOTAL_WICKET = "CurrentTotalWicket";
    public static String CURRENT_TOTAL_OVER = "CurrentTotalOver";

    public static String BATTING_STATISTICS = "BATTING_STATISTICS";
    public static String BOWLING_STATISTICS = "BOWLING_STATISTICS";
    public static String OUT_TYPE_STR = "OutTypeStr";
    public static String NEW_BATSMAN_ID = "NewBatsmanId";
    public static String NEW_BOWLER_ID = "newBowlerId";
    public static String PLAYER_ID_BATSMAN_OUT = "BatsmanRunOutId";

    public static int MAX_TEAM_NAME_LENGTH = 20;
    public static int NAME_LEN = 12;

    public static int NEW_BOWLER_ACTIVITY_REQ_CODE = 1;
    public static int NEW_BATSMAN_ACTIVITY_REQ_CODE = 2;
    public static int START_SECOND_INNINGS_REQ_CODE = 3;

    // url string for calling arcl web api
    public static String apiURL = "http://arclweb.azurewebsites.net/api/";

    public static int atoi(String s) {
        return Integer.parseInt(s);
    }

    public static String itoa(int val) {
        return Integer.toString(val);
    }

    public static String dtoa(Double val) {
        return String.valueOf(val);
    }
}
