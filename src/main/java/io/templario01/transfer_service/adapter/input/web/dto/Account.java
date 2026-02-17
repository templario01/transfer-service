package io.templario01.transfer_service.adapter.input.web.dto;

import lombok.Data;

@Data
public class Account {
    private String accountNumber;
    private String currency;
    private AccountHolder accountHolder;
}

