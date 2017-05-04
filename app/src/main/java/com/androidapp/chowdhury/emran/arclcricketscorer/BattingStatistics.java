package com.androidapp.chowdhury.emran.arclcricketscorer;

public class BattingStatistics {
    private int batsmanPlayerId;
    private int runsScored;
    private int ballsFaced;
    private int numOf4s;
    private int numOf6s;
    private OutType howOut;
    private int outByBowlerId;
    private int caughtByPlayerId;   // For runout- it represents fielder

    public BattingStatistics(int batsmanPlayerId) {
        this.batsmanPlayerId = batsmanPlayerId;
        this.runsScored = 0;
        this.ballsFaced = 0;
        this.numOf4s = 0;
        this.numOf6s = 0;
    }

    public int getBatsmanPlayerId() {
        return batsmanPlayerId;
    }

    public void setBatsmanPlayerId(int batsmanPlayerId) {
        this.batsmanPlayerId = batsmanPlayerId;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getBallsFaced() {
        return ballsFaced;
    }

    public void setBallsFaced(int ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public int getNumOf4s() {
        return numOf4s;
    }

    public void setNumOf4s(int numOf4s) {
        this.numOf4s = numOf4s;
    }

    public int getNumOf6s() {
        return numOf6s;
    }

    public void setNumOf6s(int numOf6s) {
        this.numOf6s = numOf6s;
    }

    public OutType getHowOut() {
        return howOut;
    }

    public void setHowOut(OutType howOut) {
        this.howOut = howOut;
    }

    public int getOutByBowlerId() {
        return outByBowlerId;
    }

    public void setOutByBowlerId(int outByBowlerId) {
        this.outByBowlerId = outByBowlerId;
    }

    public int getCaughtByPlayerId() {
        return caughtByPlayerId;
    }

    public void setCaughtByPlayerId(int caughtByPlayerId) {
        this.caughtByPlayerId = caughtByPlayerId;
    }

}
