package vn.iuh.entity;

import java.sql.Time;

public class ShiftAssignment {
    private String id;
    private int counterNumber;
    private Time startTime;
    private Time endTime;
    private String accountId;

    public ShiftAssignment() {
    }

    public ShiftAssignment(String id, int counterNumber, Time startTime, Time endTime,
                           String accountId) {
        this.id = id;
        this.counterNumber = counterNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.accountId = accountId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCounterNumber() {
        return counterNumber;
    }

    public void setCounterNumber(int counterNumber) {
        this.counterNumber = counterNumber;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
