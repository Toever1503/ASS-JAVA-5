package com.model;

import lombok.Data;

@Data
public class UserChangePassword {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
