package io.templario01.transfer_service.adapter.output.db;

import io.templario01.transfer_service.domain.entity.Transfer;
import io.templario01.transfer_service.domain.port.TransferRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class TransferRepositoryAdapter implements TransferRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(TransferRepositoryAdapter.class);

    private final TransferR2dbcRepository repository;

    public TransferRepositoryAdapter(TransferR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Void> save(Transfer transfer) {
        log.info("========== INICIANDO PERSISTENCIA DE TRANSFERENCIA ==========");

        TransferEntity entity = new TransferEntity();
        entity.setOriginAccount(transfer.getOriginAccountNumber());
        entity.setDestinationAccount(transfer.getDestinationAccountNumber());
        entity.setCurrency(transfer.currency());
        entity.setAmount(transfer.amount());
        entity.setExchangeRate(transfer.getExchangeRate());

        log.info("Datos a guardar en BD:");
        log.info("  - Cuenta Origen: {}", entity.getOriginAccount());
        log.info("  - Cuenta Destino: {}", entity.getDestinationAccount());
        log.info("  - Moneda: {}", entity.getCurrency());
        log.info("  - Monto: {}", entity.getAmount());
        log.info("  - Tasa de Cambio: {}", entity.getExchangeRate());

        log.debug("Repository instance: {}", repository);
        log.debug("Entity antes de guardar: {}", entity);

        return repository.save(entity)
                .doOnNext(savedEntity -> {
                    log.info("✅ Transferencia guardada exitosamente en BD");
                    log.info("  - ID generado: {}", savedEntity.getId());
                    log.info("  - Cuenta Origen guardada: {}", savedEntity.getOriginAccount());
                    log.info("  - Cuenta Destino guardada: {}", savedEntity.getDestinationAccount());
                    log.info("  - Monto guardado: {}", savedEntity.getAmount());
                    log.info("  - Tasa guardada: {}", savedEntity.getExchangeRate());
                })
                .doOnError(error -> {
                    log.error("❌ ERROR al guardar transferencia en BD", error);
                    log.error("   Tipo de error: {}", error.getClass().getName());
                    log.error("   Mensaje: {}", error.getMessage());
                    log.error("   Causa: {}", error.getCause());
                })
                .doFinally(signal -> {
                    log.info("Finalizado proceso de persistencia. Signal: {}", signal);
                })
                .then()
                .doOnError(error -> {
                    log.error("❌ Error final en el Mono<Void>: {}", error.getMessage());
                });
    }
}



