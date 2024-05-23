package ru.astondevs.restaurant_collection.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.restaurant_collection.dto.RestaurantDto;
import ru.astondevs.restaurant_collection.entity.Restaurant;
import ru.astondevs.restaurant_collection.repository.RestaurantRepository;
import ru.astondevs.restaurant_collection.service.RestaurantService;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private final RestaurantRepository repository;

    @Override
    public Restaurant findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id - " + id + "не был найден"));
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Restaurant> findAllByCity(String city) {
        return repository.findAllByCity(city);
    }

    @Override
    @Transactional
    public Restaurant updateRatingAndVotes(Integer id, RestaurantDto dto) {
        Restaurant entity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id: " + id + " не был найден"));
        entity.setAverageRating(dto.getAverageRating());
        entity.setVotes(dto.getVotes());
        repository.save(entity);
        return entity;
    }

    @Override
    @Transactional
    public void create(Restaurant restaurant) {
        repository.save(restaurant);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id: " + id + " не был найден"));
        repository.delete(restaurant);
    }

    @Override
    public List<Restaurant> findAllSortedByRating() {
        return repository.findAllSortedByRating();
    }
}
