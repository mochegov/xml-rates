package mochegov.xmlrates.services;

import static mochegov.xmlrates.utils.DateTimeUtils.buildXMLGregorianCalendar;
import static mochegov.xmlrates.utils.DateTimeUtils.convertLocalDateToXMLGregorianCalendar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.AnyOperationDto;
import mochegov.xmlrates.dto.CloseAccountDto;
import mochegov.xmlrates.dto.OpenAccountDto;
import mochegov.xmlrates.enums.Currency;
import mochegov.xmlrates.enums.MidasAccountEventType;
import mochegov.xmlrates.producer.JmsService;
import mochegov.xmlrates.utils.AccountEventXmlConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rates.SrvAccountPublEvent;
import ru.raiffeisen.gl.common.enums.Branch;

@Slf4j
@Service
public class AccountService {
    private final AccountEventXmlConverter xmlConverter;
    private final JmsService jmsService;

    private final String queue;

    public static final String ACCOUNT_ACOD = "3775";

    public AccountService(AccountEventXmlConverter xmlConverter,
                          JmsService jmsService,
                          @Value("${account.queue}") String queue) {
        this.xmlConverter = xmlConverter;
        this.jmsService = jmsService;
        this.queue = queue;
    }

    private SrvAccountPublEvent.TAccountNumber getTAccountNumber(String accountNumber) {
        SrvAccountPublEvent.TAccountNumber tAccountNumber = new SrvAccountPublEvent.TAccountNumber();
        tAccountNumber.setFmaNumber(UUID.randomUUID().toString());
        tAccountNumber.setCbaNumber(accountNumber);
        return tAccountNumber;
    }

    public void sendCloseAccountMessage(CloseAccountDto dto) {
        SrvAccountPublEvent event = buildCommonAccountEvent(dto.getAccountNumber(), dto.getCurrency(), Branch.RBA);
        event.setEvent(MidasAccountEventType.MIDAS_ACCOUNT_CLOSE_EVENT.getName());
        event.setAcod(dto.getAcod());
        event.setOpenDate(buildXMLGregorianCalendar(2021, 1, 1));
        event.setCloseDate(convertLocalDateToXMLGregorianCalendar(dto.getCloseDate()));
        String xmlText = xmlConverter.serializeToXmlFormat(event);
        jmsService.sendMultiCastMessage(queue, xmlText);
    }

    public void sendOpenAccountMessage(OpenAccountDto dto) {
        SrvAccountPublEvent event = buildCommonAccountEvent(dto.getAccountNumber(), dto.getCurrency(), dto.getBranch());
        event.setEvent(dto.getEventType().getName());
        event.setOpenDate(convertLocalDateToXMLGregorianCalendar(dto.getOpenDate()));
        event.setInstitCode(dto.getInstitutionCode());
        event.setAcod(dto.getAcod());
        event.setIntCalcTypeCr(dto.getIntCalcType());
        event.setIntCalcSubtCr(dto.getIntCalcSubType());
        String xmlText = xmlConverter.serializeToXmlFormat(event);
        jmsService.sendMultiCastMessage(queue, xmlText);
    }

    public void sendAnyAccountMessage(AnyOperationDto dto) {
        jmsService.sendMultiCastMessage(queue, dto.getRawText());
    }

    private SrvAccountPublEvent buildCommonAccountEvent(String accountNumber, Currency currency, Branch branch) {
        SrvAccountPublEvent event = new SrvAccountPublEvent();
        event.setEventDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        event.setAcsq(UUID.randomUUID().toString());
        event.setAcod(ACCOUNT_ACOD);
        event.setBranch(branch.getCodeRetail());
        event.setCustomerNumber("A1B2C3");
        event.setAccountType("R");
        event.setCurrency(currency.toString());
        event.setStatus("C");
        event.setTAccountNumber(getTAccountNumber(accountNumber));
        event.setIsInactive("N");
        event.setIsBlockDebet("N");
        event.setIsBlockCredit("N");
        event.setIsReferDebet("N");
        event.setIsReferCredit("N");
        event.setMinBalance(0L);
        event.setStatFreq("N");
        event.setIntCalcTypeCr("01");
        event.setIntCalcSubtCr("0002");
        return event;
    }
}
