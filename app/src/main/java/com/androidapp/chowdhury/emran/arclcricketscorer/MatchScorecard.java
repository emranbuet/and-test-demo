package com.androidapp.chowdhury.emran.arclcricketscorer;

import java.util.List;
import java.util.Map;

public class MatchScorecard {
    private int matchId;
    private Team team1, team2;

    // List of 8 players played in this match
    private List<Player> playersOfTeam1, playersOfTeam2;


    // Map batting position to Player
    private Map<Integer, Player> battingOrderTeam1, battingOrderTeam2;

    //Map playerId to his match information
    private Map<Integer, BattingStatistics> batstatsTeam1, batstatsTeam2;
    private Map<Integer, BowlingStatistics> bowlstatsTeam1, bowlstatsTeam2;

    public MatchScorecard(int matchId) {
        this.matchId = matchId;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public List<Player> getPlayersOfTeam1() {
        return playersOfTeam1;
    }

    public void setPlayersOfTeam1(List<Player> playersOfTeam1) {
        this.playersOfTeam1 = playersOfTeam1;
    }

    public List<Player> getPlayersOfTeam2() {
        return playersOfTeam2;
    }

    public void setPlayersOfTeam2(List<Player> playersOfTeam2) {
        this.playersOfTeam2 = playersOfTeam2;
    }

    public Map<Integer, Player> getBattingOrderTeam1() {
        return battingOrderTeam1;
    }

    public void setBattingOrderTeam1(Map<Integer, Player> battingOrderTeam1) {
        this.battingOrderTeam1 = battingOrderTeam1;
    }

    public Map<Integer, Player> getBattingOrderTeam2() {
        return battingOrderTeam2;
    }

    public void setBattingOrderTeam2(Map<Integer, Player> battingOrderTeam2) {
        this.battingOrderTeam2 = battingOrderTeam2;
    }

    public Map<Integer, BattingStatistics> getBatstatsTeam1() {
        return batstatsTeam1;
    }

    public void setBatstatsTeam1(Map<Integer, BattingStatistics> batstatsTeam1) {
        this.batstatsTeam1 = batstatsTeam1;
    }

    public Map<Integer, BattingStatistics> getBatstatsTeam2() {
        return batstatsTeam2;
    }

    public void setBatstatsTeam2(Map<Integer, BattingStatistics> batstatsTeam2) {
        this.batstatsTeam2 = batstatsTeam2;
    }

    public Map<Integer, BowlingStatistics> getBowlstatsTeam1() {
        return bowlstatsTeam1;
    }

    public void setBowlstatsTeam1(Map<Integer, BowlingStatistics> bowlstatsTeam1) {
        this.bowlstatsTeam1 = bowlstatsTeam1;
    }

    public Map<Integer, BowlingStatistics> getBowlstatsTeam2() {
        return bowlstatsTeam2;
    }

    public void setBowlstatsTeam2(Map<Integer, BowlingStatistics> bowlstatsTeam2) {
        this.bowlstatsTeam2 = bowlstatsTeam2;
    }
}
