package com.example.runapp.models;

public class TrendingView {
    private int id;
    private String name;
    private String nextRunDisplay;
    private int memberCount;
    private boolean isMember;

    // TODO: angel will implement constructors + logic later
    public TrendingView(int id, String name, String nextRunDisplay, int memberCount, boolean isMember) {
        this.id = id;
        this.name = name;
        this.nextRunDisplay = nextRunDisplay;
        this.memberCount = memberCount;
        this.isMember = isMember;
    }
    // Getters (optional placeholders)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getNextRunDisplay() { return nextRunDisplay; }
    public int getMemberCount() { return memberCount; }
    public boolean isMember() { return isMember; }
}
