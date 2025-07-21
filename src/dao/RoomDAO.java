package dao;

import models.Room;
import utils.DatabaseInitializer;

import java.sql.*;

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

    //get room by number


    public Room getRoomByNumber(String roomNumber){
        String sql = "SELECT * from rooms WHERE roomNumber = ? ";
        Room room = new Room();

        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1,roomNumber);
            ResultSet res = statement.executeQuery();
            room.setRoomNumber(res.getString("roomNumber"));
            room.setRoomType(res.getString("roomType"));
            room.setFloorNumber(res.getInt("floorNumber"));
            room.setCapacity(res.getInt("capacity"));
            room.setOccupancy(res.getInt("occupancy"));
            room.setRoomFull(res.getBoolean("isFull"));


        }catch (SQLException e){
            System.out.println("ERROR while getting room by number : "+ e.getMessage());
        }

    return room;
    }

}
