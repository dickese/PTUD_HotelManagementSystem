package vn.iuh.constraint;

public enum RoomStatus {
    ROOM_AVAILABLE_STATUS("Còn trống"),
    ROOM_BOOKED_STATUS("Chờ check-in"),
    ROOM_CHECKING_STATUS("Đang kiểm tra"),
    ROOM_USING_STATUS("Đang sử dụng"),
    ROOM_CHECKOUT_LATE_STATUS("Trả phòng trễ"),
    ROOM_CLEANING_STATUS("Đang dọn dẹp"),
    ROOM_MAINTENANCE_STATUS("Đang bảo trì");

    public String status;
    RoomStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
