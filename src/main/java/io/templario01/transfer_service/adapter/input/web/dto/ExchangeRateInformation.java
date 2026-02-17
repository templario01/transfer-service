package io.templario01.transfer_service.adapter.input.web.dto;

import lombok.Data;

@Data
public class ExchangeRateInformation {
    private String exchangeRateId;
    private String currency;
    private double targetAmount;
    private double rate;
    private String operationType;
}

