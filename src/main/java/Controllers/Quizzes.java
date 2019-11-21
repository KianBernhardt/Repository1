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

@Path("Quizzes/")
public class Quizzes {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuizzes() {
        System.out.println("Quizzes/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Quiz_ID, Quiz_Name, Quiz_Desc, Total_Score FROM Quizzes");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Quiz_ID", results.getInt(1));
                item.put("Quiz_Name", results.getString(2));
                item.put("Quiz_Desc", results.getString(3));
                item.put("Total_Score", results.getInt(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list Quizzes, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertQuiz(
            @FormDataParam("Quiz_Name") String Quiz_Name, @FormDataParam("Quiz_Desc") String Quiz_Desc, @FormDataParam("Total_Score") Integer Total_Score) {
        try {
            if (Quiz_Name == null || Quiz_Desc == null || Total_Score == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Questions/new Quiz_Name=" + Quiz_Name);
            System.out.println("Questions/new Quiz_Desc=" + Quiz_Desc);
            System.out.println("Questions/new Total_Score=" + Total_Score);


            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Quizzes (Quiz_Name, Quiz_Desc, Total_Score) VALUES (?, ?, ?)");
            ps.setString(1, Quiz_Name);
            ps.setString(2, Quiz_Desc);
            ps.setInt(3, Total_Score);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Quizzes' table");
            return "{\"Status\": \"OK\"}";

        } catch (
                Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add quiz, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateQuiz(
            @FormDataParam("Quiz_Name") String Quiz_Name, @FormDataParam("Quiz_Desc") String Quiz_Desc, @FormDataParam("Total_Score") Integer Total_Score, @FormDataParam("Quiz_ID") Integer Quiz_ID) {
        try {
            if ((Quiz_Name == null) || (Quiz_Desc == null) || (Total_Score == null) || (Quiz_ID == null)) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Quiz/update Quiz_Name=" + Quiz_Name);
            System.out.println("Quiz/update Quiz_Desc=" + Quiz_Desc);
            System.out.println("Quiz/update Total_Score=" + Total_Score);
            System.out.println("Quiz/update Quiz_ID=" + Quiz_ID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Quizzes SET Quiz_Name = ?, Quiz_Desc = ?, Total_Score = ? WHERE Quiz_ID = ?");
            ps.setString(1, Quiz_Name);
            ps.setString(2, Quiz_Desc);
            ps.setInt(3, Total_Score);
            ps.setInt(4, Quiz_ID);
            ps.executeUpdate();
            System.out.println("Record has been updated.");
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update quiz, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteQuiz(@FormDataParam("Quiz_ID") Integer Quiz_ID) {
        try {
            if (Quiz_ID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User'delete Question=" + Quiz_ID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Quizzes WHERE Quiz_ID = ?");
            ps.setInt(1, Quiz_ID);
            ps.execute();
            return "{\"status\": \"OK\"\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete quiz, please see server console for more info.\"}";


        }

    }
}