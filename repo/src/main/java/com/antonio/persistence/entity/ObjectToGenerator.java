package com.antonio.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "object_main")
public class ObjectToGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orig_name")
    private String originalName;

    @Column(name = "rus_name")
    private String rusName;

    @Column(name = "1C8_name")
    private String To1C8name;

    @Column(name = "image_path")
    private String imagePath;
}
