package dev.hinze.beryl.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "beryl-halia")
public class ApplicationProperties {

    private Set<String> accessTokens;
    private List<String> ignoreSuffixes;

}
