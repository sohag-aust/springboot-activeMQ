package com.shohag.ActiveMQ_SendEmail.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shohag.ActiveMQ_SendEmail.message.ActiveMQMessage;
import com.shohag.ActiveMQ_SendEmail.request.UserCreateRequest;
import com.shohag.ActiveMQ_SendEmail.services.ActiveMQProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final ActiveMQProducer activeMQProducer;

    public EmailController(ActiveMQProducer activeMQProducer) {
        this.activeMQProducer = activeMQProducer;
    }

    @PostMapping("/sendEmail")
    public String register(@RequestBody UserCreateRequest userCreateRequest) throws JsonProcessingException {

        ActiveMQMessage activeMQMessage = new ActiveMQMessage();
        activeMQMessage.setUserName(userCreateRequest.getUserName());
        activeMQMessage.setMailTo(userCreateRequest.getEmail());
        activeMQMessage.setMessageOption("simpleEmail");
        activeMQProducer.sendMessage(activeMQMessage);

        return "Congratulations! You have sent email successfully...";
    }


    @PostMapping("/sendEmailWithAttachment")
    public String sendWithAttachment(@RequestBody UserCreateRequest userCreateRequest) throws JsonProcessingException {

        ActiveMQMessage activeMQMessage = new ActiveMQMessage();
        activeMQMessage.setUserName(userCreateRequest.getUserName());
        activeMQMessage.setMailTo(userCreateRequest.getEmail());
        activeMQMessage.setMessageOption("EmailWithAttachment");
        activeMQProducer.sendMessage(activeMQMessage);

        return "Congratulations! You have sent email with Attachment successfully...";
    }
}
