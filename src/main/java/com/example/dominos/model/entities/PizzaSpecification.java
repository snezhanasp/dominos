package com.example.dominos.model.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity(name = "pizza_specifications")
public class PizzaSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "dough_id")
    private DoughType doughType;

    @OneToMany(mappedBy = "pizzaSpecification")
    private List<OrderedItem> orderedItems;

    @Data
    @Entity(name = "sizes")
    public static class Size{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column
        private String name;
        @Column
        private double price;
        @OneToMany(mappedBy = "size")
        private List<PizzaSpecification> pizzaSpecifications;
    }

    @Data
    @Entity(name = "dough_types")
    public static class DoughType{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column
        private String name;
        @Column
        private double price;
        @OneToMany(mappedBy = "doughType")
        private List<PizzaSpecification> pizzaSpecifications;
    }
}
