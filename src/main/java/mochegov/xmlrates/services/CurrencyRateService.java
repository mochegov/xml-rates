package mochegov.xmlrates.services;

import static mochegov.xmlrates.utils.RateUtils.currencyRateDtoToRateGroup;
import static ru.raiffeisen.gl.common.clients.GlHttpClient.COMMON_OBJECT_MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.CurrencyRateDto;
import mochegov.xmlrates.dto.RateGroupDto;
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

    public void sendCurrencyRate(List<CurrencyRateDto> dtoList) throws JsonProcessingException {
        if (dtoList.isEmpty()) {
            log.error("List of currency rates is empty");
            return;
        }

        RateGroup rateGroup = currencyRateDtoToRateGroup(dtoList);
        RateGroupDto dto = new RateGroupDto(rateGroup);
        String messageString = COMMON_OBJECT_MAPPER.writeValueAsString(dto);
        jmsService.sendAnyCastMessage(queue, messageString, null, null, Map.of());
    }

    public void sendCurrencyRate(String json) {
        jmsService.sendAnyCastMessage(queue, json, null, null, Map.of());
    }
}
