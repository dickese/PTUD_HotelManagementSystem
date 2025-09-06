package vn.iuh.entity;

import java.sql.Time;

public class RoomListPrice {
    private String id;
    private Time createAt;
    private Double previousDailyPrice;
    private Double previousHourlyPrice;
    private Double updatedDailyPrice;
    private Double updatedHourlyPrice;
    private String roomCategoryId;
    private String shiftAssignmentId;

    public RoomListPrice() {
    }

    public RoomListPrice(String id, Time createAt, Double previousDailyPrice, Double previousHourlyPrice,
                         Double updatedDailyPrice, Double updatedHourlyPrice, String roomCategoryId,
                         String shiftAssignmentId) {
        this.id = id;
        this.createAt = createAt;
        this.previousDailyPrice = previousDailyPrice;
        this.previousHourlyPrice = previousHourlyPrice;
        this.updatedDailyPrice = updatedDailyPrice;
        this.updatedHourlyPrice = updatedHourlyPrice;
        this.roomCategoryId = roomCategoryId;
        this.shiftAssignmentId = shiftAssignmentId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Time getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Time createAt) {
        this.createAt = createAt;
    }

    public Double getPreviousDailyPrice() {
        return previousDailyPrice;
    }

    public void setPreviousDailyPrice(Double previousDailyPrice) {
        this.previousDailyPrice = previousDailyPrice;
    }

    public Double getPreviousHourlyPrice() {
        return previousHourlyPrice;
    }

    public void setPreviousHourlyPrice(Double previousHourlyPrice) {
        this.previousHourlyPrice = previousHourlyPrice;
    }

    public Double getUpdatedDailyPrice() {
        return updatedDailyPrice;
    }

    public void setUpdatedDailyPrice(Double updatedDailyPrice) {
        this.updatedDailyPrice = updatedDailyPrice;
    }

    public Double getUpdatedHourlyPrice() {
        return updatedHourlyPrice;
    }

    public void setUpdatedHourlyPrice(Double updatedHourlyPrice) {
        this.updatedHourlyPrice = updatedHourlyPrice;
    }

    public String getRoomCategoryId() {
        return roomCategoryId;
    }

    public void setRoomCategoryId(String roomCategoryId) {
        this.roomCategoryId = roomCategoryId;
    }

    public String getShiftAssignmentId() {
        return shiftAssignmentId;
    }

    public void setShiftAssignmentId(String shiftAssignmentId) {
        this.shiftAssignmentId = shiftAssignmentId;
    }
}