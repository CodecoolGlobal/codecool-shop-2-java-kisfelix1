package com.codecool.shop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class EmailModel {
    private String productName;
    private List<Product> products;
    private AddressModel address;
    private LocalDateTime shippingDate;

    public EmailModel(String productName, List<Product> products, AddressModel address) {
        this.productName = productName;
        this.products = products;
        this.address = address;
        this.shippingDate = LocalDateTime.now().plusDays(7);
    }

    public String getProductName() {
        return productName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public AddressModel getAddress() {
        return address;
    }

    public String getShippingDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return shippingDate.format(formatter);
    }
}
