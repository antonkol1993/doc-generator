package com.antonio.persistence.config;

import com.antonio.persistence.entity.Product;
import com.antonio.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Path path = Paths.get(dbPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        // Добавляем пример продукта в базу данных
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(99.99);

        // Сохраняем его в базу данных
        productRepository.save(product);

        // Добавляем пример продукта в базу данных
        Product product2 = new Product();
        product2.setName("Test Product222323");
        product2.setPrice(213231321.0);

        // Сохраняем его в базу данных
        productRepository.save(product2);

        // Выводим список всех продуктов
        productRepository.findAll().forEach(p ->
                System.out.println(p.getName()
                ));

    }
}
