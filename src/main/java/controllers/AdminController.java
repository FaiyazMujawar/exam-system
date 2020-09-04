package main.java.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.java.exceptions.InvalidArgumentsException;
import main.java.misc.Validation;
import main.java.models.Question;
import main.java.services.QuestionService;

public class AdminController {
  private QuestionService qService;
  private Validation validation;

  public AdminController() {
    qService = new QuestionService();
    validation = new Validation();
  }

  public List<Question> getAllQuestions() {
    return qService.getAllQuestions();
  }

  public void addQuestion(
    String questionString,
    String optionA,
    String optionB,
    String optionC,
    String optionD,
    String correctAnswer
  )
    throws InvalidArgumentsException {
    List<String> errors = validation.validateQuestion(
      questionString,
      optionA,
      optionB,
      optionC,
      optionD
    );
    if (errors.size() != 0) {
      throw new InvalidArgumentsException(errors);
    } else {
      Question question = new Question(
        0,
        questionString,
        optionA,
        optionB,
        optionC,
        optionD,
        correctAnswer
      );
      qService.addQuestion(question);
    }
  }

  public Question getQuestionById(int id) throws InvalidArgumentsException {
    if (id < 1) throw new InvalidArgumentsException(
      new ArrayList<>(Arrays.asList("ID"))
    );
    return qService.getQuestionById(id);
  }

  public boolean deleteQuestionByID(int id) throws InvalidArgumentsException {
    if (id < 1) throw new InvalidArgumentsException(
      new ArrayList<>(Arrays.asList("ID"))
    );
    return qService.deleteQuestionById(id);
  }
}
