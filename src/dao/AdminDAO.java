package dao;

import models.Admin;
import utils.DatabaseInitializer;

import java.sql.*;

public class AdminDAO
{
    // 🔹 Admin Login
    public Admin login(String username, String password)
    {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        Admin admin = null;

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
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
    public boolean logout(int adminId)
    {
        // Example implementation: mark admin as logged out (if tracking session)
        String query = "UPDATE admins SET isLoggedIn = FALSE WHERE adminId = ?";

        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, adminId);
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
