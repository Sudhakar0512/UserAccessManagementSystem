package com.dev.servlet;

import com.dev.dao.SoftwareDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jsp/updateSoftwareDescription")
public class UpdateSoftwareDescriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        response.getWriter().println("<h1>Hello, this is a simple GET request response!</h1>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String softwareIdStr = request.getParameter("softwareId");
        String newDescription = request.getParameter("newDescription");

        System.out.println("Received Parameters:");
        System.out.println("Software ID: " + softwareIdStr);
        System.out.println("New Description: " + newDescription);

        if (softwareIdStr == null || softwareIdStr.isEmpty() || newDescription == null || newDescription.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            response.getWriter().write("Both software ID and description are required.");
            return;
        }

        try {
            int softwareId = Integer.parseInt(softwareIdStr);

            SoftwareDAO dao = new SoftwareDAO();
            boolean isUpdated = dao.updateSoftwareDescription(softwareId, newDescription);

            if (isUpdated) {
                response.setStatus(HttpServletResponse.SC_OK); // 200
                response.getWriter().write("Description updated successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                response.getWriter().write("Error updating description.");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            response.getWriter().write("Invalid software ID.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            response.getWriter().write("An unexpected error occurred.");
            e.printStackTrace();
        }
    }
}
