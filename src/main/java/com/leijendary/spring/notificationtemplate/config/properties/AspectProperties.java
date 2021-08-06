package com.leijendary.spring.notificationtemplate.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aspect")
@Data
public class AspectProperties {

    private Logging logging;
    private Execution execution;

    @Data
    public static class Logging {

        private boolean enabled;
    }

    @Data
    public static class Execution {

        private long time = -1;
    }
}
