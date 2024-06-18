package mochegov.xmlrates.producer;

import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.jms.client.ActiveMQDestination;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmsService {
    private final JmsTemplate jmsTemplateAnyCast;
    private final JmsTemplate jmsTemplateMultiCast;
    private final Boolean sendByteMessage;

    public JmsService(@Qualifier("jmsTemplateAnyCast") JmsTemplate jmsTemplateAnyCast,
                      @Qualifier("jmsTemplateMultiCast") JmsTemplate jmsTemplateMultiCast,
                      @Value("${artemis.sendByteMessage}") Boolean sendByteMessage) {
        this.jmsTemplateAnyCast = jmsTemplateAnyCast;
        this.jmsTemplateMultiCast = jmsTemplateMultiCast;
        this.sendByteMessage = sendByteMessage;
    }

    public void sendAnyCastMessage(String queue,
                                   String message,
                                   String correlationID,
                                   String replyTopic,
                                   Map<String, String> messageHeaders) {
        log.info("Sending an AnyCast message to queue {}. Message: {}. CorrelationID: {}, ReplyTopic: {}",
            queue, message, correlationID, replyTopic);

        try {
            jmsTemplateAnyCast.send(queue, session -> {
                Message jmsMessage = session.createTextMessage(message);
                if (correlationID != null) {
                    jmsMessage.setJMSCorrelationID(correlationID);
                }
                if (replyTopic != null) {
                    jmsMessage.setJMSReplyTo(ActiveMQDestination.createDestination(RoutingType.ANYCAST, SimpleString.toSimpleString(replyTopic)));
                }

                messageHeaders.forEach((key, header) -> {
                    try {
                        jmsMessage.setStringProperty(key, header);
                    } catch (JMSException e) {
                        log.error("Error setting message header: {}", e.getMessage());
                    }
                });
                return jmsMessage;
            });

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendMultiCastMessage(String queue, String message) {
        log.info("Sending an MultiCast message to queue {}. Message: {}", queue, message);

        try {
            if (sendByteMessage) {
                jmsTemplateMultiCast.send(queue, session -> {
                    BytesMessage bytesMessage = session.createBytesMessage();
                    try {
                        bytesMessage.writeBytes(message.getBytes());
                    } catch (JMSException e) {
                        log.error("Error sending message: {}. Error text: {}", message, e.getMessage());
                    }
                    return bytesMessage;
                });
            } else {
                jmsTemplateMultiCast.convertAndSend(queue, message);
            }

            log.info("Message: {}", message);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
