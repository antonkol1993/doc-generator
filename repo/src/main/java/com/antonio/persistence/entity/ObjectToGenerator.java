package com.antonio.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "objects")
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

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "tn_ved_code")
    private String tnVedCode;;

    @Column(name = "producer")
    private String producer;

}
