package dev.hinze.beryl.interceptor;

import dev.hinze.beryl.configuration.ApplicationProperties;
import dev.hinze.beryl.service.AccessTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessInterceptor implements HandlerInterceptor {

    private final AccessTokenService accessTokenService;
    private final ApplicationProperties applicationProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var path = request.getServletPath();
        if(path.equalsIgnoreCase("/health") ||
           path.equalsIgnoreCase("/error") ||
           applicationProperties.getIgnoreSuffixes().stream().anyMatch(path::endsWith)) {
            return true;
        } else {
            var accessTokenParameter = request.getParameter("accessToken");
            if(isNull(accessTokenParameter)) {
                var cookies = request.getCookies();
                if(nonNull(cookies)) {
                    var accessTokenCookie = Arrays.stream(cookies)
                            .filter(cookie -> "access-token".equals(cookie.getName()))
                            .findFirst();
                    if (accessTokenCookie.isPresent()) {
                        return accessTokenService.isValid(accessTokenCookie.get().getValue());
                    }
                }
            } else if(accessTokenService.isValid(accessTokenParameter)) {
                return true;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

}
