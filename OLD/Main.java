import org.sqlite.SQLiteConfig;

import java.sql.*;

public class Main {
    public static Connection db = null;

    public static void main(String[] args) throws SQLException {
        openDatabase("Database.db");

        Users.listUsers();

        System.out.println("Adding user...");
        Users.insertUser(10, "Steve", "Beans");
        Users.listUsers();

        System.out.println("Updating user...");
        Users.updateUser(10, "SuperSteve", "Beans123");
        Users.listUsers();

        System.out.println("Deleting user...");
        Users.deleteUser(10);
        Users.listUsers();

        closeDatabase();
    }


    private static void openDatabase(String dbFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }
    }

    private static void closeDatabase() {
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }


}

