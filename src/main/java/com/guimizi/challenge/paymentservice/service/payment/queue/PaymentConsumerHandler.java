package com.guimizi.challenge.paymentservice.service.payment.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.service.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessagingException;

@MessageEndpoint
public class PaymentConsumerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumerHandler.class);

    private ObjectMapper objectMapper;
    private PaymentService paymentService;

    @Autowired
    public PaymentConsumerHandler(ObjectMapper objectMapper, PaymentService paymentService) {
        this.objectMapper = objectMapper;
        this.paymentService = paymentService;
    }

    @ServiceActivator(inputChannel = "consumingChannel")
    public void handle(String message) throws MessagingException {
        LOGGER.info("Receiving message: " + message);
        Payment payment = null;
        try {
            payment = objectMapper.readValue(message, Payment.class);
        } catch (JsonProcessingException e) {
           //TODO
            e.printStackTrace();
        }
        paymentService.processPayment(payment);
    }

}
