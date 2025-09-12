package vn.iuh.dto.event.create;

public class RoomCreationEvent {
    private final String roomName;
    private final String roomStatus;
    private final String note;
    private final String roomDescription;
    private final String roomCategoryId;

    public RoomCreationEvent(String roomName, String roomStatus, String note, String roomDescription,
                             String roomCategoryId) {
        this.roomName = roomName;
        this.roomStatus = roomStatus;
        this.note = note;
        this.roomDescription = roomDescription;
        this.roomCategoryId = roomCategoryId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public String getNote() {
        return note;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getRoomCategoryId() {
        return roomCategoryId;
    }
}
