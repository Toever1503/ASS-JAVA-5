package com.service.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ToString
public class OrderInfomationDto {
    @NotNull
    private String fullname;
    @NotNull
    @Pattern(
            regexp = "[0-9]{10}",
            message = "phone cannot exceed 10 numbers"
    )
    private String phone;
    @NotNull
    private String detail;
    @NotNull
    private Integer province;
    @NotNull
    private Integer district;
    @NotNull
    private Integer ward;

    private Long userId;
}
