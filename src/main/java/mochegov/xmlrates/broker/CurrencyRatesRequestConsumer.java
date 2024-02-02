package mochegov.xmlrates.broker;

import static ru.raiffeisen.gl.common.clients.GlHttpClient.COMMON_OBJECT_MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import rates.RateGroup;

@Slf4j
@Service
@AllArgsConstructor
public class CurrencyRatesRequestConsumer {
    @JmsListener(destination = "${rate.queue}", subscription = "${rate.subscription}",
        containerFactory = "ratesOutJmsListenerContainerFactory")
    public void receiveMessage(TextMessage message, @Payload String messageBodyJson) throws JsonProcessingException, JMSException {
        var rateGroup = COMMON_OBJECT_MAPPER.readValue(messageBodyJson, RateGroup.class);
        log.info("New rate group received. Message id {}, code: {}, entryDateTime {}, valueDateTime: {}",
            message.getJMSMessageID(),
            rateGroup.getCode(),
            rateGroup.getEntryDateTime(),
            rateGroup.getValueDateTime());
        if (!Objects.nonNull(rateGroup.getRateList())) {
            log.error("Rate list is null");
            return;
        }
        if (!Objects.nonNull(rateGroup.getRateList().getRate())) {
            log.error("Rate collection is null");
            return;
        }
        if (rateGroup.getRateList().getRate().isEmpty()) {
            log.error("Rate collection is empty");
            return;
        }

        rateGroup.getRateList().getRate().forEach(rate -> log.info("  Rate code: {}, currency {}, value: {}",
            rate.getCode(),
            rate.getCurrency(),
            rate.getValue().getLast()));
    }
}
