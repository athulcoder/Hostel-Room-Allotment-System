package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:hostel.db";

    // Call this once at app startup
    public static void initializeDatabase() {

        System.out.print(DB_URL);

        createStudentTable();
        createRoomTable();



    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
        
    }


    private static void createStudentTable(){
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
                dateOfAdmission TEXT
            );
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println(" Database initialized and table created for students.");

        } catch (SQLException e) {
            System.err.println(" DB Error: " + e.getMessage());
        }

    }

    private static void createRoomTable(){
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

        try(Connection conn = DriverManager.getConnection(DB_URL);Statement stmt = conn.createStatement();){
            stmt.execute(sql);
            System.out.println(" Database initialized and table created for rooms.");

        }catch (SQLException e){
            System.err.println("DB Room table error :"+e.getMessage());
        }
    }

}
