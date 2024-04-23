package com.codeWithProject.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderInput {

    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private String alternateContactNumber;
    private List<OrderProduc> orderProductQuantityList;
}
