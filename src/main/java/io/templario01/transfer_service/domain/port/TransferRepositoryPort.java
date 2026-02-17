package io.templario01.transfer_service.domain.port;

import io.templario01.transfer_service.domain.entity.Transfer;
import reactor.core.publisher.Mono;

public interface TransferRepositoryPort {
    Mono<Void> save(Transfer transfer);
}
