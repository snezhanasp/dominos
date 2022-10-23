package com.example.dominos.model.compositeKeys;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class FavouriteItemNameKey implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "ordered_item_id")
    private long orderedItemId;
}
