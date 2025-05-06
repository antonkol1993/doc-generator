package com.antonio.persistence.config;

import com.antonio.persistence.entity.Product;
import com.antonio.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DbInit implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Добавляем пример продукта в базу данных
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(99.99);

        // Сохраняем его в базу данных
        productRepository.save(product);

        // Выводим список всех продуктов
        productRepository.findAll().forEach(p -> System.out.println(p.getName()));
    }
}
