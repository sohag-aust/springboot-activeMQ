package com.shohag.ActiveMQ_SendEmail.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shohag.ActiveMQ_SendEmail.config.ActiveMQConfig;
import com.shohag.ActiveMQ_SendEmail.message.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.mail.MessagingException;

@Service
public class ActiveMQConsumer {

    private static final Logger log = LoggerFactory.getLogger(ActiveMQConsumer.class);

    private final MailService mailService;

    public ActiveMQConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @JmsListener(destination = ActiveMQConfig.EMAIL_PROCESSING_QUEUE)
    public void consumeDefaultMessage(final Message jsonMessage) throws JMSException, JsonProcessingException, MessagingException {
        log.info("==** Message from Queue is : " + jsonMessage);
        String messageData = null;

        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            messageData = textMessage.getText();
            log.info("messageData: " + messageData);
        }

        ObjectMapper mapper = new ObjectMapper();

        if(messageData != null) {
            ActiveMQMessage activeMQMessage = mapper.readValue(messageData, ActiveMQMessage.class);

            log.info("==** Final ActiveMQMessage: " + activeMQMessage);

            if(activeMQMessage.getMessageOption().equals("EmailWithAttachment")) {
                mailService.sendEmailWithAttachment(activeMQMessage);
            }else {
                mailService.sendEmail(activeMQMessage);
            }
        }
    }
}
