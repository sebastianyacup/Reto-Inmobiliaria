package com.codeWithProject.ecommerce.services.Cart;

import com.codeWithProject.ecommerce.entity.Cart;

import java.util.List;

public interface CartService {
    void deleteCartItem(Integer cartId);
    Cart addToCart(Integer productId);
    List<Cart> getCartDetails();
}
