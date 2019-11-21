package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("Users/")
public class Users {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listUsers() {
        System.out.println("Users/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT User_ID, Username, Password, Email, Admin FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("User_ID", results.getInt(1));
                item.put("Username", results.getString(2));
                item.put("Password", results.getString(3));
                item.put("Email", results.getString(4));
                item.put("Admin", results.getBoolean(5));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list Users, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertUser(
            @FormDataParam("User_ID") Integer User_ID, @FormDataParam("Username") String Username, @FormDataParam("Password") String Password, @FormDataParam("Email") String Email, @FormDataParam("Admin") Boolean Admin) {
        try {
            if (User_ID == null || Username == null || Password == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User/new User_ID=" + User_ID);
            System.out.println("User/new Username=" + Username);
            System.out.println("User/new Password=" + Password);
            System.out.println("User/new Email=" + Email);
            System.out.println("User/new Is Admin=" + Admin);

            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (User_ID, Username, Password, Email, Admin) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, User_ID);
            ps.setString(2, Username);
            ps.setString(3, Password);
            ps.setString(4, Email);
            ps.setBoolean(5, Admin);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Users' table");
            return "{\"Status\": \"OK\"}";

        } catch (
                Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add user, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(
            @FormDataParam("Username") String Username, @FormDataParam("Password") String Password, @FormDataParam("Email") String Email, @FormDataParam("Admin") Boolean Admin, @FormDataParam("User_ID") Integer User_ID) {
        try {
            if ((Username == null) || (Password == null) || (User_ID == null)) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User/update User_ID=" + User_ID);
            System.out.println("User/update Username=" + Username);
            System.out.println("User/update Password=" + Password);
            System.out.println("User/update Email=" + Email);
            System.out.println("User/update Is Admin=" + Admin);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ?, Password = ?, Email = ?, Admin = ? WHERE User_ID = ?");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.setString(3, Email);
            ps.setBoolean(4, Admin);
            ps.setInt(5, User_ID);
            ps.executeUpdate();
            System.out.println("Record has been updated.");
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update user, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@FormDataParam("User_ID") Integer User_ID) {
        try {
            if (User_ID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User'delete User_ID=" + User_ID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE User_ID = ?");
            ps.setInt(1, User_ID);
            ps.execute();
            return "{\"status\": \"OK\"\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete user, please see server console for more info.\"}";


        }

    }
}
