package com.androidapp.chowdhury.emran.arclcricketscorer;

public class Team {
    private String teamName;
    private int teamId;
    private int leagueId;
    private boolean firstBatting;
    private int totalRuns;
    private int totalWickets;
    private double oversPlayed;

    public Team(String teamName, int teamId) {
        this.teamName = teamName;
        this.teamId = teamId;
        firstBatting = false;
        totalRuns = 0;
        totalWickets = 0;
        oversPlayed = 0.0;
    }

    public Team(String teamName, int teamId, int leagueId) {
        this.teamName = teamName;
        this.teamId = teamId;
        this.leagueId = leagueId;
        firstBatting = false;
        totalRuns = 0;
        totalWickets = 0;
        oversPlayed = 0.0;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public boolean isFirstBatting() {
        return firstBatting;
    }

    public void setFirstBatting(boolean firstBatting) {
        this.firstBatting = firstBatting;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(int totalRuns) {
        this.totalRuns = totalRuns;
    }

    public int getTotalWickets() {
        return totalWickets;
    }

    public void setTotalWickets(int totalWickets) {
        this.totalWickets = totalWickets;
    }

    public double getOversPlayed() {
        return oversPlayed;
    }

    public void setOversPlayed(double oversPlayed) {
        this.oversPlayed = oversPlayed;
    }
}
