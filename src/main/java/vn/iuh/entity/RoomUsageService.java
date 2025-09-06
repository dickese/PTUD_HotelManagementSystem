package vn.iuh.entity;

import java.sql.Date;

public class RoomUsageService {
    private String id;
    private double totalPrice;
    private int quantity;
    private Date orderTime;
    private String reservationFormId;
    private String serviceItemId;
    private String shiftAssignmentId;

    public RoomUsageService() {}

    public RoomUsageService(String id, double totalPrice, int quantity, Date orderTime, String reservationFormId, String serviceItemId, String shiftAssignmentId) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.orderTime = orderTime;
        this.reservationFormId = reservationFormId;
        this.serviceItemId = serviceItemId;
        this.shiftAssignmentId = shiftAssignmentId;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Date getOrderTime() { return orderTime; }
    public void setOrderTime(Date orderTime) { this.orderTime = orderTime; }
    public String getReservationFormId() { return reservationFormId; }
    public void setReservationFormId(String reservationFormId) { this.reservationFormId = reservationFormId; }
    public String getServiceItemId() { return serviceItemId; }
    public void setServiceItemId(String serviceItemId) { this.serviceItemId = serviceItemId; }
    public String getShiftAssignmentId() { return shiftAssignmentId; }
    public void setShiftAssignmentId(String shiftAssignmentId) { this.shiftAssignmentId = shiftAssignmentId; }
}
