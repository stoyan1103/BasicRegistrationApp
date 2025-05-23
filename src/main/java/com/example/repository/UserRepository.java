package com.example.repository;

import com.example.db.DBConnection;
import com.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepository {

    public User save(User user) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (id, name, email, password) VALUES (?, ?, ?, ?)");
        ps.setString(1, user.getId().toString());
        ps.setString(2, user.getName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());

        return user;
    }

    public boolean existsByEmail(String email) throws SQLException, ClassNotFoundException{
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?");
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt(1) > 0;
    }

    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password")); // Хеширана парола
                return user;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Invalid email.");
        }
        return null;
    }


}