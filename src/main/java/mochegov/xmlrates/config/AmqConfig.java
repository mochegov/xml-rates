package mochegov.xmlrates.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AmqConfig {

    @Bean
    @Primary
    public ConnectionFactory jmsConnectionFactory(AmqProperties properties) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
            properties.getMq().getBrokerUrl(),
            properties.getMq().getUser(),
            properties.getMq().getPassword());

        JmsPoolConnectionFactory jmsPoolConnectionFactory = new JmsPoolConnectionFactory();
        jmsPoolConnectionFactory.setConnectionFactory(connectionFactory);
        return jmsPoolConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplateAnyCast(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        var jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    @Bean
    public JmsTemplate jmsTemplateMultiCast(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        var jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setPubSubDomain(Boolean.TRUE);
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
        @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
        DefaultJmsListenerContainerFactoryConfigurer configurer) {

        var containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setPubSubDomain(Boolean.TRUE);
        containerFactory.setSubscriptionShared(Boolean.TRUE);
        containerFactory.setSubscriptionDurable(Boolean.TRUE);
        containerFactory.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        containerFactory.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONNECTION);
        containerFactory.setErrorHandler(t -> log.error("Jms error handler received a new error with message {}", t.getMessage()));
        containerFactory.setExceptionListener(e -> log.error("Jms exception listener received a new exception with message {}", e.getMessage()));

        configurer.configure(containerFactory, connectionFactory);

        return containerFactory;
    }
}
