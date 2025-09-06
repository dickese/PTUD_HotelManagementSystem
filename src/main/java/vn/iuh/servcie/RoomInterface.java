package vn.iuh.servcie;

import entity.Room;

public interface RoomInterface {
    Room getRoom(int roomID);
    Room createRoom(Room room);
    Room updateRoom(Room room);
    boolean deleteRoom(int roomID);
}
