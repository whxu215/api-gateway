package com.github.apigateway.filter;

import com.github.apigateway.service.TokenService;
import com.github.apigateway.util.Constants;
import com.google.common.net.HttpHeaders;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AccessFilter extends ZuulFilter {

  private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

  private static final String NO_LOGIN_PATH = "/nologin/";

  @Autowired
  private TokenService tokenService;

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    if (RequestContext.getCurrentContext().getRequest().getRequestURI().contains(NO_LOGIN_PATH)) {
      return false;
    }
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();

    log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

    Optional<String> accessToken = getToken(request);
    if (!accessToken.isPresent()) {
      log.warn("access token is empty");
      authFailed(ctx);
    } else {
      Optional<String> authInfo = tokenService.checkToken(accessToken.get());
      if (!authInfo.isPresent()) {
        authFailed(ctx);
      } else {
        ctx.getZuulRequestHeaders().put(HttpHeaders.AUTHORIZATION, authInfo.get());
        request.setAttribute(Constants.TOKEN, accessToken.get());
      }
    }
    return null;
  }

  private void authFailed(RequestContext ctx) {
    ctx.setSendZuulResponse(false);
    ctx.setResponseStatusCode(401);
  }

  private Optional<String> getToken(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isEmpty(token)) {
      return Optional.empty();
    }

    String[] tokenLis = StringUtils.split(token, " ");
    if (tokenLis.length != 2) {
      return Optional.empty();
    }

    return Optional.of(tokenLis[1]);
  }
}
