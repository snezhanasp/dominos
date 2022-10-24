package com.example.dominos.service;

import com.example.dominos.model.compositeKeys.FavouriteItemNameKey;
import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.item.CreatePizzaDTO;
import com.example.dominos.model.dto.item.PizzaInfoDTO;
import com.example.dominos.model.dto.pizza_specification.DoughDTO;
import com.example.dominos.model.dto.pizza_specification.SizeDTO;
import com.example.dominos.model.entities.*;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FavouritesService extends AbstractService{
    public PizzaInfoDTO addNewPizza(CreatePizzaDTO dto, long uid) {
        User user = getUserById(uid);
        OrderedItem orderedItem = orderedItemRepository.save(createItem(dto));
        double price = checkPrice(orderedItem);

        FavouriteItemNameKey key = new FavouriteItemNameKey(user.getId(), orderedItem.getId());
        FavouriteItemName favouriteItemName = new FavouriteItemName(key,user,orderedItem, dto.getName());
        favouriteItemRepository.save(favouriteItemName);

        PizzaInfoDTO pizzaInfoDTO = modelMapper.map(dto, PizzaInfoDTO.class);//name,dough type,size,ingredients
        pizzaInfoDTO.setId(orderedItem.getId());
        pizzaInfoDTO.setPrice(price);
        pizzaInfoDTO.setCategory(modelMapper.map(orderedItem.getItem().getCategory(), CategoryWithoutItemsDTO.class));
        return pizzaInfoDTO;
    }

    public boolean removePizzaFromFavourites(long pid, long uid) {
        //check user is in db
        User user = getUserById(uid);
        FavouriteItemNameKey key = new FavouriteItemNameKey(uid,pid);
        //check user has favourite pizza in db
        FavouriteItemName name = favouriteItemRepository.findById(key).orElseThrow(() -> new NotFoundException("Pizza not found!"));
        favouriteItemRepository.delete(name);
        return true;
    }

    private OrderedItem createItem(CreatePizzaDTO dto) {
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setItem(getItemById(dto.getItemId()));

        PizzaSpecification.Size size = modelMapper.map(dto.getSize(), PizzaSpecification.Size.class);
        PizzaSpecification.DoughType doughType = modelMapper.map(dto.getDoughType(), PizzaSpecification.DoughType.class);
        orderedItem.setPizzaSpecification(pizzaSpecificationRepository.findBySize_IdAndDoughType_Id(size.getId(),doughType.getId()).orElseThrow(()->new NotFoundException("Pizza specification not found!")));

        List<Ingredient> ingredients = dto.getIngredients().stream().map(i -> modelMapper.map(i, Ingredient.class)).toList();
        orderedItem.setIngredients(ingredients);

        return orderedItem;
    }

    private double checkPrice(OrderedItem orderedItem) {
        double price = 0;
        price += orderedItem.getItem().getPrice();
        price += orderedItem.getPizzaSpecification().getSize().getPrice();
        price += orderedItem.getPizzaSpecification().getDoughType().getPrice();
        price += orderedItem.getIngredients().stream().map(i -> i.getIngredientType().getPrice()).reduce(0.0, Double::sum);
        return price;
    }

    public List<PizzaInfoDTO> getAllFavourites(long uid) {
        User user = getUserById(uid);
        Set<FavouriteItemName> favourites = user.getFavourites();
        if (favourites.isEmpty()){
            throw new NotFoundException("User has no favourite pizzas!");
        }
        List<PizzaInfoDTO> dtos = new ArrayList<>();
        for (FavouriteItemName favourite : favourites) {

            PizzaInfoDTO dto = modelMapper.map(favourite,PizzaInfoDTO.class);
            SizeDTO size = modelMapper.map(favourite.getOrderedItem().getPizzaSpecification().getSize(), SizeDTO.class);
            DoughDTO dough = modelMapper.map(favourite.getOrderedItem().getPizzaSpecification().getDoughType(), DoughDTO.class);
            CategoryWithoutItemsDTO category = modelMapper.map(favourite.getOrderedItem().getItem().getCategory(),CategoryWithoutItemsDTO.class);

            dto.setPrice(checkPrice(favourite.getOrderedItem()));
            dto.setName(favourite.getName());
            dto.setCategory(category);
            dto.setDoughType(dough);
            dto.setSize(size);
            dto.setId(favourite.getOrderedItem().getId());
            dtos.add(dto);
        }
        return dtos;
    }
}
