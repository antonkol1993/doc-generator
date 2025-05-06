package com.antonio.persistence.config;

import com.antonio.persistence.entity.Product;
import com.antonio.persistence.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void addProduct(Product product) {
        productRepository.save(product);  // Сохраняем продукт
    }
}

