package com.codeWithProject.ecommerce.services.Cart;

import com.codeWithProject.ecommerce.config.jwt.JwtRequestFilter;
import com.codeWithProject.ecommerce.entity.Cart;
import com.codeWithProject.ecommerce.entity.Product;
import com.codeWithProject.ecommerce.entity.User;
import com.codeWithProject.ecommerce.repository.CartRepository;
import com.codeWithProject.ecommerce.repository.ProductRepository;
import com.codeWithProject.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements  CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public void deleteCartItem(Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    public Cart addToCart(Integer productId) {

        Product product = productRepository.findById(productId).get();

        String username = JwtRequestFilter.CURRENT_USER;

        User user = null;

        if(username != null) {
            user = userRepository.findById(username).get();

        }

        List<Cart> cartList = cartRepository.findByUser(user);
        List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId)
                .collect(Collectors.toList());

        if(filteredList.size() > 0) {
            return null;
        }


        if(product != null && user != null) {
            Cart cart = new Cart(product, user);
            return cartRepository.save(cart);
        }
        return null;
    }

    public List<Cart> getCartDetails(){
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userRepository.findById(username).get();
        return cartRepository.findByUser(user);

    }
}
