package com.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLogin {

    @NotBlank
    @Max(value = 255)
    private String username;

    @NotNull
    @Max(value = 255)
    @Min(value = 6)
    private String password;

    @NotNull
    private Boolean isRemember;
}
