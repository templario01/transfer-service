package io.templario01.transfer_service.adapter.output.db;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("transfers")
public class TransferEntity {

    @Id
    private Long id;
    private String originAccount;
    private String destinationAccount;
    private String currency;
    private double amount;
    private double exchangeRate;
}
