package com.example.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {

    private String email;
    private String password;

    public com.example.firstproject.entity.Member toEntity() {
        return new com.example.firstproject.entity.Member(null, email, password);
    }
}
