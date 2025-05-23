package com.example.controller;

import com.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        String email = extractJsonValue(json.toString(), "email");
        String password = extractJsonValue(json.toString(), "password");

        try {
            boolean loggedIn = userService.login(email, password);
            if (loggedIn) {
                sendJson(response, "{\"message\":\"Successful login!\"}", 200);
            } else {
                sendJson(response, "{\"message\":\"Invalid password!\"}", 401);
            }
        } catch (Exception e) {
            sendJson(response, "{\"message\":\"Internal Server Error!\"}", 500);
        }
    }

    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex == -1) return "";
        int colonIndex = json.indexOf(":", keyIndex);
        int startQuote = json.indexOf("\"", colonIndex + 1);
        int endQuote = json.indexOf("\"", startQuote + 1);
        return (startQuote != -1 && endQuote != -1) ?
                json.substring(startQuote + 1, endQuote) : "";
    }

    private void sendJson(HttpServletResponse response, String json, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}