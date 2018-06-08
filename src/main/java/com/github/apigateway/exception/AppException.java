package com.github.apigateway.exception;

public class AppException extends BaseException {

  public AppException(ErrorMessage result) {
    super(result);
  }

  public AppException(ErrorMessage result, String... params) {
    super(result, params);
  }

  public AppException(ErrorMessage message, RuntimeException ex) {
    super(message, ex);
  }

}
