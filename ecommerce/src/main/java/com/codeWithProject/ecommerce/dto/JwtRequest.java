package com.codeWithProject.ecommerce.dto;

import lombok.Data;


@Data
public class JwtRequest {
    private String userName;
    private String userPassword;
}
