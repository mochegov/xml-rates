package mochegov.xmlrates.producer;

import static ru.raiffeisen.gl.common.clients.GlHttpClient.COMMON_OBJECT_MAPPER;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JmsService {
    private final JmsTemplate jmsTemplate;

    public <T> void sendAsyncMessage(String queue, T message) {
        log.info("Sending a message to queue {}. Message: {}", queue, message);

        try {
            jmsTemplate.convertAndSend(queue, COMMON_OBJECT_MAPPER.writeValueAsString(message));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
