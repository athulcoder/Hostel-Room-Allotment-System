package utils;

import models.Room;

import java.util.List;

public class RoomList {

    public static boolean containsRoom(List<Room> availableRooms, Room room) {
        for (Room r : availableRooms) {
            if (r.getRoomNumber().equals(room.getRoomNumber())) {
                return true;
            }
        }
        return false;
    }
}