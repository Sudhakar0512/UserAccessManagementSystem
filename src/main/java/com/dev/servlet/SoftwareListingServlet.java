package com.dev.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dev.dao.SoftwareDAO;
import com.dev.model.Software;

@WebServlet("/jsp/softwareListing")
public class SoftwareListingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Servlet Called!");

        SoftwareDAO softwareDAO = new SoftwareDAO();
        List<Software> softwareList = softwareDAO.getAllSoftware();



        if (softwareList != null && !softwareList.isEmpty()) {
            request.setAttribute("softwareList", softwareList);
        } else {
            request.setAttribute("message", "No software found.");
        }

        request.getRequestDispatcher("softwareListing.jsp").forward(request, response);
    }
}