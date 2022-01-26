package com.codecool.shop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class EmailModel {
    private String productName;
    private List<Product> products;

    public EmailModel(String productName, List<Product> products) {
        this.productName = productName;
        this.products = products;
    }

    public String getProductName() {
        return productName;
    }

    public List<Product> getProducts() {
        return products;
    }
}
