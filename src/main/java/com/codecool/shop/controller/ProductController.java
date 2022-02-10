package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.jdbc.DatabaseManager;
import com.codecool.shop.dao.implementation.jdbc.CartDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJDBC;
import com.codecool.shop.dao.implementation.mem.CartDaoMem;
import com.codecool.shop.dao.implementation.mem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.mem.ProductDaoMem;
import com.codecool.shop.dao.implementation.mem.SupplierDaoMem;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String dbInput = getDatabaseType();
        try {
            createDbConnection(dbInput);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ProductService productService = ProductService.getInstance();
        context.setVariable("products", productService.getProductsForCategory(0));
        context.setVariable("categories", productService.getAllCategories());
        context.setVariable("suppliers", productService.getAllSuppliers());
        engine.process("product/index.html", context, resp.getWriter());
    }

    private String getDatabaseType(){
        System.out.println("jdbc / memory");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("jdbc") && !input.equals("memory")){
            input = scanner.nextLine();
        }
        return input;
    }

    private void createDbConnection(String dbInput) throws SQLException {
        ProductDao productDataStore;
        ProductCategoryDao productCategoryDataStore;
        SupplierDao supplierDataStore;
        CartDao cartDataStore;

        if (dbInput.equals("jdbc")) {
            DatabaseManager databaseManager = new DatabaseManager();
            DataSource dataSource = databaseManager.getDataSource();
            productCategoryDataStore = ProductCategoryDaoJDBC.getInitialInstance(dataSource);
            supplierDataStore = SupplierDaoJDBC.getInitialInstance(dataSource);
            productDataStore = ProductDaoJDBC.getInitialInstance(dataSource, productCategoryDataStore, supplierDataStore);
            cartDataStore = CartDaoJDBC.getInitialInstance(dataSource);

        } else {
            productDataStore = ProductDaoMem.getInstance();
            productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            supplierDataStore = SupplierDaoMem.getInstance();
            cartDataStore = CartDaoMem.getInstance();
        }
        ProductService.createInitialInstance(productDataStore, productCategoryDataStore, supplierDataStore);
        CartService.createInitialInstance(cartDataStore);
    }

}
