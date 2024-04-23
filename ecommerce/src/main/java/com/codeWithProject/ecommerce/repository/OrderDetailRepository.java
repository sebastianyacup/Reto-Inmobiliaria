package com.codeWithProject.ecommerce.repository;

import com.codeWithProject.ecommerce.entity.OrderDetail;
import com.codeWithProject.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

     List<OrderDetail> findByUser(User user);
     List<OrderDetail> findByProductProductId(Integer productId);


}