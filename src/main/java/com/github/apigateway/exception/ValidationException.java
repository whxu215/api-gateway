package com.github.apigateway.exception;

import java.text.ParseException;

public class ValidationException extends RuntimeException {

  public ValidationException(String message, ParseException cause) {
    super(message, cause);
  }
}
