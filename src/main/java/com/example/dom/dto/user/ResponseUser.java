package com.example.dom.dto.user;

import com.example.dom.entity.UserEntity;
import lombok.Data;

@Data
public class ResponseUser {
    private Long id;
    private String username;
    private String email;

    public static ResponseUser toModel(UserEntity entity) {
        ResponseUser model = new ResponseUser();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        return model;
    }
}
