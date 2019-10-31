package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("users/")
public class Users {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listUsers() {
        System.out.println("Users/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT User_ID, Username, Password FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("User_ID", results.getInt(1));
                item.put("Username", results.getString(2));
                item.put("Password", results.getString(3));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\":  \"Unable to list items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertUser(
            @FormDataParam("User_ID") Integer User_ID, @FormDataParam("Username") String Username, @FormDataParam("Password") String Password){
        try{
            if (User_ID == null || Username == null || Password == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User/new User_ID=" + User_ID);
            System.out.println("User/new Username=" + Username);
            System.out.println("User/new Password=" + Password);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (User_ID, Username, Password) VALUES (?, ?, ?)");
            ps.setInt(1, User_ID);
            ps.setString(2, Username);
            ps.setString(3, Password);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Users' table");
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }

    public static void updateUser (int User_ID, String Username, String Password){
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ?, Password = ? WHERE User_ID = ?");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.setInt(3, User_ID);
            ps.executeUpdate();
            System.out.println("The 'Controllers.Users' table has been updated");

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
