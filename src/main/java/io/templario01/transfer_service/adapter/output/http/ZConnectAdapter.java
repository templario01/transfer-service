package io.templario01.transfer_service.adapter.output.http;

import com.io.templario01.transfer_service.adapter.contract.ZConnectResponse;
import io.templario01.transfer_service.domain.entity.Transfer;
import io.templario01.transfer_service.domain.port.ZConnectPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Adaptador para notificar transferencias al mainframe (ZConnect).
 * Implementa el puerto ZConnectPort.
 */
public class ZConnectAdapter implements ZConnectPort {

    private static final Logger log = LoggerFactory.getLogger(ZConnectAdapter.class);
    private static final String ZCONNECT_URL = "http://localhost:8081/api/zconnect/notify"; // Ajustar según entorno

    private final WebClient webClient;

    public ZConnectAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Notifica una transferencia al mainframe.
     *
     * @param transfer Transferencia a notificar
     * @return Mono con la respuesta del mainframe
     */
    @Override
    public Mono<ZConnectResponse> notifyMainframe(Transfer transfer) {
        log.info("Notifying mainframe for transfer from {} to {}",
                transfer.getOriginAccountNumber(), transfer.getDestinationAccountNumber());

        ZConnectRequest request = new ZConnectRequest(transfer);

        return webClient
                .post()
                .uri(ZCONNECT_URL)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ZConnectResponse.class)
                .doOnSuccess(response -> log.info("Mainframe notification successful: {}", response.getTransactionId()))
                .doOnError(error -> log.error("Error notifying mainframe: {}", error.getMessage()))
                .onErrorResume(error -> Mono.error(
                        new RuntimeException("Failed to notify mainframe", error)
                ));
    }

    /**
     * Clase interna para serializar la solicitud al mainframe.
     */
    public static class ZConnectRequest {
        private String transactionId;
        private String originAccount;
        private String destinationAccount;
        private String currency;
        private double amount;
        private double exchangeRate;
        private ZonedDateTime timestamp;

        public ZConnectRequest(Transfer transfer) {
            this.transactionId = UUID.randomUUID().toString();
            this.originAccount = transfer.getOriginAccountNumber();
            this.destinationAccount = transfer.getDestinationAccountNumber();
            this.currency = transfer.currency();
            this.amount = transfer.amount();
            this.exchangeRate = transfer.getExchangeRate();
            this.timestamp = ZonedDateTime.now();
        }

        // Getters y Setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getOriginAccount() {
            return originAccount;
        }

        public void setOriginAccount(String originAccount) {
            this.originAccount = originAccount;
        }

        public String getDestinationAccount() {
            return destinationAccount;
        }

        public void setDestinationAccount(String destinationAccount) {
            this.destinationAccount = destinationAccount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }

        public void setExchangeRate(double exchangeRate) {
            this.exchangeRate = exchangeRate;
        }

        public ZonedDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(ZonedDateTime timestamp) {
            this.timestamp = timestamp;
        }
    }
}

