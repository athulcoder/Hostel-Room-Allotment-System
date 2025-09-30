
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
        String insertSql = "INSERT INTO rooms (roomId, roomNumber, capacity, occupied, roomType, hostelId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertSql)) {

            stmt.setString(1, room.getRoomId());
            stmt.setInt(2, room.getRoomNumber());
            stmt.setInt(3, room.getCapacity());
            stmt.setInt(4, room.getOccupied());
            stmt.setString(5, room.getRoomType());
            stmt.setString(6, room.getHostelId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0)
                return true;

        } catch (SQLException e) {
            System.out.println("ERROR while creating room : " + e);
        }
        return false;
    }

    //Fetch details of a particular room by roomNumber
    public Room getRoomDetails(int roomNumber) {
        String sql = "SELECT * FROM rooms WHERE roomNumber = ?";
        Room room = new Room();

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                room.setRoomId(rs.getString("roomId"));
                room.setRoomNumber(rs.getInt("roomNumber"));
                room.setCapacity(rs.getInt("capacity"));
                room.setOccupied(rs.getInt("occupied"));
                room.setRoomType(rs.getString("roomType"));
                room.setHostelId(rs.getString("hostelId"));
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
                    occupied = ?,
                    roomType = ?,
                    hostelId = ?
                WHERE roomNumber = ?
                """;

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setInt(1, room.getCapacity());
            stmt.setInt(2, room.getOccupied());
            stmt.setString(3, room.getRoomType());
            stmt.setString(4, room.getHostelId());
            stmt.setInt(5, room.getRoomNumber());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0)
                return true;

        } catch (SQLException e) {
            System.out.println("ERROR while updating room : " + e);
        }

        return false;
    }

    //Delete a room
    public boolean deleteRoom(int roomNumber) {
        String deleteSql = "DELETE FROM rooms WHERE roomNumber = ?";

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, roomNumber);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0)
                return true;
        } catch (SQLException e) {
            System.out.println("Error while deleting Room : " + e);
        }
        return false;
    }

    //Fetch all rooms
    public List<Room> getAllRooms() {
        String sql = "SELECT * FROM rooms";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getString("roomId"));
                room.setRoomNumber(rs.getInt("roomNumber"));
                room.setCapacity(rs.getInt("capacity"));
                room.setOccupied(rs.getInt("occupied"));
                room.setRoomType(rs.getString("roomType"));
                room.setHostelId(rs.getString("hostelId"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching all rooms : " + e);
        }
        return rooms;
    }

    //Fetch all available rooms (rooms where occupied < capacity)
    public List<Room> getAvailableRooms() {
        String sql = "SELECT * FROM rooms WHERE occupied < capacity";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getString("roomId"));
                room.setRoomNumber(rs.getInt("roomNumber"));
                room.setCapacity(rs.getInt("capacity"));
                room.setOccupied(rs.getInt("occupied"));
                room.setRoomType(rs.getString("roomType"));
                room.setHostelId(rs.getString("hostelId"));
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