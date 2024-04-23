package com.codeWithProject.ecommerce.services.Role;

import com.codeWithProject.ecommerce.entity.Role;
import com.codeWithProject.ecommerce.repository.RoleRepository;
import com.codeWithProject.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
