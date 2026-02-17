package io.templario01.transfer_service.adapter.output.db;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransferR2dbcRepository extends ReactiveCrudRepository<TransferEntity, Long> {

}
