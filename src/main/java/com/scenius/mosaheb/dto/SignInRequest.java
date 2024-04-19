package com.scenius.mosaheb.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String phone;
    private String password;
}
