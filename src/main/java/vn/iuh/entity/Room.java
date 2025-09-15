package vn.iuh.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Room {
    private String id;
    private String roomName;
    private String roomStatus;
    private Timestamp createDate;
    private String note;
    private String roomDescription;
    private String roomCategoryId;

    public Room() {
    }

    public Room(String id, String roomName, String roomStatus, Timestamp createDate, String note, String roomDescription,
                String roomCategoryId) {
        this.id = id;
        this.roomName = roomName;
        this.roomStatus = roomStatus;
        this.createDate = createDate;
        this.note = note;
        this.roomDescription = roomDescription;
        this.roomCategoryId = roomCategoryId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getRoomCategoryId() {
        return roomCategoryId;
    }

    public void setRoomCategoryId(String roomCategoryId) {
        this.roomCategoryId = roomCategoryId;
    }
}