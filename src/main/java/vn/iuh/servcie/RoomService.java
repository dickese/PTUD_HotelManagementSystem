package vn.iuh.servcie;

import vn.iuh.entity.Room;

public interface RoomService {
    Room getRoomByID(int roomID);
    Room createRoom(Room room);
    Room updateRoom(Room room);
    boolean deleteRoomByID(int roomID);
}
