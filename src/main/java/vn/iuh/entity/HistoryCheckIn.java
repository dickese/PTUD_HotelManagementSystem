package vn.iuh.entity;

import java.sql.Date;

public class HistoryCheckIn {
    private String id;
    private Date checkInTime;
    private boolean isFirst;
    private String roomReservationDetailId;

    public HistoryCheckIn() {
    }

    public HistoryCheckIn(String id, Date checkInTime, boolean isFirst, String roomReservationDetailId) {
        this.id = id;
        this.checkInTime = checkInTime;
        this.isFirst = isFirst;
        this.roomReservationDetailId = roomReservationDetailId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public String getRoomReservationDetailId() {
        return roomReservationDetailId;
    }

    public void setRoomReservationDetailId(String roomReservationDetailId) {
        this.roomReservationDetailId = roomReservationDetailId;
    }
}
