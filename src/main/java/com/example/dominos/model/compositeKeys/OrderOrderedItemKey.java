package com.example.dominos.model.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderOrderedItemKey implements Serializable {

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "ordered_item_id")
    private long orderedItemId;
}
