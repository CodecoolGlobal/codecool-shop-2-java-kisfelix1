package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {
    private DataSource dataSource;
    private List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;
    private static ProductCategoryDaoMem productCategoryDaoMem;
    private static SupplierDaoMem supplierDaoMem;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
        try {
            dataSource = DatabaseManager.connect();
            productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
            supplierDaoMem = SupplierDaoMem.getInstance();
        }catch(SQLException e){
            System.out.println("Couldn't connect to the database!");
        }
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        try(Connection connection = dataSource.getConnection()){
            String sql = "SELECT id, product_name, price, currency, description, image_path, supplier_id, category_id FROM product WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) { // first row was not found == no data was returned by the query
                return null;
            }
            Product product = new Product(rs.getString(2),
                    rs.getBigDecimal(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    productCategoryDaoMem.find(rs.getInt(8)),
                    supplierDaoMem.find(rs.getInt(7)));
            product.setId(rs.getInt(1));
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id FROM product";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Integer id : ids) {
            products.add(find(id));
        }
        return products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return getAll().stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return getAll().stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }
}
