package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.DatabaseModel;

import java.io.IOException;
import java.sql.*;

@WebServlet("/CheckForCardInfo")
public class CheckForCardInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("email");
        DatabaseModel dbModel = new DatabaseModel();
        boolean cardExists = dbModel.hasCardInfo(email);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"cardExists\":" + cardExists + "}");
    }
}