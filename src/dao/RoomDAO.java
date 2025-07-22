package dao;

import models.Room;
import models.Student;
import utils.DatabaseInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // for error handling
    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
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

    //allocating room for a new student

    public void allocateRoomToStudent(Room r){
        String roomNo = r.getRoomNumber();
        int occupancy = r.getOccupancy();
        String sql = "UPDATE rooms SET occupancy=? ,isFull=? WHERE roomNumber =?";

        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,occupancy);
            stmt.setBoolean(2,r.getRoomFull());
            stmt.setString(3,roomNo);
            stmt.executeUpdate();
            System.out.println("Room "+roomNo +" updated");
        } catch (SQLException e){
            System.out.println("Error while creating ROOM : "+e.getMessage());
        }
    }

    //Getting available rooms
    public List<Room> getAvailableRoomsByTypeAndFloor(String roomType,int floorNumber){
        String sql = "SELECT * from rooms WHERE roomType=? AND floorNumber=? AND isFull= 0 ";
        return getRoomsByFilter(sql, ps -> {ps.setString(1, roomType);ps.setInt(2,floorNumber);});
    }
    // get All Rooms
    public List<Room> getAllRooms(){
        String sql = "SELECT * from rooms";
        return getRoomsByFilter(sql,preparedStatement -> {});
    }


    //get room by number
    public Room getRoomByNumber(String roomNumber){
        String sql = "SELECT * from rooms WHERE roomNumber = ? ";
        Room room = new Room();

        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1,roomNumber);
            ResultSet res = statement.executeQuery();

           room = mapRoom(res);


        }catch (SQLException e){
            System.out.println("ERROR while getting room by number : "+ e.getMessage());
        }

    return room;
    }

    //get room by floor
    public List<Room> getRoomsByFloor(int floorNumber) {
        String sql = "SELECT * FROM rooms WHERE floorNumber = ?";
        return getRoomsByFilter(sql, ps -> ps.setInt(1, floorNumber));

    }

    //Get room by type
    public List<Room> getRoomsByType(String roomType) {
        String sql = "SELECT * FROM rooms WHERE roomType = ?";
        return getRoomsByFilter(sql, ps -> ps.setString(1, roomType));

    }



    // filters room by  Type , floorNumber
    public List<Room> getRoomsByFilter(String sql, SQLConsumer<PreparedStatement> binder) {
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            binder.accept(ps);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Room room = new Room();
                rooms.add(mapRoom(res));
            }

        } catch (SQLException e) {
            System.out.println("ERROR fetching rooms: " + e.getMessage());
        }

        return rooms;
    }

    // map room

    public Room mapRoom(ResultSet res) throws SQLException {
        Room room = new Room();
        room.setRoomNumber(res.getString("roomNumber"));
        room.setRoomType(res.getString("roomType"));
        room.setFloorNumber(res.getInt("floorNumber"));
        room.setCapacity(res.getInt("capacity"));
        room.setOccupancy(res.getInt("occupancy"));
        room.setRoomFull(res.getBoolean("isFull"));

        return room;
    }
    public  int academicYearMapFloor(String academicYear){

        int floor = 1;
        switch (academicYear){
            case "1st Year" -> {
                floor= 5;
            }
            case "2nd Year" -> {
                floor= 4;
            }
            case "3rd Year"->{
                floor= 3;
            }
            case "Final Year"->{
                floor= 2;
            }



        }
        return floor;
    }


}
