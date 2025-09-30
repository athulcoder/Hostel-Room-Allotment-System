package dao;

import models.Hostel;
import models.Room;
import utils.DatabaseInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HostelDAO
{
    // 🔹 Insert a new hostel into the database
    public void addHostel(Hostel hostel)
    {
        String query = "INSERT INTO hostels (hostelId, hostelName, type, totalRoomCount, totalFloorCount, maxCapacity) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, hostel.getHostelId());
            stmt.setString(2, hostel.getHostelName());
            stmt.setString(3, hostel.getType());
            stmt.setInt(4, hostel.getTotalRoomCount());
            stmt.setInt(5, hostel.getTotalFloorCount());
            stmt.setInt(6, hostel.getMaxCapacity());

            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("Error  while adding Hostel : "+e.getMessage());
        }
    }

    // 🔹 Get hostel by ID
    public Hostel getHostelById(String hostelId)
    {
        String query = "SELECT * FROM hostels WHERE hostelId = ?";
        Hostel hostel = null;

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, hostelId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                hostel = new Hostel(
                        rs.getString("hostelName"),
                        rs.getString("type"),
                        rs.getInt("totalRoomCount"),
                        rs.getInt("totalFloorCount"),
                        rs.getInt("maxCapacity"),
                        rs.getString("hostelId")
                );
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error  while fetching  Hostel by Id : "+e.getMessage());
        }
        return hostel;
    }

    // 🔹 Get all hostels
    public List<Hostel> getAllHostels()
    {
        List<Hostel> hostels = new ArrayList<>();
        String query = "SELECT * FROM hostels";

        try (Connection conn = DatabaseInitializer.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query))
        {

            while (rs.next())
            {
                Hostel hostel = new Hostel(
                        rs.getString("hostelName"),
                        rs.getString("type"),
                        rs.getInt("totalRoomCount"),
                        rs.getInt("totalFloorCount"),
                        rs.getInt("maxCapacity"),
                        rs.getString("hostelId")
                );
                hostels.add(hostel);
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error my getting all hostels "+e.getMessage());
        }
        return hostels;
    }

    // 🔹 Update hostel details
    public void updateHostel(Hostel hostel)
    {
        String query = "UPDATE hostels SET hostelName = ?, type = ?, totalRoomCount = ?, totalFloorCount = ?, maxCapacity = ? " +
                "WHERE hostelId = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, hostel.getHostelName());
            stmt.setString(2, hostel.getType());
            stmt.setInt(3, hostel.getTotalRoomCount());
            stmt.setInt(4, hostel.getTotalFloorCount());
            stmt.setInt(5, hostel.getMaxCapacity());
            stmt.setString(6, hostel.getHostelId());

            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("Error  while updating Hostel : "+e.getMessage());
        }
    }

    // 🔹 Delete hostel by ID
    public void deleteHostel(String hostelId)
    {
        String query = "DELETE FROM hostels WHERE hostelId = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, hostelId);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("Error  while deleting Hostel : "+e.getMessage());
        }
    }

    // 🔹 Get all rooms inside a hostel
    public List<Room> getRoomsInHostel(String hostelId)
    {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE hostelId = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, hostelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Room room = new Room(
                        rs.getString("roomNumber"),
                        rs.getString("roomType"),
                        rs.getInt("floorNumber"),
                        rs.getInt("capacity"),
                        rs.getString("hostelId")
                );
                room.setOccupancy(rs.getInt("occupancy"));
                room.setRoomFull(rs.getBoolean("isFull"));
                rooms.add(room);
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error  while getting rooms of  Hostel : "+e.getMessage());
        }
        return rooms;
    }
}
