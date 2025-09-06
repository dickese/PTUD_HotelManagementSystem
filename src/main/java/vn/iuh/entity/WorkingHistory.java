package vn.iuh.entity;

import java.sql.Time;

public class WorkingHistory {
    private String id;
    private String taskName;
    private Time createTime;
    private String actionDescription;
    private String shiftAssignmentId;
    private String accountId;

    public WorkingHistory() {
    }

    public WorkingHistory(String id, String taskName, Time createTime, String actionDescription,
                          String shiftAssignmentId, String accountId) {
        this.id = id;
        this.taskName = taskName;
        this.createTime = createTime;
        this.actionDescription = actionDescription;
        this.shiftAssignmentId = shiftAssignmentId;
        this.accountId = accountId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getShiftAssignmentId() {
        return shiftAssignmentId;
    }

    public void setShiftAssignmentId(String shiftAssignmentId) {
        this.shiftAssignmentId = shiftAssignmentId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
