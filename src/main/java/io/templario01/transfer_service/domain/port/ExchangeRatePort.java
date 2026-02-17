package io.templario01.transfer_service.domain.port;

import reactor.core.publisher.Mono;

public interface ExchangeRatePort {
    // Devuelve la tasa (rate) consultada en la API para la moneda indicada.
    // La validación si coincide con la enviada por el usuario debe hacerse en el caso de uso.
    Mono<Double> getRate(String currency);
}
