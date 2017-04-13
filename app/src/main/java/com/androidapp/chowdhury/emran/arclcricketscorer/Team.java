package com.androidapp.chowdhury.emran.arclcricketscorer;

public class Team {
    private String teamName;
    private int teamId;
    private int leagueId;

    public Team(String teamName, int teamId) {
        this.teamName = teamName;
        this.teamId = teamId;
    }

    public Team(String teamName, int teamId, int leagueId) {
        this.teamName = teamName;
        this.teamId = teamId;
        this.leagueId = leagueId;
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
}
