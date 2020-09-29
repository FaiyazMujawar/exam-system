package main.java.misc;

import java.util.ArrayList;
import java.util.List;

public class Validation {

  public Validation() {}

  public List<String> validateQuestion(
    String qtn,
    String optA,
    String optB,
    String optC,
    String optD
  ) {
    // TODO: Modify these configurations.
    List<String> errors = new ArrayList<>();
    if (qtn.length() < 20) errors.add("Question");
    if (optA.length() < 20) errors.add("Question");
    if (optB.length() < 20) errors.add("Question");
    if (optC.length() < 20) errors.add("Question");
    if (optD.length() < 20) errors.add("Question");
    return errors;
  }
}
