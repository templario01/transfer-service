package io.templario01.transfer_service.adapter.input.web;

import io.templario01.transfer_service.adapter.input.web.dto.TransferRequest;
import io.templario01.transfer_service.adapter.input.web.dto.TransferResponse;
import io.templario01.transfer_service.application.TransferUseCase;
import io.templario01.transfer_service.domain.entity.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransactionController {
    private final TransferUseCase transferUseCase;

    @PostMapping
    public Mono<ResponseEntity<TransferResponse>> createTransfer(@RequestBody TransferRequest request) {
        Transfer transfer = new Transfer(
                request.getSourceAccount(),
                request.getDestinationAccount(),
                request.getCurrency(),
                request.getAmount(),
                request.getExchangeRateInformation()
        );

        // Ejecutar el caso de uso y mapear la respuesta
        return transferUseCase.execute(transfer)
                .map(zConnectResponse -> {
                    TransferResponse response = mapToTransferResponse(request, zConnectResponse);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                })
                .onErrorResume(error -> {
                    TransferResponse errorResponse = new TransferResponse();
                    errorResponse.setStatus("REJECTED");
                    return Mono.just(ResponseEntity.badRequest().body(errorResponse));
                });
    }

    private TransferResponse mapToTransferResponse(TransferRequest request, Object zConnectResponse) {
        TransferResponse response = new TransferResponse();
        response.setSourceAccount(request.getSourceAccount());
        response.setDestinationAccount(request.getDestinationAccount());
        response.setStatus("SUCCESS");

        if (request.getExchangeRateInformation() != null) {
            TransferResponse.ExchangeRateResponseInfo exchangeInfo = new TransferResponse.ExchangeRateResponseInfo();
            TransferResponse.ExchangeRateResponseInfo.TargetRate targetRate =
                    new TransferResponse.ExchangeRateResponseInfo.TargetRate(
                            request.getDestinationAccount().getCurrency(),
                            request.getExchangeRateInformation().getRate(),
                            request.getExchangeRateInformation().getTargetAmount()
                    );
            exchangeInfo.setTargetRate(targetRate);
            response.setExchangeRateInformation(exchangeInfo);
        }

        return response;
    }
}