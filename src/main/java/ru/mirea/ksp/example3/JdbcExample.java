package ru.mirea.ksp.example3;

import java.sql.*;

public class JdbcExample {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/example_db")) {
            try (PreparedStatement ps = connection.prepareStatement(
                "create table if not exists site_users (" +
                "  id int primary key," +
                "  login varchar(20) not null," +
                "  display_name varchar(100) not null" +
                ")"
            )) {
                ps.execute();
            }

            try (PreparedStatement ps = connection.prepareStatement(
                "insert into site_users (id, login, display_name) values (?, ?, ?)"
            )) {
                ps.setInt(1, 123);
                ps.setString(2, "ivan");
                ps.setString(3, "Иванов Иван Иванович");
                ps.executeUpdate();
            }

            try (PreparedStatement ps = connection.prepareStatement(
                "select id, display_name from site_users where login = ?"
            )) {
                ps.setString(1, "ivan");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String displayName = rs.getString(2);
                        System.out.println(id + ": " + displayName);
                    }
                }
            }
        }
    }
}
