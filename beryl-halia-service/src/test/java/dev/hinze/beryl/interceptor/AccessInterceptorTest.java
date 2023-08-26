package dev.hinze.beryl.interceptor;


import dev.hinze.beryl.configuration.ApplicationProperties;
import dev.hinze.beryl.service.AccessTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AccessInterceptor.class)
class AccessInterceptorTest {

    @MockBean
    private AccessTokenService accessTokenService;

    @MockBean
    private ApplicationProperties applicationProperties;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AccessInterceptor accessInterceptor;

    @BeforeEach
    void beforeEach() {
        when(applicationProperties.getIgnoreSuffixes())
                .thenReturn(List.of(".js"));
    }

    @Test
    void shouldReturnFalseWhenSuffixNotInRequest() {
        when(httpServletRequest.getServletPath())
                .thenReturn("/foo.jpg");

        assertThrows(
                ResponseStatusException.class,
                () -> accessInterceptor.preHandle(httpServletRequest, null, null)
        );
    }

    @Test
    void shouldReturnTrueWhenSuffixInRequest() {
        when(httpServletRequest.getServletPath())
                .thenReturn("/foo.js");

        assertThat(accessInterceptor.preHandle(httpServletRequest, null, null))
                .isTrue();
    }

    @Test
    void shouldReturnTrueWhenHealthRequest() {
        when(httpServletRequest.getServletPath())
                .thenReturn("/health");

        assertThat(accessInterceptor.preHandle(httpServletRequest, null, null))
                .isTrue();
    }

    @Test
    void shouldReturnTrueWhenErrorRequest() {
        when(httpServletRequest.getServletPath())
                .thenReturn("/error");

        assertThat(accessInterceptor.preHandle(httpServletRequest, null, null))
                .isTrue();
    }

    @Test
    void shouldReturnTrueWhenAccessTokenIsValid() {
        var accessToken = "1";

        when(httpServletRequest.getServletPath())
                .thenReturn("/content");
        when(accessTokenService.isValid(eq(accessToken)))
                .thenReturn(true);
        when(httpServletRequest.getParameter(eq("accessToken")))
                .thenReturn(accessToken);

        assertThat(accessInterceptor.preHandle(httpServletRequest, null, null))
                .isTrue();
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenAccessTokenIsNull() {

        when(httpServletRequest.getServletPath())
                .thenReturn("/content");
        when(httpServletRequest.getParameter(eq("accessToken")))
                .thenReturn(null);

        assertThrows(
                ResponseStatusException.class,
                () -> accessInterceptor.preHandle(httpServletRequest, null, null)
        );
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenAccessTokenIsNotValid() {
        var accessToken = "1";

        when(httpServletRequest.getServletPath())
                .thenReturn("/content");
        when(accessTokenService.isValid(eq(accessToken)))
                .thenReturn(false);
        when(httpServletRequest.getParameter(eq("accessToken")))
                .thenReturn(accessToken);

        assertThrows(
                ResponseStatusException.class,
                () -> accessInterceptor.preHandle(httpServletRequest, null, null)
        );
    }

}