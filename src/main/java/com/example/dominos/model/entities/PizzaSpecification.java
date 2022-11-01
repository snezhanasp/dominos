package com.example.dominos.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity(name = "pizza_specifications")
@EqualsAndHashCode
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


    @Data
    @Entity(name = "sizes")
    @EqualsAndHashCode
    public static class Size{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column
        private String name;
        @Column
        private double price;
    }

    @Data
    @Entity(name = "dough_types")
    @EqualsAndHashCode
    public static class DoughType{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column
        private String name;
        @Column
        private double price;
    }
}
