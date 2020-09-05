package main.java.exceptions;

import java.util.List;

/**
 * InvalidArgumentsException
 */
public class InvalidArgumentsException extends Exception {
  private static final long serialVersionUID = -2452575926132040852L;

  List<String> invalidArgs;

  public InvalidArgumentsException(List<String> args) {
    this.invalidArgs = args;
  }

  @Override
  public String getMessage() {
    return ("Invalid Arguments: " + invalidArgs.toString());
  }
}
