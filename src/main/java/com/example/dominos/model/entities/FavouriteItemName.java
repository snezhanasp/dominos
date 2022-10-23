package com.example.dominos.model.entities;

import com.example.dominos.model.compositeKeys.FavouriteItemNameKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favourite_items")
public class FavouriteItemName {

    @EmbeddedId
    private FavouriteItemNameKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("orderedItemId")
    @JoinColumn(name = "ordered_item_id")
    private OrderedItem orderedItem;

    private String name;

}
