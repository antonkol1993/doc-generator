package com.antonio.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;


}


