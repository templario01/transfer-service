package io.templario01.transfer_service.domain.entity;

import io.templario01.transfer_service.adapter.input.web.dto.Account;
import io.templario01.transfer_service.adapter.input.web.dto.ExchangeRateInformation;

import java.time.LocalDateTime;
import java.util.UUID;

public record Transfer(
        Account sourceAccount,
        Account destinationAccount,
        String currency,
        double amount,
        ExchangeRateInformation exchangeRateInformation
) {

    public boolean isValidCurrency() {
        return currency.equals("USD") || currency.equals("PEN");
    }

    public String getOriginAccountNumber() {
        return sourceAccount.getAccountNumber();
    }

    public String getDestinationAccountNumber() {
        return destinationAccount.getAccountNumber();
    }

    public double getExchangeRate() {
        return exchangeRateInformation != null ? exchangeRateInformation.getRate() : 1.0;
    }
}
