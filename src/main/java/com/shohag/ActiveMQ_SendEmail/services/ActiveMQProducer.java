package com.shohag.ActiveMQ_SendEmail.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shohag.ActiveMQ_SendEmail.config.ActiveMQConfig;
import com.shohag.ActiveMQ_SendEmail.message.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

@Service
public class ActiveMQProducer {

    private static final Logger log = LoggerFactory.getLogger(ActiveMQProducer.class);

    private final JmsTemplate jmsTemplate;

    public ActiveMQProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(ActiveMQMessage activeMQMessage) throws JsonProcessingException {
        log.info("==** ActiveMQ Message: " + activeMQMessage);

        try {
            String jsonObj = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(activeMQMessage);

            jmsTemplate.send(ActiveMQConfig.EMAIL_PROCESSING_QUEUE, messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(jsonObj);
                return message;
            });
        } catch (Exception ex) {
            log.error("ERROR in sending message to queue");
        }

        log.info("Message has been sent !!!");
    }
}
