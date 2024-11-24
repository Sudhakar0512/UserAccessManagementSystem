package com.dev.servlet;

import com.dev.dao.SoftwareDAO;
import com.dev.model.Software;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jsp/createSoftware")
public class CreateSoftwareServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String version = request.getParameter("version");
        String accessLevels = request.getParameter("accessLevels");

        Software software = new Software();
        software.setName(name);
        software.setDescription(description);
        software.setAccessLevels(accessLevels);

        SoftwareDAO softwareDAO = new SoftwareDAO();
        boolean isCreated = softwareDAO.addSoftware(software);

        if (isCreated) {
            response.sendRedirect("softwareListing");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create software");
        }
    }
}
