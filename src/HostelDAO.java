package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Hostel POJO
class Hostel
{
    private int hostelId;
    private String name;
    private String location;
    private int capacity;

    public Hostel(int hostelId, String name, String location, int capacity)
    {
        this.hostelId = hostelId;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public Hostel(String name, String location, int capacity)
    {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    // getters and setters
    public int getHostelId()
    {
        return hostelId;
    }
    public void setHostelId(int hostelId)
    {
        this.hostelId = hostelId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getLocation()
    {
        return location;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }
    public int getCapacity()
    {
        return capacity;
    }
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }
}

// Room POJO
class Room
{
    private int roomId;
    private int hostelId;
    private int roomNumber;
    private int capacity;
    private boolean isAvailable;

    public Room(int roomId, int hostelId, int roomNumber, int capacity, boolean isAvailable)
    {
        this.roomId = roomId;
        this.hostelId = hostelId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
    }

    // getters
    public int getRoomId()
    {
        return roomId;
    }
    public int getHostelId()
    {
        return hostelId;
    }
    public int getRoomNumber()
    {
        return roomNumber;
    }
    public int getCapacity()
    {
        return capacity;
    }
    public boolean isAvailable()
    {
        return isAvailable;
    }
}

// HostelDAO (single class with all logic)
public class HostelDAO
{

    private Connection conn;

    // Constructor accepts DB connection
    public HostelDAO(Connection conn)
    {
        this.conn = conn;
    }

    // Create hostel
    public void createHostel(Hostel hostel)
    {
        String sql = "INSERT INTO hostel (name, location, capacity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, hostel.getName());
            ps.setString(2, hostel.getLocation());
            ps.setInt(3, hostel.getCapacity());
            ps.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Update hostel
    public void updateHostel(Hostel hostel)
    {
        String sql = "UPDATE hostel SET name=?, location=?, capacity=? WHERE hostel_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, hostel.getName());
            ps.setString(2, hostel.getLocation());
            ps.setInt(3, hostel.getCapacity());
            ps.setInt(4, hostel.getHostelId());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Delete hostel
    public void deleteHostel(int hostelId)
    {
        String sql = "DELETE FROM hostel WHERE hostel_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, hostelId);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Get hostel by ID
    public Hostel getHostelById(int hostelId)
    {
        String sql = "SELECT * FROM hostel WHERE hostel_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, hostelId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return new Hostel(
                        rs.getInt("hostel_id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getInt("capacity")
                );
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // Get all hostels
    public List<Hostel> getAllHostels()
    {
        List<Hostel> hostels = new ArrayList<>();
        String sql = "SELECT * FROM hostel";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {

            while (rs.next())
            {
                hostels.add(new Hostel(
                        rs.getInt("hostel_id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getInt("capacity")
                ));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return hostels;
    }

    // Get all rooms in a hostel
    public List<Room> getRoomsInHostel(int hostelId)
    {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE hostel_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, hostelId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                rooms.add(new Room(
                        rs.getInt("room_id"),
                        rs.getInt("hostel_id"),
                        rs.getInt("room_number"),
                        rs.getInt("capacity"),
                        rs.getBoolean("is_available")
                ));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rooms;
    }
}
