package main.java.controllers;

import java.util.ArrayList;
import java.util.List;
import main.java.models.Question;
import main.java.services.QuestionService;

public class TestController {
  private int questionsCount;
  private int currentIndex;
  private List<Question> questions;
  private List<String> userSelectedAnswers;
  private QuestionService qService;

  TestController(int count) {
    qService = new QuestionService();
    questionsCount = count;
    userSelectedAnswers = new ArrayList<>();
    for (int i = 0; i < questionsCount; i++) userSelectedAnswers.add("");
    questions = qService.getRandomQuestions(questionsCount);
    currentIndex = -1;
  }

  /**
   * Get the next question in the test.
   */
  public Question getNextQuestion() {
    if (currentIndex < questionsCount - 1) currentIndex++;
    return questions.get(currentIndex);
  }

  /**
   * Get the previous question in the test.
   */
  public Question getPreviousQuestion() {
    if (currentIndex > 0) currentIndex--;
    return questions.get(currentIndex);
  }

  /**
   * Set the answer for the current question.
   */
  public void setAnswer(String answer) {
    userSelectedAnswers.set(currentIndex, answer);
  }

  /**
   * Get the answer selected by the user for the current question, if any.
   */
  public String getSelectedAnswer() {
    return userSelectedAnswers.get(currentIndex);
  }

  /**
   * Get the result for the current test.
   */
  public int getResult() {
    int result = 0;
    int i = 0;
    for (Question question : questions) {
      if (question.getcorrectAnswer() == userSelectedAnswers.get(i++)) {
        result++;
      }
    }
    return result;
  }
}
