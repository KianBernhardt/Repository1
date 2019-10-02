import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {

    public static void listLogin() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT User_ID, Username, Password FROM Users");

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

    public static void insertUser (int User_ID, String Username, String Password){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users(User_ID, Username, Password) VALUES (?, ?, ?)");
            ps.setInt(1, User_ID);
            ps.setString(2, Username);
            ps.setString(3, Password);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Users' table");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }

    public static void updateUser (int User_ID, String Username, String Password){
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET User_ID = ?, Username = ?, Password = ?");
            ps.setInt(1, User_ID);
            ps.setString(2, Username);
            ps.setString(3, Password);
            ps.executeUpdate();
            System.out.println("The 'Users' table has been updated");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }

    public static void deleteUser(int User_ID) throws SQLException {
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE User_ID = ?");
            ps.setInt(1, User_ID);
            ps.executeUpdate();

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

}
