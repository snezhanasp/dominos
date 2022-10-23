package com.example.dominos.model.repositories;

import com.example.dominos.model.compositeKeys.FavouriteItemNameKey;
import com.example.dominos.model.entities.FavouriteItemName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteItemRepository extends JpaRepository<FavouriteItemName, FavouriteItemNameKey> {
}
