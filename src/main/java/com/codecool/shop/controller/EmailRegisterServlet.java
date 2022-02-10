package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.email.EmailUtil;
import com.codecool.shop.model.CartProduct;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api/sendRegisterEmail"})
public class EmailRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append(System.lineSeparator());
        }
        System.out.println(buffer);
        String data = buffer.toString();
        System.out.println(data);
        System.out.println("krisz2098@gmail.com");
        try {
            String htmlTemplate = templateEngine(req, resp, data);
            EmailUtil.sendEmail(data, htmlTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String templateEngine(HttpServletRequest req, HttpServletResponse resp, String email) throws IOException {

        String correctEmail = email.replace("\"", "").replace("\n", "");
        UserDao userData = UserDaoMem.getInstance();
        String name = userData.find(correctEmail);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("username", name);
        return engine.process("register_confirmation.html", context);
    }
}
