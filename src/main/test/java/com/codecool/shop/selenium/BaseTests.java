package com.codecool.shop.selenium;

import com.google.common.annotations.VisibleForTesting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTests {

    private WebDriver driver;

    public void setUp(){
        System.setProperty("webdriver.chrome.driver",
                "/home/emis/Projects/codecool/oop/week6/codecool-shop-2-java-kisfelix1/src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");

        System.out.println(driver.getTitle());
        driver.quit();

    }

    public static void main(String[] args) {
        BaseTests test = new BaseTests();
        test.setUp();
    }
}
