package com.example.controller;

import com.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private final UserService userService;

    public RegisterServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader bufferedReader = request.getReader();

        StringBuilder json = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            json.append(line);
        }

        String jsonString = json.toString();

        String name = extractJsonValue(jsonString, "name");
        String email = extractJsonValue(jsonString, "email");
        String password = extractJsonValue(jsonString, "password");

        try {
            userService.registerUser(name, email, password);
            sendJson(response, "{\"message\":\"Successful registration!, " + name + "!\"}", 201);
        } catch (SQLException | ClassNotFoundException e) {
            sendJson(response, "{\"message\":\"Internal Server Error.\"}", 500);
        }
    }

    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex == -1) {
            return "";
        }

        int colonIndex = json.indexOf(":", keyIndex);
        if (colonIndex == -1) {
            return "";
        }

        int startQuote = json.indexOf("\"", colonIndex + 1);
        int endQuote = json.indexOf("\"", startQuote + 1);

        if (startQuote == -1 || endQuote == -1) {
            return "";
        }

        return json.substring(startQuote + 1, endQuote);
    }

    private void sendJson(HttpServletResponse response, String json, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}