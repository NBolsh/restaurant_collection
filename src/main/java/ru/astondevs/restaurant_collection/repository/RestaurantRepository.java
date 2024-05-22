package ru.astondevs.restaurant_collection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.astondevs.restaurant_collection.entity.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAllByCity(String city);

    @Query(value = "SELECT * FROM restaurant order by average_rating desc", nativeQuery = true)
    List<Restaurant> findAllSortedByRating();
}
