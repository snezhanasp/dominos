package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;

    @OneToMany(mappedBy = "payment")
    private List<Order> orders;
}
