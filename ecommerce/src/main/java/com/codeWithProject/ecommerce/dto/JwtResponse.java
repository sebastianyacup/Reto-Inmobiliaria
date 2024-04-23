package com.codeWithProject.ecommerce.dto;

import com.codeWithProject.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor

public class JwtResponse {
    private User user;
    private String jwtToken;
}
