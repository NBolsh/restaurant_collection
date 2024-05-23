package ru.astondevs.restaurant_collection.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.astondevs.restaurant_collection.dto.RestaurantDto;
import ru.astondevs.restaurant_collection.entity.Restaurant;
import ru.astondevs.restaurant_collection.service.RestaurantService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Контроллер для коллекции ресторанов",
        description = "Предоставляет возможности управления коллекцией ресторанов")
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private final RestaurantService service;

    @Operation(summary = "Получение списка ресторанов")
    @GetMapping
    public ResponseEntity<List<Restaurant>> findAll() {
        List<Restaurant> restaurantList = service.findAll();
        return ResponseEntity.ok().body(restaurantList);
    }

    @Operation(summary = "Получение списка ресторанов по заданному городу")
    @GetMapping(value = "/query", params = {"city"})
    public ResponseEntity<List<Restaurant>> findAllByCity(@RequestParam String city) {
        List<Restaurant> restaurantList = service.findAllByCity(city);
        return ResponseEntity.ok().body(restaurantList);
    }

    @Operation(summary = "Получение ресторана по id")
    @GetMapping(value = "/query", params = {"id"})
    public ResponseEntity<Restaurant> findById(@RequestParam Integer id) {
        Restaurant restaurant = service.findById(id);
        return ResponseEntity.ok().body(restaurant);
    }

    @Operation(summary = "Получение отсортированного списка ресторанов",
            description = "Возвращает список ресторанов отсортированный по их среднему рейтингу")
    @GetMapping("/sort")
    public ResponseEntity<List<Restaurant>> findAllSortedByRating() {
        List<Restaurant> sortedRestaurantList = service.findAllSortedByRating();
        return ResponseEntity.ok().body(sortedRestaurantList);
    }

    @Operation(summary = "Добавление нового ресторана")
    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        service.create(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    @Operation(summary = "Обновление параметров ресторана по id",
            description = "Обновление полей average_rating и votes у ресторана по id")
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRatingAndVotes(@PathVariable Integer id,
                                                           @RequestBody RestaurantDto dto) {
        Restaurant updatedRest = service.updateRatingAndVotes(id, dto);
        return ResponseEntity.ok().body(updatedRest);
    }

    @Operation(summary = "Удаление ресторана по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
