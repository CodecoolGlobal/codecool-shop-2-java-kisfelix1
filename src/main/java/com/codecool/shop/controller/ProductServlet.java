package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(urlPatterns = {"/api/filter"})
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String categoryId = req.getParameter("categoryId");
        String supplierId = req.getParameter("supplierId");
        List<Product> products = ProductDaoMem
                .getInstance()
                .getAll()
                .stream()
                .filter(product -> (product
                        .getProductCategory().getId()==Integer.parseInt(categoryId)||categoryId.equals("0"))&&
                        (product.getSupplier().getId()==Integer.parseInt(supplierId)||supplierId.equals("0")))
                .collect(Collectors.toList());
        out.println(new Gson().toJson(products));
        out.flush();
    }

}
