package mochegov.xmlrates.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import mochegov.xmlrates.enums.Currency;
import mochegov.xmlrates.enums.MidasAccountEventType;
import ru.raiffeisen.gl.common.enums.Branch;

@Getter
@Setter
public class OpenAccountDto {
    private MidasAccountEventType eventType;
    private String accountNumber;
    private Currency currency;
    private LocalDate openDate;
    private Branch branch;
    private String intCalcType;
    private String intCalcSubType;
    private String acod;
    private String institutionCode;
}
