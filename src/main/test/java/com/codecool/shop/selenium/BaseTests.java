package com.codecool.shop.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;


import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BaseTests {

    private static WebDriver driver;

    private void setUp(){
        System.setProperty("webdriver.chrome.driver",
                "/home/emis/Projects/codecool/oop/week6/codecool-shop-2-java-kisfelix1/src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }


    @Test
    public void testGetTitle(){
        setUp();
        String title = "Codecool Shop";
        assertEquals(driver.getTitle(), title, "Title is not matching");
        System.out.println(driver.getTitle() + " : driver.getTitle() result.");
        System.out.println(title + " : our result.");
        driver.quit();
    }


    @Test
    public void testSelectOption(){
        setUp();
        driver.manage().window().maximize();
        String option = "NFT";
        findDropDownElement().selectByVisibleText(option);
        var selectedOptions = getSelectedOption();
        assertTrue(selectedOptions.contains(option), "Option not selected");
        driver.quit();

    }

    private List<String> getSelectedOption(){
        List<WebElement> selectedElements =  findDropDownElement().getAllSelectedOptions();
        return selectedElements.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    private Select findDropDownElement(){
        return new Select(driver.findElement(By.id("categories")));
    }
}
