package dao;

import models.Admin;
import utils.DatabaseInitializer;
import utils.SessionManager;

import java.sql.*;
import java.time.LocalDateTime;

public class AdminDAO
{
    // 🔹 Admin Login
    public Admin login(String username, String password)
    {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        String sql = "UPDATE admins SET lastLoginTime = ?, isActive = ? WHERE username =?  ";

        Admin admin = null;

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query); PreparedStatement updateStmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();




            if (rs.next())
            {
                admin = new Admin(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("phoneNumber"),
                        rs.getString("hostelId")
                );

                admin.setLastLoginTime(LocalDateTime.now());
                updateStmt.setString(1,admin.getLastLoginTime().toString());
                updateStmt.setBoolean(2,true);
                updateStmt.setString(3,admin.getUsername());
                int c = updateStmt.executeUpdate();
                admin.setIsActive(true);
                SessionManager.login(admin);
            }



        }
        catch (SQLException e)
        {
            System.out.println("Error while logging in Admin : " + e.getMessage());
        }

        return admin;
    }

    // 🔹 Admin Signup
    public boolean signup(Admin admin)
    {
        String query = "INSERT INTO admins (username, password, name, role, phoneNumber, hostelId) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getRole());
            stmt.setString(5, admin.getPhoneNumber());
            stmt.setString(6, admin.getHostelId());

            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Error while signing up Admin : " + e.getMessage());
            return false;
        }
    }

    // 🔹 Admin Logout
    public boolean logout(String username)
    {
        // Example implementation: mark admin as logged out (if tracking session)
        String query = "UPDATE admins SET isActive = 0 WHERE username = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, username);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Error while logging out Admin : " + e.getMessage());
            return false;
        }
    }
}
