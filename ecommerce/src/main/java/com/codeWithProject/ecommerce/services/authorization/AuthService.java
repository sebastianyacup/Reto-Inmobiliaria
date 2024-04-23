package com.codeWithProject.ecommerce.services.authorization;

import com.codeWithProject.ecommerce.entity.User;

public interface AuthService {
    void initRoleAndUser();
    User registerNewUser(User user);
    String getEncodedPassword(String password);
}
