package com.example.dominos.model.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private boolean isModifiable;

    @OneToMany(mappedBy = "category")
    private List<Item> items;

}
