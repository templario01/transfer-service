package io.templario01.transfer_service.adapter.input.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransferRequest {

    @Valid
    @NotNull
    private Account sourceAccount;

    @Valid
    @NotNull
    private Account destinationAccount;

    @Positive
    private double amount;

    @NotNull
    private String currency;

    private ExchangeRateInformation exchangeRateInformation;
}