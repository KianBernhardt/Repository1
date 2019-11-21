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

@Path("Answers/")
public class Answers {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listAnswers() {
        System.out.println("Answers/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Answer_ID, Question_ID, Correct, Answer FROM Answers");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("Answer_ID", results.getInt(1));
                item.put("Question_ID", results.getInt(2));
                item.put("Correct", results.getBoolean(3));
                item.put("Answer", results.getString(4));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list Answers, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertAnswer(
            @FormDataParam("Question_ID") Integer Question_ID, @FormDataParam("Correct") Boolean Correct, @FormDataParam("Answer") String Answer) {
        try {
            if (Question_ID == null || Correct == null || Answer == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Answers/new Question_ID=" + Question_ID);
            System.out.println("Answers/new Correct=" + Correct);
            System.out.println("Answers/new Answer=" + Answer);


            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Answers (Question_ID, Correct, Answer) VALUES (?, ?, ?)");
            ps.setInt(1, Question_ID);
            ps.setBoolean(2, Correct);
            ps.setString(3, Answer);
            ps.executeUpdate();
            System.out.println("Record has been added to the 'Answers' table");
            return "{\"Status\": \"OK\"}";

        } catch (
                Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add answer, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateAnswer(
            @FormDataParam("Question_ID") Integer Question_ID, @FormDataParam("Correct") Boolean Correct, @FormDataParam("Answer") String Answer, @FormDataParam("Answer_ID") Integer Answer_ID) {
        try {
            if ((Question_ID == null) || (Correct == null) || (Answer == null) || (Answer_ID == null)) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Quiz/update Question_ID=" + Question_ID);
            System.out.println("Quiz/update Correct=" + Correct);
            System.out.println("Quiz/update Answer=" + Answer);
            System.out.println("Quiz/update Answer=" + Answer_ID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Answers SET Question_ID = ?, Correct = ?, Answer = ? WHERE Answer_ID = ?");
            ps.setInt(1, Question_ID);
            ps.setBoolean(2, Correct);
            ps.setString(3, Answer);
            ps.setInt(4, Answer_ID);
            ps.executeUpdate();
            System.out.println("Record has been updated.");
            return "{\"Status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update answer, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAnswer(@FormDataParam("Answer_ID") Integer Answer_ID) {
        try {
            if (Answer_ID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("User'delete Question=" + Answer_ID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Answers WHERE Answer_ID = ?");
            ps.setInt(1, Answer_ID);
            ps.execute();
            return "{\"status\": \"OK\"\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to delete answer, please see server console for more info.\"}";


        }

    }
}