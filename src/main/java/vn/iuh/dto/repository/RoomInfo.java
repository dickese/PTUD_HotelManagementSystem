package vn.iuh.dto.repository;

public class RoomInfo {
    private final String id;
    private final String roomName;
    private final String roomStatus;
    private final String roomType;
    private final String numberOfCustomers;
    private final double dailyPrice;
    private final double hourlyPrice;

    public RoomInfo(String id, String roomName, String roomStatus, String roomType, String numberOfCustomers,
                    double dailyPrice, double hourlyPrice) {
        this.id = id;
        this.roomName = roomName;
        this.roomStatus = roomStatus;
        this.roomType = roomType;
        this.numberOfCustomers = numberOfCustomers;
        this.dailyPrice = dailyPrice;
        this.hourlyPrice = hourlyPrice;
    }

    public String getId() {
        return id;
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
}
