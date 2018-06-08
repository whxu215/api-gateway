package com.github.apigateway.exception;

import java.text.MessageFormat;

public class BaseException extends RuntimeException {

  private final String errorCode;
  private final String errorMessage;
  private String extErrorMessage;

  public BaseException(String errorCode) {
    super();
    this.errorCode = errorCode;
    errorMessage = "";
  }

  public BaseException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public BaseException(ErrorMessage result, Exception exception) {
    super(result.getMsg(), exception);
    errorCode = result.getCode();
    errorMessage = exception.getMessage();
  }

  public BaseException(ErrorMessage result) {
    super(result.getMsg());
    this.errorCode = result.getCode();
    this.errorMessage = result.getMsg();
  }

  public BaseException(ErrorMessage result, String... params) {
    super(result.getMsg());
    this.errorCode = result.getCode();
    this.errorMessage = MessageFormat.format(result.getMsg(), params);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getExtErrorMessage() {
    return extErrorMessage;
  }
}
