package com.github.apigateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "zuul.service.secret")
public class ServiceSecretConfig {
  private Map<String, String> serviceSecretMap;

  public boolean isServiceSecretValid(String serviceName, String serviceSecret) {
    Optional<String> secret = Optional.ofNullable(serviceSecretMap.get(serviceName));
    return secret.map(s -> Objects.equals(s, serviceSecret)).orElseGet(() -> false);
  }
}



