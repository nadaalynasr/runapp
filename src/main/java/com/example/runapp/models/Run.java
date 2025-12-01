package com.example.runapp.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Run {
    private Integer runId;
    private Integer userId;
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

}
