package io.templario01.transfer_service.adapter.input.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("executionTime")
    private ZonedDateTime executionTime;

    @JsonProperty("sourceAccount")
    private Account sourceAccount;

    @JsonProperty("destinationAccount")
    private Account destinationAccount;

    @JsonProperty("exchangeRateInformation")
    private ExchangeRateResponseInfo exchangeRateInformation;

    @JsonProperty("status")
    private String status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeRateResponseInfo {
        @JsonProperty("targetRate")
        private TargetRate targetRate;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TargetRate {
            private String currency;
            private double rate;
            private double amount;
        }
    }
}
