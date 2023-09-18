package io.devlabs.keytree.commons.interceptor;

import io.devlabs.keytree.commons.annotation.HasRole;
import io.devlabs.keytree.commons.redis.RedissonUtils;
import io.devlabs.keytree.domains.auth.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

  private final RedissonUtils redissonUtils;
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if(!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    HasRole hasRole = handlerMethod.getMethodAnnotation(HasRole.class);
    if(hasRole == null) {
      return true;
    }

    HttpSession session = request.getSession();
    if(session == null) {
      return false;
    }

    String email = (String) session.getAttribute("email");
    if(email == null) {
      return false;
    }

    Optional<String> sessionId = redissonUtils.getValue(email);
    if(sessionId.isEmpty()) {
      return false;
    }
    if(!sessionId.get().equals(session.getId())) {
      return false;
    }

    UserRole userRole = (UserRole) session.getAttribute("role");
    if(userRole == null) {
      return false;
    }

    if(!userRole.validateLevel(hasRole.role())) {
      return false;
    }

    return true;
  }
}
