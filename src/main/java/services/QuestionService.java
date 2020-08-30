package main.java.services;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import main.java.models.Question;

/**
 *  Service class for performing CRUD operations on 'questions' table.
 */
public class QuestionService {
  private Properties properties;

  private Connection connection;
  private PreparedStatement statement;

  private ResultSet resultSet;

  public QuestionService() {
    properties = new Properties();
    try {
      properties.load(new FileReader("app.properties"));
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
        DriverManager.getConnection(
          properties.getProperty("dbURI"),
          properties.getProperty("username"),
          properties.getProperty("password")
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the number of questions in the database.
   */
  public int getQuestionsCount() {
    int count = 0;
    try {
      statement =
        connection.prepareStatement("SELECT COUNT(id) FROM questions");
      resultSet = statement.executeQuery();
      resultSet.next();
      count = resultSet.getInt(1);
    } catch (Exception e) {}
    return count;
  }

  /**
   * Add new question to the database.
   */
  public boolean addQuestion(Question question) {
    try {
      statement =
        connection.prepareStatement(
          "INSERT INTO questions VALUES(null,?,?,?,?,?,?)"
        );
      statement.setString(1, question.getQuestion());
      statement.setString(2, question.getOptionA());
      statement.setString(3, question.getOptionB());
      statement.setString(4, question.getOptionC());
      statement.setString(5, question.getOptionD());
      statement.setString(6, question.getcorrectAnswer());
      if (statement.executeUpdate() > 0) return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Return the list of all questions in the database.
   */
  public List<Question> getAllQuestions() {
    List<Question> questions = new ArrayList<>();
    Question question;
    try {
      statement = connection.prepareStatement("SELECT * FROM questions");
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        question =
          new Question(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3),
            resultSet.getString(4),
            resultSet.getString(5),
            resultSet.getString(6),
            resultSet.getString(7)
          );
        questions.add(question);
      }
    } catch (SQLException sqlException) {}
    return questions;
  }

  /**
   * Return the question with specified ID from the database.
   */
  public Question getQuestionById(int id) {
    Question question = null;
    try {
      statement =
        connection.prepareStatement("SELECT * FROM questions WHERE id=?");
      statement.setInt(1, id);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        question =
          new Question(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3),
            resultSet.getString(4),
            resultSet.getString(5),
            resultSet.getString(6),
            resultSet.getString(7)
          );
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    return question;
  }

  /**
   * Update the specified question from the database.
   */
  public boolean updatequestion(Question question) {
    try {
      statement =
        connection.prepareStatement(
          "UPDATE questions SET question=?, A=?, B=?, C=?, D=?, answer=? WHERE id=?"
        );
      statement.setString(1, question.getQuestion());
      statement.setString(2, question.getOptionA());
      statement.setString(3, question.getOptionB());
      statement.setString(4, question.getOptionC());
      statement.setString(5, question.getOptionD());
      statement.setString(6, question.getcorrectAnswer());
      statement.setInt(7, question.getId());
      if (statement.executeUpdate() > 0) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Delete the specified question from the database.
   */
  public boolean deleteQuestionById(int id) {
    try {
      statement =
        connection.prepareStatement("DELETE FROM questions WHERE id=?");
      statement.setInt(1, id);
      if (statement.executeUpdate() > 0) return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Get the questions with specified IDs from the database.
   */
  public List<Question> getQuestionsFromIds(List<Integer> ids) {
    List<Question> questions = new ArrayList<>();
    String idList = "";
    for (int id : ids) {
      idList += Integer.toString(id) + ",";
    }
    idList = idList.substring(0, idList.length() - 1);
    try {
      statement =
        connection.prepareStatement(
          "SELECT * FROM questions WHERE ID in (" + idList + ")"
        );
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        questions.add(
          new Question(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3),
            resultSet.getString(4),
            resultSet.getString(5),
            resultSet.getString(6),
            resultSet.getString(7)
          )
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return questions;
  }
}
