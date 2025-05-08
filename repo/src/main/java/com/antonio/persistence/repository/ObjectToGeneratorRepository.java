package com.antonio.persistence.repository;

import com.antonio.persistence.entity.ObjectToGenerator;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ObjectToGeneratorRepository extends JpaRepository<ObjectToGenerator, Long> {
}
