package com.androidapp.chowdhury.emran.arclcricketscorer;

public class ScoringUtility {
    public static String PLAYER_LIST_FULL = "PLAYER_LIST_FULL";
    public static String PLAYER_LIST_FIRST = "PlayerListOne";
    public static String PLAYER_LIST_SECOND = "PlayerListTwo";
    public static String PLAYER_LIST_BOWLERS = "PlayerListAvailableBowlers";
    public static String PLAYER_LIST_BATSMEN = "PlayerListAvailableBatsmen";

    public static String TEAM_NAME_1 = "TEAM_NAME_1";
    public static String TEAM_NAME_2 = "TEAM_NAME_2";
    public static String TOTAL_OVER = "TOTAL_OVER";

    public static String PLAYER_ID_BATSMAN_1 = "Batsman1";
    public static String PLAYER_ID_BATSMAN_2 = "Batsman2";
    public static String PLAYER_ID_BOWLER = "Bowler";

    public static String BATTING_STATISTICS = "BATTING_STATISTICS";
    public static String BOWLING_STATISTICS = "BOWLING_STATISTICS";
    public static String OUT_TYPE_STR = "OutTypeStr";
    public static String NEW_BATSMAN_ID = "NewBatsmanId";
    public static String PLAYER_ID_BATSMAN_OUT = "BatsmanRunOutId";

    public static int MAX_TEAM_NAME_LENGTH = 20;
    public static int NAME_LEN = 12;

    public static int NEW_BOWLER_ACTIVITY_REQ_CODE = 1;
    public static int NEW_BATSMAN_ACTIVITY_REQ_CODE = 2;

    // url string for calling arcl web api
    public static String apiURL = "http://arclweb.azurewebsites.net/api/";
}
