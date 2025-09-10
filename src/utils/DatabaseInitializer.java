package utils;

import java.sql.*;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:hostel.db";

    // Call this once at app startup
    public static void initializeDatabase() {

        System.out.print(DB_URL);

        createStudentTable();
        createRoomTable();


    }


    //This function returns a connection from the database
    // .Since Sqlite is our database to make references from
    // one table we have to execute a sql statement to enable the foreign keys
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        try(Statement stmt = conn.createStatement()){
            stmt.execute("PRAGMA foreign_keys = ON;");
        }catch (SQLException e){
            System.out.println("DB Connection error : "+e);
        }

        return conn;
    }



    private static void createStudentTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS students (
                studentId TEXT PRIMARY KEY,
                name TEXT,
                gender TEXT,
                age INTEGER,
                department TEXT,
                academicYear TEXT,
                contactNumber TEXT,
                email TEXT,
                guardianName TEXT,
                guardianPhone TEXT,
                preferredRoomType TEXT,
                assignedRoom TEXT,
                sleepType TEXT,
                dateOfAdmission TEXT,
                hostelId TEXT,
                FOREIGN KEY (hostelId) REFERENCES hostels(hostelId)
            );
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println(" Database initialized and table created for students. ");

        } catch (SQLException e) {
            System.err.println(" DB Error: " + e.getMessage());
        }

    }

    private static void createRoomTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS rooms (
                roomNumber TEXT PRIMARY KEY,
                roomType TEXT ,
                floorNumber INTEGER,
                capacity INTEGER,
                occupancy INTEGER,
                isFull BOOLEAN
                )
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement();) {
            stmt.execute(sql);
            System.out.println(" Database initialized and table created for rooms.");

        } catch (SQLException e) {
            System.err.println("DB Room table Error : " + e.getMessage());
        }




    }

}
