package com.dev.servlet;

import com.dev.dao.UserDAO;
import com.dev.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jsp/signup")
public class SignUpServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match. Please try again.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if (userDAO.checkUsernameExists(username)) {
            request.setAttribute("errorMessage", "Username already exists. Please choose a different one.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        User newUser = new User(username, password);
        boolean userCreated = userDAO.createUser(newUser);

        if (userCreated) {
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("errorMessage", "An error occurred during sign-up. Please try again.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}
