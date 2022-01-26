package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "addCart", urlPatterns = {"/api/add_cart"})
public class AddCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String itemId = req.getParameter("itemId");
        Product item = ProductDaoMem
                .getInstance()
                .find(Integer.parseInt(itemId));
        CartDao cart = CartDaoMem.getInstance();
        if (cart.find(item.getId()) != null) {
            cart.edit(1, item.getId());
        } else {
            cart.add(item, 1);
        }
        String json = new Gson().toJson(item.getName() + "added to cart");

        PrintWriter out = resp.getWriter();
        out.println(json);
        out.flush();
    }
}
