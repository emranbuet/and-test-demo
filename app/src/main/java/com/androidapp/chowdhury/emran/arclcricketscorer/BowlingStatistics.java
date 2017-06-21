package com.androidapp.chowdhury.emran.arclcricketscorer;

import java.io.Serializable;

public class BowlingStatistics implements Serializable {
    private int bowlerPlayerId;
    private String bowlerName;
    private double oversBowled; // Number after decimal point represents the ball
    private int runsConceded;
    private int maidenOvers;
    private int widesCount;
    private int noBallCount;
    private int numberOfWickets;

    public BowlingStatistics(int bowlerPlayerId, String bName) {
        this.bowlerPlayerId = bowlerPlayerId;
        this.bowlerName = bName;
        this.oversBowled = 0.0;
        this.runsConceded = 0;
        this.maidenOvers = 0;
        this.widesCount = 0;
        this.noBallCount = 0;
        this.numberOfWickets = 0;
    }

    public int getBowlerPlayerId() {
        return bowlerPlayerId;
    }

    public void setBowlerPlayerId(int bowlerPlayerId) {
        this.bowlerPlayerId = bowlerPlayerId;
    }

    public String getBowlerName() {
        return bowlerName;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }

    public double getOversBowled() {
        return oversBowled;
    }

    public void setOversBowled(double oversBowled) {
        this.oversBowled = oversBowled;
    }

    public int getRunsConceded() {
        return runsConceded;
    }

    public void setRunsConceded(int runsConceded) {
        this.runsConceded = runsConceded;
    }

    public int getMaidenOvers() {
        return maidenOvers;
    }

    public void setMaidenOvers(int maidenOvers) {
        this.maidenOvers = maidenOvers;
    }

    public int getWidesCount() {
        return widesCount;
    }

    public void setWidesCount(int widesCount) {
        this.widesCount = widesCount;
    }

    public int getNoBallCount() {
        return noBallCount;
    }

    public void setNoBallCount(int noBallCount) {
        this.noBallCount = noBallCount;
    }

    public int getNumberOfWickets() {
        return numberOfWickets;
    }

    public void setNumberOfWickets(int numberOfWickets) {
        this.numberOfWickets = numberOfWickets;
    }
}
