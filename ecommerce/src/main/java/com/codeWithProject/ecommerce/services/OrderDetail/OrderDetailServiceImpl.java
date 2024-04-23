package com.codeWithProject.ecommerce.services.OrderDetail;

import com.codeWithProject.ecommerce.config.jwt.JwtRequestFilter;
import com.codeWithProject.ecommerce.dto.OrderInput;
import com.codeWithProject.ecommerce.dto.OrderProduc;
import com.codeWithProject.ecommerce.entity.*;
import com.codeWithProject.ecommerce.repository.CartRepository;
import com.codeWithProject.ecommerce.repository.OrderDetailRepository;
import com.codeWithProject.ecommerce.repository.ProductRepository;
import com.codeWithProject.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    private static final String ORDER_PLACED = "Placed";

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<OrderDetail> getAllOrderDetails(){
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetailRepository.findAll().forEach(e -> orderDetails.add(e));

        return orderDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userRepository.findById(currentUser).get();

        return orderDetailRepository.findByUser(user);
    }

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProduc> productQuantityList = orderInput.getOrderProductQuantityList();

        for(OrderProduc o: productQuantityList) {
            Product product = productRepository.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user= userRepository.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice()*o.getQuantity(),
                    product,
                    user);

            if(!isSingleProductCheckout) {
                List<Cart> carts= cartRepository.findByUser(user);
                carts.stream().forEach(x -> cartRepository.deleteById(x.getCartId()));

            }
            orderDetailRepository.save(orderDetail);
        }
    }


}
