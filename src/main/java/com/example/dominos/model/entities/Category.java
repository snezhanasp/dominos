package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;

    //todo set
    @OneToMany(mappedBy = "category")
    private List<Item> items;
}
