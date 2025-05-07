package com.antonio.persistence.repository;

import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.entity.TestProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ObjectToGeneratorRepository extends JpaRepository<ObjectToGenerator, Long> {
}
