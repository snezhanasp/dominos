package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private double price;
    @Column(name = "picture_url")
    private String pictureURL;

    @OneToMany(mappedBy = "item")
    private List<OrderedItem> orderedItems;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany // todo set
    @JoinTable(
            name = "items_have_ingredients",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

}
