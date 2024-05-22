package ru.astondevs.restaurant_collection.service;

import ru.astondevs.restaurant_collection.dto.RestaurantDto;
import ru.astondevs.restaurant_collection.entity.Restaurant;

import java.util.List;


public interface RestaurantService {
    Restaurant findById(Integer id);

    List<Restaurant> findAll();

    List<Restaurant> findAllByCity(String city);

    Restaurant updateRatingAndVotes(Integer id, RestaurantDto dto);

    void create(Restaurant restaurant);

    void deleteById(Integer id);

    List<Restaurant> findAllSortedByRating();
}
