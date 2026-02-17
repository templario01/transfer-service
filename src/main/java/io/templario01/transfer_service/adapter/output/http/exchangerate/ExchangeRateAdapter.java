package io.templario01.transfer_service.adapter.output.http.exchangerate;

import io.templario01.transfer_service.adapter.output.http.exchangerate.dto.PolygonResponse;
import io.templario01.transfer_service.domain.port.ExchangeRatePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class ExchangeRateAdapter implements ExchangeRatePort {

    private final WebClient webClient;
    private final ExchangeRateProperties props;

    @Override
    public Mono<Double> getRate(String fromCurrency) {

        String toCurrency = fromCurrency.equals("USD") ? "PEN" : "USD";
        String cValue = "C";

        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/v2/aggs/ticker/{cValue}:{fromCurrency}{toCurrency}/prev")
                                .queryParam("apiKey", props.getApiKey())
                                .build(cValue, fromCurrency, toCurrency)
                )
                .retrieve()
                .bodyToMono(PolygonResponse.class)
                .flatMap(response -> {
                    if (!"OK".equals(response.getStatus()) || response.getResults().isEmpty()) {
                        return Mono.empty();
                    }
                    double apiRate = response.getResults().get(0).getC();
                    log.info("Exchange rate from API: {}", apiRate);
                    return Mono.just(apiRate);
                })
                .retryWhen(
                        Retry.backoff(
                                        props.getRetry().getMaxAttempts(),
                                        Duration.ofMillis(props.getRetry().getBackoffMs())
                                )
                                .doBeforeRetry(r ->
                                        log.warn("Retrying exchange rate API, attempt {}",
                                                r.totalRetries() + 1))
                )
                .doOnError(e -> log.error("Error calling exchange rate API", e));
    }
}