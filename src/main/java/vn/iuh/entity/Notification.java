package vn.iuh.entity;

import java.sql.Date;

public class Notification {
    private String id;
    private Date createTime;
    private String notiMessage;
    private String shiftAssignmentId;

    public Notification() {
    }

    public Notification(String id, Date createTime, String notiMessage, String shiftAssignmentId) {
        this.id = id;
        this.createTime = createTime;
        this.notiMessage = notiMessage;
        this.shiftAssignmentId = shiftAssignmentId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNotiMessage() {
        return notiMessage;
    }

    public void setNotiMessage(String notiMessage) {
        this.notiMessage = notiMessage;
    }

    public String getShiftAssignmentId() {
        return shiftAssignmentId;
    }

    public void setShiftAssignmentId(String shiftAssignmentId) {
        this.shiftAssignmentId = shiftAssignmentId;
    }
}
