package dao;

import models.Room;
import utils.DatabaseInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RoomDAO {

    // create room function

    public void createRoom(Room room){

        String sql = "INSERT INTO rooms (roomNumber, roomType, floorNumber, capacity,occupancy, isFull) VALUES (?, ?, ?, ?,?,?)";

        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,room.getRoomNumber());
            stmt.setString(2,room.getRoomType());
            stmt.setInt(3,room.getFloorNumber());
            stmt.setInt(4,room.getCapacity());
            stmt.setInt(5,room.getOccupancy());
            stmt.setBoolean(6, room.getRoomFull());
            stmt.executeUpdate();
            System.out.println("new room created");
        } catch (SQLException e){
            System.out.println("Error while creating ROOM : "+e.getMessage());
        }
    }

}
