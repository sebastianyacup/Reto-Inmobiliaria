package com.codeWithProject.ecommerce.services.OrderDetail;

import com.codeWithProject.ecommerce.entity.OrderDetail;
import com.codeWithProject.ecommerce.dto.OrderInput;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    List<OrderDetail> getOrderDetails();
    void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout);

}
