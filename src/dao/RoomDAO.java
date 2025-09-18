
package dao;

import models.Room;
import utils.DatabaseInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAO{

    //function to create room

    public boolean createRoom(Room room){

        String insertSql  = "INSERT INTO rooms (roomNumber,roomType,floorNumber,capacity, occupancy,isFull,hostelId) VALUES(?,?,?,?,?,?,?)";
        String checkSql = "SELECT COUNT(*) FROM rooms WHERE roomNumber = ?";

        try(Connection conn = DatabaseInitializer.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql); PreparedStatement insertStmt = conn.prepareStatement(insertSql)){

            checkStmt.setString(1,room.getRoomNumber());
            ResultSet res = checkStmt.executeQuery();
            res.next();
            int count = res.getInt(1);
            if(count>0)
                return false;

            insertStmt.setString(1,room.getRoomNumber());
            insertStmt.setString(2,room.getRoomType());
            insertStmt.setInt(3,room.getFloorNumber());
            insertStmt.setInt(4,room.getCapacity());
            insertStmt.setInt(5,room.getOccupancy());
            insertStmt.setBoolean(6,room.getRoomFull());
            insertStmt.setString(7,room.getHostelId());

            int rowsInserted = insertStmt.executeUpdate();

            if(rowsInserted>0)
                return true;

        }catch (SQLException e){
            System.out.println("Error while creating room : "+e);
        }

        return false;
    }
}