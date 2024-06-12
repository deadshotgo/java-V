package com.example.dom.dto.auth;

import com.example.dom.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAuth {
    private String token;
    private User responseUser;

    public ResponseAuth(String token) {
        this.token = token;
    }

    public ResponseAuth(String token, User user) {
        this.token = token;
        this.responseUser = user;
    }
}
