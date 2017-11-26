package com.hilum.sso.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UsernamePasswordLoginRequest {
    private boolean create = true;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
