package io.templario01.transfer_service.adapter.output.http.exchangerate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "exchange-rate.polygon")
public class ExchangeRateProperties {

    private String baseUrl;
    private String apiKey;

    @NestedConfigurationProperty
    private Timeouts timeouts = new Timeouts();

    @NestedConfigurationProperty
    private Retry retry = new Retry();

    @Getter @Setter
    public static class Timeouts {
        private int connect = 2000;
        private int read = 3000;
        private int write = 3000;
    }

    @Getter @Setter
    public static class Retry {
        private int maxAttempts = 3;
        private int backoffMs = 500;
    }
}