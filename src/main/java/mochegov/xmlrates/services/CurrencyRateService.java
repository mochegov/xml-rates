package mochegov.xmlrates.services;

import static mochegov.xmlrates.utils.RateUtils.currencyRateDtoToRateGroup;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.CurrencyRateDto;
import mochegov.xmlrates.producer.JmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rates.RateGroup;

@Slf4j
@Service
public class CurrencyRateService {
    private final JmsService jmsService;

    private final String queue;

    public CurrencyRateService(JmsService jmsService, @Value("${rate.queue}") String queue) {
        this.jmsService = jmsService;
        this.queue = queue;
    }

    public void sendCurrencyRate(List<CurrencyRateDto> dtoList) {
        if (dtoList.isEmpty()) {
            log.error("List of currency rates is empty");
            return;
        }

        RateGroup rateGroup = currencyRateDtoToRateGroup(dtoList);
        jmsService.sendAsyncMessage(queue, rateGroup);
    }
}
