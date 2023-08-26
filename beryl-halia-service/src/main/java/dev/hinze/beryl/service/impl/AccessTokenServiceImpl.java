package dev.hinze.beryl.service.impl;

import dev.hinze.beryl.configuration.ApplicationProperties;
import dev.hinze.beryl.service.AccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenServiceImpl implements AccessTokenService {

    private final ApplicationProperties applicationProperties;

    @Override
    public boolean isValid(String accessToken) {
        var valid = applicationProperties.getAccessTokens().contains(accessToken);
        log.debug("accessToken: {} valid: {}", accessToken, valid);
        return valid;
    }

}
