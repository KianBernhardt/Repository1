import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Users {

    public static void listUsers() {
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

}