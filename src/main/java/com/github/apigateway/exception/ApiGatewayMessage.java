package com.github.apigateway.exception;

public enum ApiGatewayMessage implements ErrorMessage {

  SERVICE_AUTH_FAILED("0001", "服务认证失败");

  private final String code;
  private final String msg;

  ApiGatewayMessage(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

}
