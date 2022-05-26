package com.model;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

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
    @Value("false")
    private Boolean isRemember;

}
