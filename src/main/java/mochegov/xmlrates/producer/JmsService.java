package mochegov.xmlrates.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JmsService {
    private final @Qualifier("jmsTemplateAnyCast") JmsTemplate jmsTemplateAnyCast;
    private final @Qualifier("jmsTemplateMultiCast") JmsTemplate jmsTemplateMultiCast;

    public void sendAnyCastMessage(String queue, String message) {
        log.info("Sending an AnyCast message to queue {}. Message: {}", queue, message);

        try {
            jmsTemplateAnyCast.convertAndSend(queue, message);
            log.info("Message: %s".formatted(message));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendMultiCastMessage(String queue, String message) {
        log.info("Sending an MultiCast message to queue {}. Message: {}", queue, message);

        try {
            jmsTemplateMultiCast.convertAndSend(queue, message);
            log.info("Message: %s".formatted(message));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
