import org.sqlite.SQLiteConfig;

import java.sql.*;

public class Main {
    public static Connection db = null;

    public static void main(String[] args) throws SQLException {
        openDatabase("Database.db");

        Users.listUsers();
        deleteUser(2);

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

    public static void insertUser (int User_ID, String Username){
        try{
            PreparedStatement ps = db.prepareStatement("INSERT INTO Users (User_ID, Username) VALUES (?, ?)");

            ps.setInt(1, User_ID);
            ps.setString(2, Username);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Users' table");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }

    public static void listUsers() {
        try {
            PreparedStatement ps = db.prepareStatement("SELECT User_ID, Username, Password FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int User_ID = results.getInt(1);
                String Username = results.getString(2);
                String Password = results.getString(3);
                System.out.println(User_ID + " " + Username + " " + Password);
            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void deleteUser(int User_ID) throws SQLException {
        try{
            PreparedStatement ps = db.prepareStatement("DELETE FROM Users WHERE User_ID = ?");
            ps.setInt(1, User_ID);

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public static void updateUser (String Username, String Password){
        try{
            PreparedStatement ps = db.prepareStatement("UPDATE Users SET Username = ?, Password = ?");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.executeUpdate();
            System.out.println("The 'Users' table has been updated");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }
}

