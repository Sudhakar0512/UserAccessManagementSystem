package com.dev.servlet;

import com.dev.dao.RequestDAO;
import com.dev.model.Request;
import com.dev.dao.SoftwareDAO;
import com.dev.model.Software;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/jsp/requestAccess")
public class RequestServlet extends HttpServlet {
    private RequestDAO requestDAO = new RequestDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Retrieve userId from session

        // Check if userId is null
        if (userId == null) {
            response.sendRedirect("login.jsp"); // Redirect to login page if userId is not found
            return;
        }

        int softwareId = Integer.parseInt(request.getParameter("softwareId"));
        String accessType = request.getParameter("accessType");
        String reason = request.getParameter("reason");

        Request accessRequest = new Request();
        accessRequest.setUserId(userId);
        accessRequest.setSoftwareId(softwareId);
        accessRequest.setAccessType(accessType);
        accessRequest.setReason(reason);
        accessRequest.setStatus("Pending");

        if (requestDAO.createRequest(accessRequest)) {
            response.sendRedirect("requestAccess");
        } else {
            response.getWriter().write("Error during access request. Try again.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            List<Request> userRequests = requestDAO.getUserRequests(userId);
            List<Software> softwareDropDown = SoftwareDAO.getSoftwareUserById(userId); // Updated method call
            request.setAttribute("userRequests", userRequests);
            request.setAttribute("softwareDropDown", softwareDropDown);
        }



        request.getRequestDispatcher("/jsp/requestAccess.jsp").forward(request, response);
    }



}


