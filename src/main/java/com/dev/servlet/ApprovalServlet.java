package com.dev.servlet;

import com.dev.dao.RequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jsp/approveRequest")
public class ApprovalServlet extends HttpServlet {
    private RequestDAO requestDAO = new RequestDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String action = request.getParameter("action"); // "approve" or "reject"

        if (requestDAO.updateRequestStatus(requestId, action.equals("approve") ? "Approved" : "Rejected")) {
            response.sendRedirect("pendingRequests.jsp");
        } else {
            response.getWriter().write("Error updating request status.");
        }
    }
}