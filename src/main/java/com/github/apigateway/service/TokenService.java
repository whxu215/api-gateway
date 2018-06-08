package com.github.apigateway.service;

import com.github.apigateway.config.ServiceSecretConfig;
import com.github.apigateway.exception.ApiGatewayMessage;
import com.github.apigateway.exception.AppException;
import com.github.apigateway.util.AccessTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.github.apigateway.protocol.ApplyTokenProtocol.ApplyTokenReq;
import static com.github.apigateway.protocol.ApplyTokenProtocol.ApplyTokenResp;

@Service
public class TokenService {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private ServiceSecretConfig serviceSecretConfig;

  public ApplyTokenResp applyToken(ApplyTokenReq req) {
    checkServiceSecret(req);

    String accessToken = AccessTokenGenerator.generate();
    redisTemplate.opsForValue().set(accessToken, req.getUserInfo(), req.getExpiredIn(), TimeUnit.SECONDS);
    return new ApplyTokenResp(accessToken);
  }

  public Optional<String> checkToken(String token) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(token));
  }

  public void invalidateToken(String token) {
    redisTemplate.delete(token);
  }

  private void checkServiceSecret(ApplyTokenReq req) {
    if (!serviceSecretConfig.isServiceSecretValid(req.getServiceName(), req.getSecret())) {
      throw new AppException(ApiGatewayMessage.SERVICE_AUTH_FAILED);
    }
  }
}
