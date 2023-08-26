package dev.hinze.beryl.service.impl;


import dev.hinze.beryl.configuration.ApplicationProperties;
import dev.hinze.beryl.service.AccessTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AccessTokenServiceImpl.class)
class AccessTokenServiceImplTest {

    @MockBean
    private ApplicationProperties applicationProperties;

    @Autowired
    private AccessTokenService accessTokenService;

    @BeforeEach
    void beforeEach() {
        when(applicationProperties.getAccessTokens())
                .thenReturn(Set.of("1"));

    }

    @Test
    void shouldReturnTrueWhenAccessTokenIsInSet() {
        assertThat(accessTokenService.isValid("1"))
                .isTrue();
    }

    @Test
    void shouldReturnFalseWhenAccessTokenIsNotInSet() {
        assertThat(accessTokenService.isValid("2"))
                .isFalse();
    }

}