package vn.iuh.dto.event.create;

import java.sql.Date;
import java.util.List;

public class BookingCreationEvent {
    private String customerName;
    private String phoneNumber;
    private String CCCD;
    private Date reserveDate;
    private String note;
    private Date checkInDate;
    private Date checkOutDate;
    private double initialPrice;
    private double depositPrice;
    private boolean isAdvanced;
    private List<String> roomIds;
    private List<String> serviceIds;
    private String shiftAssignmentId;

    public BookingCreationEvent(String customerName, String phoneNumber, String CCCD, Date reserveDate, String note,
                                Date checkInDate, Date checkOutDate, double initialPrice, double depositPrice,
                                boolean isAdvanced, List<String> roomIds, List<String> serviceIds,
                                String shiftAssignmentId) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.CCCD = CCCD;
        this.reserveDate = reserveDate;
        this.note = note;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.initialPrice = initialPrice;
        this.depositPrice = depositPrice;
        this.isAdvanced = isAdvanced;
        this.roomIds = roomIds;
        this.serviceIds = serviceIds;
        this.shiftAssignmentId = shiftAssignmentId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCCCD() {
        return CCCD;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public String getNote() {
        return note;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

    public List<String> getRoomIds() {
        return roomIds;
    }

    public List<String> getServiceIds() {
        return serviceIds;
    }

    public String getShiftAssignmentId() {
        return shiftAssignmentId;
    }
}
