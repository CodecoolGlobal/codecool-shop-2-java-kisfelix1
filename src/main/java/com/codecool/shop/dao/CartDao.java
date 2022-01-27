package com.codecool.shop.dao;

import com.codecool.shop.model.CartProduct;
import com.codecool.shop.model.Product;

import java.util.List;

public interface CartDao {

    void add(Product product, int amount);
    void edit(int value, int id);
    Product find(int id);
    void remove(int id);

    List<CartProduct> getAll();
}
