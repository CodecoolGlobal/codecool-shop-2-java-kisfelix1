package com.codecool.shop.selenium;

import com.google.common.annotations.VisibleForTesting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.sql.Driver;

import static org.testng.Assert.assertEquals;

public class BaseTests {

    private static WebDriver driver;

    public void setUp(){
        System.setProperty("webdriver.chrome.driver",
                "/home/emis/Projects/codecool/oop/week6/codecool-shop-2-java-kisfelix1/src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }

    public static void main(String[] args) {
        BaseTests test = new BaseTests();
        test.setUp();
    }

    @Test
    public void testGetTitle(){
        BaseTests test = new BaseTests();
        test.setUp();
        String title = "Codecool Shop";
        assertEquals(driver.getTitle(), title, "Title is not matching");
        System.out.println(driver.getTitle() + " : driver.getTitle() result.");
        System.out.println(title + " : our result.");
        driver.quit();
    }
}
