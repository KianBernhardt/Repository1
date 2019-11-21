package Controllers;

import Server.Main;
import org.eclipse.jetty.server.Authentication;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("Scores/")
public class Scores {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listScores() {
        System.out.println("Scores/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT User_ID, Quiz_ID, Score, Score_ID FROM Scores");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("User_ID", results.getInt(1));
                item.put("Quiz_ID", results.getInt(2));
                item.put("Score", results.getInt(3));
                item.put("Score_ID", results.getInt(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list Scores, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertScore(
            @FormDataParam("User_ID") Integer User_ID, @FormDataParam("Quiz_ID") Integer Quiz_ID, @FormDataParam("Score") Integer Score) {
        try {
            if (User_ID == null || Quiz_ID == null || Score == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Scores/new User_ID=" + User_ID);
            System.out.println("Scoers/new Quiz_ID=" + Quiz_ID);
            System.out.println("Scores/new Score=" + Score);


            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Scores (User_ID, Quiz_ID, Score) VALUES (?, ?, ?)");
            ps.setInt(1, User_ID);
            ps.setInt(2, Quiz_ID);
            ps.setInt(3, Score);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Scores' table");
            return "{\"Status\": \"OK\"}";

        } catch (
                Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add score, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateScore(
            @FormDataParam("User_ID") Integer User_ID, @FormDataParam("Quiz_ID") Integer Quiz_ID, @FormDataParam("Score") Integer Score, @FormDataParam("Score_ID") Integer Score_ID) {
        try {
            if ((User_ID == null) || (Quiz_ID == null) || (Score == null) || (Score_ID == null)) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Score/update User_ID=" + User_ID);
            System.out.println("Score/update Quiz_ID=" + Quiz_ID);
            System.out.println("Score/update Answer=" + Score);
            System.out.println("Score/update Answer=" + Score_ID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Scores SET User_ID = ?, Quiz_ID = ?, Score = ? WHERE Score_ID = ?");
            ps.setInt(1, User_ID);
            ps.setInt(2, Quiz_ID);
            ps.setInt(3, Score);
            ps.setInt(4, Score_ID);
            ps.executeUpdate();
            System.out.println("Record has been updated.");
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update score, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteScore(@FormDataParam("Score_ID") Integer Score_ID) {
        try {
            if (Score_ID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User'delete Score=" + Score_ID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Scores WHERE Score_ID = ?");
            ps.setInt(1, Score_ID);
            ps.execute();
            return "{\"status\": \"OK\"\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete score, please see server console for more info.\"}";


        }

    }
}
