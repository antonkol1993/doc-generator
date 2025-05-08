package com.antonio.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "object_main")
public class ObjectToGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "rus_name")
    private String rusName;

    @Column(name = "1C8_name")
    private String To1C8name;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "tn_ved_code")
    private String tn_ved_code;

    @Column(name = "producer")
    private String producer;

}
