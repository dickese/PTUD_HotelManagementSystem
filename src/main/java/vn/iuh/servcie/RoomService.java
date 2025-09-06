package vn.iuh.servcie;

import vn.iuh.dto.event.create.CreateRoomEvent;
import vn.iuh.dto.event.update.UpdateRoomEvent;
import vn.iuh.entity.Room;

import java.util.List;

public interface RoomService {
    Room getRoomByID(String roomID);
    List<Room> getAll();
    Room createRoom(CreateRoomEvent room);
    Room updateRoom(UpdateRoomEvent room);
    boolean deleteRoomByID(String roomID);
}
