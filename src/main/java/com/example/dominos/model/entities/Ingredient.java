package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //todo set
    @ManyToMany
    @JoinTable(
            name = "items_have_ingredients",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    @ManyToMany
    @JoinTable(
            name = "ordered_items_have_ingredients",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "ordered_item_id")
    )
    private List<OrderedItem> orderedItems;
}
