package com.dev.servlet;

import com.dev.dao.SoftwareDAO;
import com.dev.model.Software;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/fetchSoftwareDetails")
public class FetchSoftwareDetailsServlet extends HttpServlet {
    private SoftwareDAO softwareDAO = new SoftwareDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int softwareId = Integer.parseInt(request.getParameter("softwareId"));
        Software software = softwareDAO.getSoftwareById(softwareId);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(software);

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}