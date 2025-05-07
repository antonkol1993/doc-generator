package com.antonio.persistence.config;

import com.antonio.persistence.entity.TestProduct;
import com.antonio.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class DbInit implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DbInit(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Value("${app.database.path}")
    private String dbPath;


    @Override
    public void run(String... args) throws Exception {

//        // Добавляем пример продукта в базу данных
//        TestProduct testProduct = new TestProduct();
//        testProduct.setName("ANTON12131");
//        testProduct.setPrice(99.99);
//
//        // Сохраняем его в базу данных
//        productRepository.save(testProduct);
//
//        // Добавляем пример продукта в базу данных
//        TestProduct testProduct2 = new TestProduct();
//        testProduct2.setName("ANTON2");
//        testProduct2.setPrice(213231321.0);
//
//        // Сохраняем его в базу данных
//        productRepository.save(testProduct2);
//
//        // Выводим список всех продуктов
//        productRepository.findAll().forEach(p ->
//                System.out.println(p.getName()
//                ));

    }
}
