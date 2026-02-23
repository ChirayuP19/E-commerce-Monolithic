package com.ecommerce.project.securityjwt.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Data
@RequiredArgsConstructor
public class UserInfoResponse {

    public UserInfoResponse(Long id, String username, String jwtCookie, List<String> roles, LocalDateTime time) {
        this.id=id;
        this.username = username;
        this.jwtCookie = jwtCookie;
        this.roles = roles;
        this.time=time;
    }

    private Long id;
    private String jwtCookie;
    private String username;
    private LocalDateTime time;

    private List<String> roles;

    public UserInfoResponse(Long id, String username, List<String> roles, LocalDateTime time) {
        this.id=id;
        this.username = username;
        this.roles = roles;
        this.time=time;
    }
}
