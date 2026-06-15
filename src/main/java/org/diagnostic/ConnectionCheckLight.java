package org.diagnostic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionCheckLight {

    public static void main(String[] args) {
        checkDatabase();
    }

    public static void checkDatabase() {
        String url = "jdbc:sqlserver://185.119.119.126:1433;databaseName=ben;encrypt=true;trustServerCertificate=true";
        String user = "ben";
        String password = "ben";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connection OK");

            // Retrieve and display all available tables in the database
            ResultSet rs = con.getMetaData().getTables(con.getCatalog(), null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_SCHEM") + "." + rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
