package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Questions {

    public static void listQuestions() {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT Question_ID, Topic_ID, Text, Question_Type, Difficulty FROM Question");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int Question_ID = results.getInt(1);
                int Topic_ID = results.getInt(2);
                String Text = results.getString(3);
                String Question_Type = results.getString(4);
                String Difficulty = results.getString(5);
                System.out.println(Question_ID + " " + Topic_ID + " " + Text + " " + Question_Type + " " + Difficulty);
            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertQuestion (int Question_ID, String Topic_ID, String Text, String Question_Type, String Difficulty){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Question(Question_ID, Topic_ID, Text, Question_Type, Difficulty ) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, Question_ID);
            ps.setInt(2, Integer.parseInt(Topic_ID));
            ps.setString(3, Text);
            ps.setString(4, Question_Type);
            ps.setString(5, Difficulty);

            ps.executeUpdate();
            System.out.println("Record has been added to the 'Question' table");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }

    public static void updateQuestion (int Question_ID, String Topic_ID, String Text, String Question_Type, String Difficulty){
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Question SET Question_ID = ?, Topic_ID = ?, Text = ?, Question_Type = ?, Difficulty = ?");
            ps.setInt(1, Question_ID);
            ps.setInt(2, Integer.parseInt(Topic_ID));
            ps.setString(3, Text);
            ps.setString(4, Question_Type);
            ps.setString(5, Difficulty);
            ps.executeUpdate();
            System.out.println("The 'Question' table has been updated");

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something has gone wrong.");
        }
    }

    public static void deleteQuestion(int Question_ID) throws SQLException {
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Question WHERE Question_ID = ?");
            ps.setInt(1, Question_ID);
            ps.executeUpdate();

        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

}
