package com.androidapp.chowdhury.emran.arclcricketscorer;

public class CurrentBatsmanInfo {
    private int batsmanPlayerId;
    private boolean isStriker;
    private boolean isDisplayedFirst;

    public CurrentBatsmanInfo(int batsmanPlayerId, boolean isStriker, boolean isDisplayedFirst) {
        this.batsmanPlayerId = batsmanPlayerId;
        this.isStriker = isStriker;
        this.isDisplayedFirst = isDisplayedFirst;
    }

    public int getBatsmanPlayerId() {
        return batsmanPlayerId;
    }

    public void setBatsmanPlayerId(int batsmanPlayerId) {
        this.batsmanPlayerId = batsmanPlayerId;
    }

    public boolean isStriker() {
        return isStriker;
    }

    public void setStriker(boolean striker) {
        isStriker = striker;
    }

    public boolean isDisplayedFirst() {
        return isDisplayedFirst;
    }

    public void setDisplayedFirst(boolean displayedFirst) {
        isDisplayedFirst = displayedFirst;
    }
}
