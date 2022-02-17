package com.model;

import lombok.Data;

@Data
public class UserRegister {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private long role;
}
