package com.antonio.persistence.config;

import com.antonio.persistence.entity.Product;
import com.antonio.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DbInit implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Value("${app.database.path}")
    private String dbPath;

    public DbInit(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional  // Добавляем транзакцию на метод run
    public void run(String... args) throws Exception {
        // Выводим путь к базе данных для отладки
        System.out.println("Database path: " + dbPath);

        // Создаем директорию, если её нет
        Path path = Paths.get(dbPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directory created: " + path.toString());
        }

        // Пример добавления нового продукта
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Test Product");
        product1.setPrice(99.99);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Test Product233223");
        product2.setPrice(32322332.0);

        // Сохраняем продукты
        productRepository.save(product1);
        productRepository.save(product2);

        // Выводим все продукты для проверки
        productRepository.findAll().forEach(p -> {
            System.out.println("Product in DB: " + p.getName());
        });
    }
}
