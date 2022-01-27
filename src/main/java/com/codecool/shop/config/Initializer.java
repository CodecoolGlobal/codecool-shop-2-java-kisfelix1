package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier valve = new Supplier("Valve", "Valve is a video game developer and publisher company");
        supplierDataStore.add(valve);
        Supplier opensea = new Supplier("OpenSea", "A peer-to-peer marketplace for NFTs");
        supplierDataStore.add(opensea);
        Supplier staub = new Supplier("Staub", "A quality knife producer.");
        supplierDataStore.add(staub);

        //setting up a new product category
        ProductCategory knife = new ProductCategory("Knife", "Cuisinart", "A product for cutting things up or to deal some damage.");
        productCategoryDataStore.add(knife);
        ProductCategory gun = new ProductCategory("Gun", "Guns", "A weapon of destruction and instrument of death. A good time killer too");
        productCategoryDataStore.add(gun);
        ProductCategory nft = new ProductCategory("NFT", "Digital art", "An overpriced digital picture for bragging reasons");
        productCategoryDataStore.add(nft);

        //setting up products and printing it
        productDataStore.add(new Product("M4A4 | Howl", new BigDecimal("2000"), "USD", "CS:GO digital skin. Get your hand on a rare peace of art now!", gun, valve));
        productDataStore.add(new Product("M4A4 | Poseidon", new BigDecimal("549.9"), "USD", "CS:GO digital skin. A mostly rare skin with rare condition. Feel the power of the sea!", gun, valve));
        productDataStore.add(new Product("AWP | Dragon lore", new BigDecimal("1556"), "USD", "CS:GO digital skin. A nice skin with nordic motives and a nice dragon art.", gun, valve));
        productDataStore.add(new Product("M4A1 | Imminent Danger", new BigDecimal("239"), "USD", "CS:GO digital skin. Warn others as you take the lead on the battlefield.", gun, valve));
        productDataStore.add(new Product("M9 Bayonet | Crimson Web", new BigDecimal("258"), "USD", "CS:GO digital skin. A knife with red color and a black web design on the middle.", knife, valve));
        productDataStore.add(new Product("Classic Knife", new BigDecimal("214"), "USD", "CS:GO digital skin. Feel the old times with this peace of art!", knife, valve));
        productDataStore.add(new Product("Kitchen Knife", new BigDecimal("64"), "USD", "A quality knife. Perfect for cutting meat and vegetables!", knife, staub));
    }
}
