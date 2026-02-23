package com.ecommerce.project.securityjwt.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class UserInfoResponse {

    public UserInfoResponse(Long id,String username, String jwtToken, List<String> roles,LocalDateTime time) {
        this.id=id;
        this.username = username;
        this.jwtToken=jwtToken;
        this.roles = roles;
        this.time=time;
    }

    private Long id;
    private String jwtToken;
    private String username;
    private LocalDateTime time;

    private List<String> roles;

}
