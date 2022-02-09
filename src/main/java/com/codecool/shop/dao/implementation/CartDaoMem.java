package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.CartProduct;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {
    private DataSource dataSource;
    private List<CartProduct> data = new ArrayList<>();
    private static CartDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
        try {
            dataSource = DatabaseManager.connect();
        }catch (SQLException e){
            System.out.println("Couldn't connect to database!");
        }
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product, int amount) {
        data.add(new CartProduct(product, amount));
    }

    @Override
    public void edit(int amount, int id) {
        CartProduct cartProduct = find(id);
            if (cartProduct.getAmount() + amount < 1) {
                data.remove(cartProduct);
            } else {
                cartProduct.setAmount(cartProduct.getAmount() + amount);
            }
    }

    @Override
    public CartProduct find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public BigDecimal getTotalCartPrice(){
        return data.stream()
                .map(e -> e.getDefaultPrice()
                        .multiply(BigDecimal.valueOf(e.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<CartProduct> getAll() {
        return data;
    }
}
