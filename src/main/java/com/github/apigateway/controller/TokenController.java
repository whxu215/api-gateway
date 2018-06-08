package com.github.apigateway.controller;

import com.github.apigateway.protocol.ApplyTokenProtocol;
import com.github.apigateway.service.TokenService;
import com.github.apigateway.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class TokenController {
  @Autowired
  private TokenService tokenService;

  @RequestMapping("/apply/token")
  public ApplyTokenProtocol.ApplyTokenResp applyToken(@RequestBody ApplyTokenProtocol.ApplyTokenReq req) {
    return tokenService.applyToken(req);
  }

  @RequestMapping("/logout")
  public void logout(HttpServletRequest request) {
    tokenService.invalidateToken((String) request.getAttribute(Constants.TOKEN));
  }
}