package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.email.EmailUtil;
import com.codecool.shop.model.CartProduct;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/api/sendEmail"})
public class EmailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append(System.lineSeparator());
        }
        String data = buffer.toString();
        try {
            String htmlTemplate = templateEngine(req, resp);
            EmailUtil.sendEmail(data, htmlTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String templateEngine(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CartDao cartDataStore = CartDaoMem.getInstance();
        List<CartProduct> cart = cartDataStore.getAll();
        double totalPrice = calculatePrice(cart);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("products", cart);
        context.setVariable("totalPrice", totalPrice);
        return engine.process("order_confirmation.html", context);
    }

    private double calculatePrice(List<CartProduct> cartDataStore) {
        float totalPrice = 0;
        for (CartProduct product : cartDataStore) {
            totalPrice += product.getDefaultPrice().floatValue() * product.getAmount();
        }
        return Math.round(totalPrice*100.0)/100.0;
    }
}
