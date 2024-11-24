package com.dev.servlet;
import com.dev.dao.UserDAO;
import com.dev.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/jsp/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.authenticateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Store the User object
            session.setAttribute("userId", user.getId()); // Store the userId for later use

            // Redirect based on user role
            switch (user.getRole()) {
                case "Admin":
                    response.sendRedirect("softwareListing");
                    break;
                case "Manager":
                    response.sendRedirect("pendingRequests.jsp");
                    break;
                case "Employee":
                    response.sendRedirect("requestAccess");
                    break;
                default:
                    response.sendRedirect("defaultPage.jsp"); // Handle unexpected roles
                    break;
            }
        } else {
            // Set the error message and forward back to login.jsp only if no redirect has occurred
            request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
