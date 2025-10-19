
package dao;

import models.Room;
import utils.DatabaseInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//This class contains the methods that are need to be performed by the Room
public class RoomDAO {

    //Add a new Room
    public boolean createRoom(Room room) {
        String insertSql = "INSERT INTO rooms ( roomNumber, capacity, occupancy,floorNumber, roomType, hostelId ,isFull) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertSql)) {


            stmt.setString(1, room.getRoomNumber());
            stmt.setInt(2, room.getCapacity());
            stmt.setInt(3, room.getOccupancy());
            stmt.setInt(4, room.getFloorNumber());
            stmt.setString(5,room.getRoomType());
            stmt.setString(6, room.getHostelId());
            stmt.setBoolean(7,room.getRoomFull());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0)
                return true;

        } catch (SQLException e) {
            System.out.println("ERROR while creating room : " + e);
        }
        return false;
    }

    //Fetch details of a particular room by roomNumber
    public Room getRoomDetails(String roomNumber) {
        String sql = "SELECT * FROM rooms WHERE roomNumber = ?";
        Room room = new Room();

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roomNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                room.setRoomNumber(rs.getString("roomNumber"));
                room.setCapacity(rs.getInt("capacity"));
                room.setOccupancy(rs.getInt("occupancy"));
                room.setRoomType(rs.getString("roomType"));
                room.setHostelId(rs.getString("hostelId"));
                room.setFloorNumber(rs.getInt("floorNumber"));
                room.setRoomFull(rs.getBoolean("isFull"));
                return room;
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching room details : " + e);
        }
        return null;
    }

    //Update room details
    public boolean updateRoom(Room room) {
        String updateSql = """
                UPDATE rooms
                SET capacity = ?,
                    occupancy = ?,
                    roomType = ?,
                    floorNumber = ?,
                    isFull =?
                WHERE roomNumber = ?
                """;

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setInt(1, room.getCapacity());
            stmt.setInt(2, room.getOccupancy());
            stmt.setString(3, room.getRoomType());
            stmt.setInt(4, room.getFloorNumber());
            stmt.setBoolean(5, room.getRoomFull());
            stmt.setString(6, room.getRoomNumber());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0)
                return true;

        } catch (SQLException e) {
            System.out.println("ERROR while updating room : " + e);
        }

        return false;
    }

    //Delete a room
    public boolean deleteRoom(String roomNumber) {
        String deleteSql = "DELETE FROM rooms WHERE roomNumber = ?";

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setString(1, roomNumber);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0)
                return true;
        } catch (SQLException e) {
            System.out.println("Error while deleting Room : " + e);
        }
        return false;
    }

    //Fetch all rooms
    public ArrayList<Room> getAllRooms() {
        String sql = "SELECT * FROM rooms";
        ArrayList<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();

                room.setRoomNumber(rs.getString("roomNumber"));
                room.setCapacity(rs.getInt("capacity"));
                room.setOccupancy(rs.getInt("occupancy"));
                room.setFloorNumber(rs.getInt("floorNumber"));
                room.setRoomType(rs.getString("roomType"));
                room.setHostelId(rs.getString("hostelId"));
                room.setRoomFull(rs.getBoolean("isFull"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching all rooms : " + e);
        }
        return rooms;
    }

    //Fetch all available rooms (rooms where occupied < capacity)
    public ArrayList<Room> getAvailableRooms() {
        String sql = "SELECT * FROM rooms WHERE isFull = 0"; // isFull = false
        ArrayList<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();

                room.setRoomNumber(rs.getString("roomNumber"));
                room.setCapacity(rs.getInt("capacity"));
                room.setOccupancy(rs.getInt("occupancy"));
                room.setRoomType(rs.getString("roomType"));
                room.setHostelId(rs.getString("hostelId"));
                room.setRoomFull(rs.getBoolean("isFull"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching available rooms : " + e);
        }
        return rooms;
    }

    //Assign a student to a room (increments occupied count)
    public boolean assignStudentToRoom(int studentId, int roomId) {
        String updateSql = "UPDATE rooms SET occupied = occupied + 1 WHERE roomId = ? AND occupied < capacity";

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setInt(1, roomId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0)
                return true;
        } catch (SQLException e) {
            System.out.println("Error while assigning student to room : " + e);
        }
        return false;
    }

    //Vacate a room (decrement occupied count)
    public boolean vacateRoom(int roomId) {
        String updateSql = "UPDATE rooms SET occupied = occupied - 1 WHERE roomId = ? AND occupied > 0";

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setInt(1, roomId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0)
                return true;
        } catch (SQLException e) {
            System.out.println("Error while vacating room : " + e);
        }
        return false;
    }
}