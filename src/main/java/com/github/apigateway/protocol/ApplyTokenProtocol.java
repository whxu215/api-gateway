package com.github.apigateway.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApplyTokenProtocol {
  @Getter
  @Setter
  public static class ApplyTokenReq {
    private String serviceName;
    private String secret;
    private int expiredIn;
    private String userInfo;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class ApplyTokenResp {
    private String token;
  }
}
