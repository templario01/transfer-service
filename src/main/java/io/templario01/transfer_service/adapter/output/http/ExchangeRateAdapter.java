package io.templario01.transfer_service.adapter.output.http;

import io.templario01.transfer_service.domain.port.ExchangeRatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Adaptador para obtener tasas de cambio desde una API externa.
 * Implementa el puerto ExchangeRatePort.
 */
public class ExchangeRateAdapter implements ExchangeRatePort {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateAdapter.class);
    private static final String EXCHANGE_RATE_URL = "https://api.exchangerate-api.com/v4/latest/{currency}";

    private final WebClient webClient;

    public ExchangeRateAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Obtiene la tasa de cambio para una moneda específica.
     *
     * @param currency Código de moneda (ej: USD, PEN)
     * @return Mono con la tasa de cambio
     */
    @Override
    public Mono<Double> getRate(String currency) {
        log.info("Fetching exchange rate for currency: {}", currency);

        return webClient
                .get()
                .uri(EXCHANGE_RATE_URL, currency)
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .map(response -> response.getRates().get("USD")) // Ajustar según API
                .doOnError(error -> log.error("Error fetching exchange rate for {}: {}", currency, error.getMessage()))
                .onErrorResume(error -> Mono.error(
                        new RuntimeException("Failed to fetch exchange rate for " + currency, error)
                ));
    }

    /**
     * Clase interna para deserializar la respuesta de la API de tasas.
     */
    public static class ExchangeRateResponse {
        private String base;
        private java.util.Map<String, Double> rates;

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public java.util.Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(java.util.Map<String, Double> rates) {
            this.rates = rates;
        }
    }
}

