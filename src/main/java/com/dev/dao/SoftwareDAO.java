package com.dev.dao;

import com.dev.model.Software;
import com.dev.util.DatabaseUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoftwareDAO {

    public List<Software> getAllSoftware() {
        List<Software> softwareList = new ArrayList<>();
        String query = "SELECT id, name, description, access_levels FROM software";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Software software = new Software();
                software.setId(rs.getInt("id"));
                software.setName(rs.getString("name"));
                software.setDescription(rs.getString("description"));
                software.setAccessLevels(rs.getString("access_levels"));
                softwareList.add(software);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return softwareList;
    }

    public Software getSoftwareById(int softwareId) {
        Software software = null;
        String query = "SELECT id, name, description, access_levels FROM software WHERE id = ?";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, softwareId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    software = new Software();
                    software.setId(rs.getInt("id"));
                    software.setName(rs.getString("name"));
                    software.setDescription(rs.getString("description"));
                    software.setAccessLevels(rs.getString("access_levels"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return software;
    }


    public boolean addSoftware(Software software) {
        String query = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, software.getName());
            stmt.setString(2, software.getDescription());
            stmt.setString(3, software.getAccessLevels());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

public boolean updateSoftwareDescription(int softwareId, String newDescription) {
    String query = "UPDATE software SET description = ? WHERE id = ?";

    try (Connection conn = DatabaseUtility.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, newDescription);
        stmt.setInt(2, softwareId);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



public static List<Software> getSoftwareUserById(int userId) {
        List<Software> softwareList = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.description, s.access_levels " +
                "FROM software s " +
                "LEFT JOIN requests r ON s.id = r.software_id AND r.user_id = ? " +
                "WHERE r.id IS NULL";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the userId parameter
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Software software = new Software();
                    software.setId(rs.getInt("id"));
                    software.setName(rs.getString("name"));
                    software.setDescription(rs.getString("description"));
                    software.setAccessLevels(rs.getString("access_levels"));
                    softwareList.add(software);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return softwareList;
    }
}