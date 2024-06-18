package mochegov.xmlrates.dto.ae;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mochegov.xmlrates.enums.OperationType;
import ru.raiffeisen.gl.common.enums.account.ResidentSign;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrototypeBusProc {
    private OperationType operationType;

    // Data for opening a client account
    private String customerCode;
    private String branchCode;
    private ResidentSign residentSign;
    private String currencyCode;

    // Data for creating accounting entries
    private String bankAccountNumber;
    private String bankCashAccountNumber;
    private BigDecimal amount;
}
