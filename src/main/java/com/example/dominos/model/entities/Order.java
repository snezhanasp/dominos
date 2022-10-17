package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
