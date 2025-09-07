package vn.iuh.dto.repository;

import java.sql.Date;

public class BookingInfo {
    private final String roomId;
    private final String customerName;
    private final Date timeIn;
    private final Date timeOut;

    public BookingInfo(String roomId, String customerName, Date timeIn, Date timeOut) {
        this.roomId = roomId;
        this.customerName = customerName;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }
}
