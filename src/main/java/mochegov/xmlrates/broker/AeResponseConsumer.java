package mochegov.xmlrates.broker;

import jakarta.jms.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AeResponseConsumer {

    @JmsListener(destination = "${ae.queue.prototype.out}",
        containerFactory = "jmsListenerAnyCastContainerFactory")
    public void receiveCreateAccountMessage(TextMessage message, @Payload String messageBodyJson) {
        log.info("Received message from AE (prototype): {}", messageBodyJson);
    }
}
