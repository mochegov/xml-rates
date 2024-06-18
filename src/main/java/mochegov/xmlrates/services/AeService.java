package mochegov.xmlrates.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.ae.PrototypeRequestDto;
import mochegov.xmlrates.producer.JmsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AeService {
    private final JmsService jmsService;
    private final ObjectMapper objectMapper;

    public AeService(JmsService jmsService,
                     ObjectMapper objectMapper) {
        this.jmsService = jmsService;
        this.objectMapper = objectMapper;
    }

    public String sendMessage(PrototypeRequestDto dto, String queueIn, String queueOut) {
        try {
            Map<String, String> headers = Map.of("apiVersion", "v1");
            jmsService.sendAnyCastMessage(queueIn, objectMapper.writeValueAsString(dto), UUID.randomUUID().toString(), queueOut, headers);
            return "Message for AE was successfully sent";
        } catch (JsonProcessingException e) {
            return "Error: %s".formatted(e.getMessage());
        }
    }
}
