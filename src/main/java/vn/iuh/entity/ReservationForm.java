package vn.iuh.entity;

import java.sql.Date;

public class ReservationForm {
    private String id;
    private Date reserveDate;
    private String note;
    private Date checkInDate;
    private Date checkOutDate;
    private double initialPrice;
    private double depositPrice;
    private boolean isAdvanced;
    private String customerId;
    private String shiftAssignmentId;

    public ReservationForm() {
    }

    public ReservationForm(String id, Date reserveDate, String note, Date checkInDate, Date checkOutDate,
                           double initialPrice, double depositPrice, boolean isAdvanced, String customerId,
                           String shiftAssignmentId) {
        this.id = id;
        this.reserveDate = reserveDate;
        this.note = note;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.initialPrice = initialPrice;
        this.depositPrice = depositPrice;
        this.isAdvanced = isAdvanced;
        this.customerId = customerId;
        this.shiftAssignmentId = shiftAssignmentId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public boolean getIsAdvanced() {
        return isAdvanced;
    }

    public void setIsAdvanced(boolean isAdvanced) {
        this.isAdvanced = isAdvanced;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getShiftAssignmentId() {
        return shiftAssignmentId;
    }

    public void setShiftAssignmentId(String shiftAssignmentId) {
        this.shiftAssignmentId = shiftAssignmentId;
    }
}
