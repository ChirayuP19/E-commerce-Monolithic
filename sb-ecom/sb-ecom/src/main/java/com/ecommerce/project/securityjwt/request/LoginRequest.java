package com.ecommerce.project.securityjwt.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    private String username;
    private String password;

}
