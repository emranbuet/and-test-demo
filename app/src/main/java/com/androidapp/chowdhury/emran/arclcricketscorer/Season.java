package com.androidapp.chowdhury.emran.arclcricketscorer;


public class Season {

    private String Name;

    private int Id;

    public Season(String n, int id) {
        this.setName(n);
        this.setId(id);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
