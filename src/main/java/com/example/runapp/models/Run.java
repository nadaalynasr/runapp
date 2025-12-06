package com.example.runapp.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Run {
    private Integer runId;
    private Integer userId;
    private String username;
    private Date runDate;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer elapsedTime;
    private Double distanceMeters;
    private Double bpm;

    public Run(Integer userId, Date runDate, Timestamp startTime, Timestamp endTime, Integer elapsedTime, Double distanceMeters, Double bpm) {
        this.userId = userId;
        this.runDate = runDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.elapsedTime = elapsedTime;
        this.distanceMeters = distanceMeters;
        this.bpm = bpm;
    }

    public Integer getRunId() {
        return runId;
    }
    public void setRunId(Integer runId) {
        this.runId = runId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Date getRunDate() {
        return runDate;
    }
    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    public Integer getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public Double getDistanceMeters() {
        return distanceMeters;
    }
    public void setDistanceMeters(Double distanceMeters) {
        this.distanceMeters = distanceMeters;
    }
    public Double getBpm() {
        return bpm;
    }
    public void setBpm(Double bpm) {
        this.bpm = bpm;
    }

    public String getFormattedElapsedTime() {
        if (elapsedTime == null) return null;
        int hours = elapsedTime / 3600;
        int minutes = (elapsedTime % 3600) / 60;
        int seconds = elapsedTime % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getFormattedDistance() {
        if (distanceMeters == null) return null;
        double miles = distanceMeters / 1609.34;
        return String.format("%.2f", miles);
    }

    public String getFormattedRunDate() {
        if (runDate == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
        return sdf.format(runDate);
    }

}
