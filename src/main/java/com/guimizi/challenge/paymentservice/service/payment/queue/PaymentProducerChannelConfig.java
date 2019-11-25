package com.guimizi.challenge.paymentservice.service.payment.queue;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHandler;

@Configuration
public class PaymentProducerChannelConfig {

    @Value("${payment.queue}")
    private String integrationDestination;

    @Bean
    public DirectChannel producingChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "producingChannel")
    public MessageHandler jmsMessageHandler(JmsTemplate jmsTemplate) {
        JmsSendingMessageHandler handler =
                new JmsSendingMessageHandler(jmsTemplate);
        handler.setDestinationName(integrationDestination);

        return handler;
    }

}
