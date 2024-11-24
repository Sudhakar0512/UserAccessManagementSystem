package com.dev.dao;

import com.dev.model.Request;
import com.dev.util.DatabaseUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    public List<Request> getPastRequests() {
        List<Request> pastRequests = new ArrayList<>();

        String query = "SELECT r.id AS request_id, " +
                "u.username AS user_name, " +
                "s.name AS software_name, " +
                "r.access_type, " +
                "r.reason, " +
                "r.status " +
                "FROM requests r " +
                "JOIN users u ON r.user_id = u.id " +
                "JOIN software s ON r.software_id = s.id " +
                "WHERE r.status IN ('Approved', 'Rejected')";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("request_id"));
                request.setUserName(rs.getString("user_name")); // Get the username from the join
                request.setSoftwareName(rs.getString("software_name")); // Get the software name from the join
                request.setAccessType(rs.getString("access_type"));
                request.setReason(rs.getString("reason"));
                request.setStatus(rs.getString("status"));
                pastRequests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pastRequests;
    }

    public List<Request> getUserRequests(int userId) {
        List<Request> userRequests = new ArrayList<>();

        String query = "SELECT r.id AS request_id, " +
                "u.username AS user_name, " +
                "s.name AS software_name, " +
                "s.id as software_id, "+
                "r.access_type, " +
                "r.reason, " +
                "r.status " +
                "FROM requests r " +
                "JOIN users u ON r.user_id = u.id " +
                "JOIN software s ON r.software_id = s.id " +
                "WHERE r.user_id = ? and r.status!='Pending'";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request();
                    request.setId(rs.getInt("request_id"));
                    request.setUserName(rs.getString("user_name")); // Get the username from the join
                    request.setSoftwareName(rs.getString("software_name")); // Get the software name from the join
                    request.setAccessType(rs.getString("access_type"));
                    request.setReason(rs.getString("reason"));
                    request.setStatus(rs.getString("status"));
                    request.setSoftwareId(rs.getInt("software_id"));
                    userRequests.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRequests;
    }

    public List<Request> getPendingRequests() {

        List<Request> pendingRequests = new ArrayList<>();
        String query = "SELECT r.id, r.user_id, r.software_id, r.access_type, r.reason, r.status, u.username AS user_name, s.name AS software_name FROM requests r JOIN users u ON r.user_id = u.id JOIN software s ON r.software_id = s.id WHERE r.status = 'Pending'";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setUserId(rs.getInt("user_id")); // Set user ID
                request.setSoftwareId(rs.getInt("software_id")); // Set software ID
                request.setAccessType(rs.getString("access_type")); // Set access type
                request.setReason(rs.getString("reason")); // Set reason
                request.setStatus(rs.getString("status")); // Set status
                request.setUserName(rs.getString("user_name")); // Set user name
                request.setSoftwareName(rs.getString("software_name")); // Set software name

                pendingRequests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingRequests;
    }

    public boolean updateRequestStatus(int requestId, String status) {
        String query = "UPDATE requests SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, requestId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); //
            return false;
        }
    }

    public boolean createRequest(Request request) {
        String query = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, request.getUserId());
            stmt.setInt(2, request.getSoftwareId());
            stmt.setString(3, request.getAccessType());
            stmt.setString(4, request.getReason());
            stmt.setString(5, request.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}