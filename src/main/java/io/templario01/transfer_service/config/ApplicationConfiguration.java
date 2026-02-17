package io.templario01.transfer_service.config;

import io.templario01.transfer_service.application.TransferUseCase;
import io.templario01.transfer_service.domain.port.ExchangeRatePort;
import io.templario01.transfer_service.domain.port.TransferRepositoryPort;
import io.templario01.transfer_service.domain.port.ZConnectPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    TransferUseCase transferUseCase(
            TransferRepositoryPort repositoryPort,
            ExchangeRatePort exchangeRatePort,
            ZConnectPort zConnectPort
    ) {
        return new TransferUseCase(
                repositoryPort,
                exchangeRatePort,
                zConnectPort
        );
    }
}
