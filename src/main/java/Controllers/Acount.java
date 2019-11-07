package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Acount {

    public static void listAccounts() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Account_ID, User_ID, Username, Points FROM Account");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int Account_ID = results.getInt(1);
                int User_ID = results.getInt(2);
                String Username = results.getString(3);
                String Points = results.getString(4);
                System.out.println(Account_ID + " " + User_ID + " " + Username + " " + Points);
            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertAccount (int Account_ID, int User_ID, String Username, String Points){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Account(Account_ID, User_ID, Username, Points) VALUES (?, ?, ?, ?)");
            ps.setInt(1, Account_ID);
            ps.setInt(2, User_ID);
            ps.setString(3, Username);
            ps.setString(4, Points);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Account' table");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }

    public static void updateAccount (int Account_ID, int User_ID, String Username, String Points){
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Account SET Account_ID = ?, User_ID = ?, Username = ?, Points = ?");
            ps.setInt(1, Account_ID);
            ps.setInt(2, User_ID);
            ps.setString(3, Username);
            ps.setString(4, Points);
            ps.executeUpdate();
            System.out.println("The 'Account' table has been updated");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }

    public static void deleteAccount(int Account_ID) throws SQLException {
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Account WHERE Account_ID = ?");
            ps.setInt(1, Account_ID);
            ps.executeUpdate();

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

}
