package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import main.java.models.Question;
import main.java.services.QuestionService;
import org.junit.jupiter.api.Test;

public class QuestionServiceTest {

  @Test
  void testGetQuestionsCount() {
    QuestionService qService = new QuestionService();
    assertEquals(
      404,
      qService.getQuestionsCount(),
      "Shoud return count of questions in the database."
    );
  }

  @Test
  void testGetAllQuestions() {
    QuestionService qService = new QuestionService();
    assertEquals(
      qService.getQuestionsCount(),
      qService.getAllQuestions().size(),
      "Should return list of all Questions."
    );
  }

  @Test
  void testGetQuestion() {
    QuestionService qService = new QuestionService();
    assertEquals(
      "According to a famous line from the existentialist play 'No Exit' what is hell?",
      qService.getQuestionById(1).getQuestion(),
      "Should return question specified by ID"
    );
  }

  @Test
  void testUpdateQuestion() {
    QuestionService qService = new QuestionService();
    Question question = qService.getQuestionById(1);
    question.setcorrectAnswer("B");
    if (!qService.updatequestion(question)) {
      fail("Could not update question");
      return;
    }
    assertEquals("B", qService.getQuestionById(1).getcorrectAnswer());
  }

  @Test
  void testAddQuestion() {
    QuestionService qService = new QuestionService();
    int count = qService.getQuestionsCount();
    Question question = new Question(
      0,
      "question",
      "optionA",
      "optionB",
      "optionC",
      "optionD",
      "B"
    );
    if (!qService.addQuestion(question)) fail(
      "Should add new Question to the database."
    );
    assertEquals(count + 1, qService.getQuestionsCount());
  }

  @Test
  void testDeleteQuestion() {
    QuestionService qService = new QuestionService();
    int count = qService.getQuestionsCount();
    // Replace '406' with the id of last inserted element.
    if (!qService.deleteQuestionById(406)) fail(
      "Should delete specified question."
    );
    assertEquals(count - 1, qService.getQuestionsCount());
  }
}
