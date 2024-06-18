package mochegov.xmlrates.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "artemis")
public class AmqProperties {
    private ActiveMQProperties mq;
    private String queue;
    private Boolean byteMessage;
}
