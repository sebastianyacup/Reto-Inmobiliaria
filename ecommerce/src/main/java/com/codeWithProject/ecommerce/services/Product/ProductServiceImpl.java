package com.codeWithProject.ecommerce.services.Product;

import com.codeWithProject.ecommerce.config.jwt.JwtRequestFilter;
import com.codeWithProject.ecommerce.entity.Cart;
import com.codeWithProject.ecommerce.entity.OrderDetail;
import com.codeWithProject.ecommerce.entity.Product;
import com.codeWithProject.ecommerce.entity.User;
import com.codeWithProject.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements  ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(int pageNumber, String searchKey){
        Pageable pageable = PageRequest.of(pageNumber, 8);

        if(searchKey.equals("")) {
            return (List<Product>) productRepository.findAll(pageable);
        }else {
            return (List<Product>)
                    productRepository.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase
                            (searchKey, searchKey, pageable);
        }

    }

    public void deleteProductDetails(Integer productId) {
         List<OrderDetail> orderDetails = orderDetailRepository.findByProductProductId(productId);

        if (!orderDetails.isEmpty()) {
            orderDetailRepository.deleteAll(orderDetails);
        }

        productRepository.deleteById(productId);
    }

    public Product getProductDetailsById(Integer productId) {

        return productRepository.findById(productId).get();
    }

    public List<Product> getProductDetails(boolean isSingeProductCheckout, Integer productId) {

        if(isSingeProductCheckout && productId != 0) {
            List<Product> list= new ArrayList<>();
            Product product = productRepository.findById(productId).get();
            list.add(product);
            return list;
        }else {

            String username = JwtRequestFilter.CURRENT_USER;
            User user = userRepository.findById(username).get();
            List<Cart>  carts= cartRepository.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());

        }


    }


}
