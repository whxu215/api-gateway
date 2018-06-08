package com.github.apigateway.util;

import java.util.UUID;

public final class AccessTokenGenerator {
  public static String generate() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
