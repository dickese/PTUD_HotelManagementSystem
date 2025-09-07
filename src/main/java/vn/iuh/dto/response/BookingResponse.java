package vn.iuh.dto.response;

import java.sql.Date;
import java.util.Objects;

public class BookingResponse {
    private String roomId;
    private String roomName;
    private String roomStatus;
    private String roomType;
    private String numberOfCustomers;
    private double dailyPrice;
    private double hourlyPrice;
    private String customerName;
    private Date timeIn;
    private Date timeOut;

    public BookingResponse(String roomId, String roomName, String roomStatus, String roomType, String numberOfCustomers,
                           double dailyPrice, double hourlyPrice) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomStatus = roomStatus;
        this.roomType = roomType;
        this.numberOfCustomers = numberOfCustomers;
        this.dailyPrice = dailyPrice;
        this.hourlyPrice = hourlyPrice;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
               "roomId='" + roomId + '\'' +
               ", roomName='" + roomName + '\'' +
               ", roomStatus='" + roomStatus + '\'' +
               ", roomType='" + roomType + '\'' +
               ", numberOfCustomers='" + numberOfCustomers + '\'' +
               ", dailyPrice=" + dailyPrice +
               ", hourlyPrice=" + hourlyPrice +
               ", customerName='" + customerName + '\'' +
               ", timeIn=" + timeIn +
               ", timeOut=" + timeOut +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof String) {
            return roomId.equals(o);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomName, roomStatus, roomType, numberOfCustomers, dailyPrice, hourlyPrice,
                            customerName, timeIn, timeOut);
    }

    public void updateBookingInfo(String customerName, Date timeIn, Date timeOut) {
        this.customerName = customerName;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
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
