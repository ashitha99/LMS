package com.swiz.lms.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequest {

    private String email;
    private String password;

}
