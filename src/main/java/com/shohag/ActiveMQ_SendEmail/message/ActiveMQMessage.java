package com.shohag.ActiveMQ_SendEmail.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveMQMessage implements Serializable {

    private String userName;
    private String mailTo;
    private String mailFrom = "abc@gmail.com";
    private String subject = "Testing Mail API";
    private String text = "Hurray ! Message has been sent through ActiveMQ !!!";
    private String messageOption;
}
