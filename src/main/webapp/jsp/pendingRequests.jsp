<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dev.model.Request" %>
<%@ page import="com.dev.dao.RequestDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Requests Management</title>
    <link rel="stylesheet" href="../css/pendingRequest.css"> <!-- Link to external CSS file -->
</head>
<body>
    <div class="container">
        <div class="table-container">
            <h2 class="table-title">Pending Access Requests</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Software</th>
                        <th>Access Type</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        RequestDAO requestDAO = new RequestDAO();
                        List<Request> pendingRequests = requestDAO.getPendingRequests(); // Fetch pending requests
                        for (Request req : pendingRequests) {
                            String userName = req.getUserName(); // Correct method to get user name
                            String softwareName = req.getSoftwareName(); // Correct method to get software name
                    %>
                        <tr>
                            <td><%= userName %></td>
                            <td><%= softwareName %></td>
                            <td><%= req.getAccessType() %></td>
                            <td><%= req.getReason() %></td>
                            <td><%= req.getStatus() %></td>
                            <td>
                                <form action="approveRequest" method="POST">
                                    <input type="hidden" name="requestId" value="<%= req.getId() %>">
                                    <button type="submit" name="action" value="approve" class="btn btn-approve">Approve</button>
                                    <button type="submit" name="action" value="reject" class="btn btn-reject">Reject</button>
                                </form>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>

        <div class="table-container">
            <h2 class="table-title">Past Access Requests</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Software</th>
                        <th>Access Type</th>
                        <th>Reason</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Request> pastRequests = requestDAO.getPastRequests(); // Fetch past requests
                        for (Request req : pastRequests) {
                            String userName = req.getUserName(); // Correct method to get user name
                            String softwareName = req.getSoftwareName(); // Correct method to get software name
                            String status = req.getStatus();
                            String statusClass = "status-pending"; // Default class for pending status

                            // Conditional logic to apply green or red for approved or rejected statuses
                            if ("Approved".equalsIgnoreCase(status)) {
                                statusClass = "status-approved";
                            } else if ("Rejected".equalsIgnoreCase(status)) {
                                statusClass = "status-rejected";
                            }
                    %>
                        <tr>
                            <td><%= userName %></td>
                            <td><%= softwareName %></td>
                            <td><%= req.getAccessType() %></td>
                            <td><%= req.getReason() %></td>
                            <td class="<%= statusClass %>"><%= status %></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
