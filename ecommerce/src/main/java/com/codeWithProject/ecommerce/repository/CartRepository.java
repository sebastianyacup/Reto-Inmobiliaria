package com.codeWithProject.ecommerce.repository;

import com.codeWithProject.ecommerce.entity.Cart;
import com.codeWithProject.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Integer> {

    public List<Cart> findByUser(User user);

}