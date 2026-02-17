package io.templario01.transfer_service.config;

import io.templario01.transfer_service.adapter.output.db.TransferRepositoryAdapter;
import io.templario01.transfer_service.adapter.output.db.TransferR2dbcRepository;
import io.templario01.transfer_service.adapter.output.http.ExchangeRateAdapter;
import io.templario01.transfer_service.adapter.output.http.ZConnectAdapter;
import io.templario01.transfer_service.domain.port.ExchangeRatePort;
import io.templario01.transfer_service.domain.port.TransferRepositoryPort;
import io.templario01.transfer_service.domain.port.ZConnectPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * Configuración de puertos del dominio para el Transfer Service.
 * Define los beans de los adaptadores que implementan los puertos.
 */
@Configuration
public class DomainConfiguration {

    /**
     * Bean para el puerto de repositorio de transferencias.
     * Implementado por TransferRepositoryAdapter.
     *
     * @param repository Repositorio R2DBC de transferencias
     * @return Instancia del adaptador de repositorio
     */
    @Bean
    public TransferRepositoryPort transferRepositoryPort(TransferR2dbcRepository repository) {
        return new TransferRepositoryAdapter(repository);
    }

    /**
     * Bean para el puerto de tasas de cambio.
     * Implementado por ExchangeRateAdapter.
     * Utiliza WebClient para llamadas HTTP reactivas.
     *
     * @param webClientBuilder Constructor de WebClient
     * @return Instancia del adaptador de tasas de cambio
     */
    @Bean
    public ExchangeRatePort exchangeRatePort(WebClient.Builder webClientBuilder) {
        return new ExchangeRateAdapter(webClientBuilder.build());
    }

    /**
     * Bean para el puerto de notificación a mainframe (ZConnect).
     * Implementado por ZConnectAdapter.
     * Utiliza WebClient para llamadas HTTP reactivas.
     *
     * @param webClientBuilder Constructor de WebClient
     * @return Instancia del adaptador de ZConnect
     */
    @Bean
    public ZConnectPort zConnectPort(WebClient.Builder webClientBuilder) {
        return new ZConnectAdapter(webClientBuilder.build());
    }

    /**
     * Bean para WebClient que será utilizado por los adaptadores HTTP.
     * Proporciona soporte para llamadas HTTP reactivas.
     *
     * @param webClientBuilder Constructor de WebClient
     * @return Instancia configurada de WebClient
     */
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }
}
