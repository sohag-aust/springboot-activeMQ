package com.shohag.ActiveMQ_SendEmail.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequest {

    @JsonProperty("user_name")
    private String userName;
    private String email;
    private String password;
}
