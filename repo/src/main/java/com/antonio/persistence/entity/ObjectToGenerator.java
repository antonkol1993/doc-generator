package com.antonio.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


@Data
@Entity(name = "object_main")
public class ObjectToGenerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Value("orig_name")
    private String originalName;

    @Value("rus_name")
    private String rusName;

    @Value("1C8_name")
    private String To1C8name;

    @Value("imagePath")
    private String imagePath;



}
