package com.example.runapp.models;

public class GroupsView {
    public int id;
    public String name;
    public String nextRunDisplay;
    public int memberCount;
    public int memberMax;
    public boolean isMember;

    public GroupsView(int id, String name, String nextRunDisplay, int memberCount, int memberMax, boolean isMember) {
        this.id = id;
        this.name = name;
        this.nextRunDisplay = nextRunDisplay;
        this.memberCount = memberCount;
        this.memberMax = memberMax;
        this.isMember = isMember;
    }
}
