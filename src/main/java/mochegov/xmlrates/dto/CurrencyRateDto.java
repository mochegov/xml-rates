package mochegov.xmlrates.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import mochegov.xmlrates.enums.Currency;

@Data
public class CurrencyRateDto {
    private Currency currency;
    private LocalDate date;
    private BigDecimal rateValue;
}
