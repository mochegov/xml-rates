package mochegov.xmlrates.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import mochegov.xmlrates.enums.Currency;

@Getter
@Setter
public class CloseAccountDto {
    private String accountNumber;
    private Currency currency;
    private LocalDate closeDate;
    private String acod;
}
