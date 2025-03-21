package com.challenge.api.account.management.configuration;

import com.challenge.api.account.management.util.JacocoAnnotationGenerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "api.http-client.retry")
@JacocoAnnotationGenerated
public class ClientRetryProperties {
    @NotNull
    @Min(0)
    Integer attempts;

    @NotNull
    @Min(0)
    Long delay;

    @NonNull
    public Retry getFixedDelayRetry() {
        return Retry.fixedDelay(attempts, Duration.ofMillis(delay));
    }
}
