package org.slix.mproxy.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.IndividualDeadLetterStrategy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Slf4j
@Configuration
@EnableJms
@RequiredArgsConstructor
@EnableConfigurationProperties(ActiveMQProperties.class)
public class JmsConfig {

    private final ActiveMQProperties activeMQProperties;

    public final static String FORWARD_QUEUE_NAME = "forward-queue";

    @Bean
    public Queue forwardQueue() {
        return new ActiveMQQueue(FORWARD_QUEUE_NAME);
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setErrorHandler(t -> log.error("An error with payload sending " + t.getMessage()));
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(activeMQProperties.getBrokerUrl());
        final RedeliveryPolicy redeliveryPolicy = activeMQConnectionFactory.getRedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(50000);
        redeliveryPolicy.setBackOffMultiplier(2);
        redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setMaximumRedeliveryDelay(5000);

        return activeMQConnectionFactory;
    }

    @Bean
    public DeadLetterStrategy deadLetterStrategy() {
        IndividualDeadLetterStrategy deadLetterStrategy = new IndividualDeadLetterStrategy();
        deadLetterStrategy.setQueueSuffix(".dlq");
        deadLetterStrategy.setUseQueueForQueueMessages(true);
        return deadLetterStrategy;
    }

}
