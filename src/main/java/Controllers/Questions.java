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

@Path("Questions/")
public class Questions {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuestions() {
        System.out.println("Questions/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Question_ID, Quiz_ID, Question, Difficulty FROM Questions");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Question_ID", results.getInt(1));
                item.put("Quiz_ID", results.getInt(2));
                item.put("Question", results.getString(3));
                item.put("Difficulty", results.getString(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list Questions, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertQuestion(
            @FormDataParam("Quiz_ID") Integer Quiz_ID, @FormDataParam("Question") String Question, @FormDataParam("Difficulty") String Difficulty) {
        try {
            if (Quiz_ID == null || Question == null || Difficulty == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Questions/new Quiz_ID=" + Quiz_ID);
            System.out.println("Questions/new Question=" + Question);
            System.out.println("Questions/new Difficulty=" + Difficulty);


            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Questions (Quiz_ID, Question, Difficulty) VALUES (?, ?, ?)");
            ps.setInt(1, Quiz_ID);
            ps.setString(2, Question);
            ps.setString(3, Difficulty);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Questions' table");
            return "{\"Status\": \"OK\"}";

        } catch (
                Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add question, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateQuestion(
            @FormDataParam("Quiz_ID") Integer Quiz_ID, @FormDataParam("Question") String Question, @FormDataParam("Difficulty") String Difficulty, @FormDataParam("Question_ID") Integer Question_ID) {
        try {
            if ((Quiz_ID == null) || (Question == null) || (Difficulty == null) || (Question_ID == null)) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User/update Quiz_ID=" + Quiz_ID);
            System.out.println("User/update Question=" + Question);
            System.out.println("User/update Difficulty=" + Difficulty);
            System.out.println("User/update Question_ID=" + Question_ID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Questions SET Quiz_ID = ?, Question = ?, Difficulty = ? WHERE Question_ID = ?");
            ps.setInt(1, Quiz_ID);
            ps.setString(2, Question);
            ps.setString(3, Difficulty);
            ps.setInt(4, Question_ID);
            ps.executeUpdate();
            System.out.println("Record has been updated.");
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update question, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteQuestion(@FormDataParam("Question_ID") Integer Question_ID) {
        try {
            if (Question_ID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User'delete Question=" + Question_ID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Questions WHERE Question_ID = ?");
            ps.setInt(1, Question_ID);
            ps.execute();
            return "{\"status\": \"OK\"\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete question, please see server console for more info.\"}";


        }

    }
}
