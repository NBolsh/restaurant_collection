package ru.astondevs.restaurant_collection.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.restaurant_collection.dto.RestaurantDto;
import ru.astondevs.restaurant_collection.entity.Restaurant;
import ru.astondevs.restaurant_collection.repository.RestaurantRepository;
import ru.astondevs.restaurant_collection.service.impl.RestaurantServiceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Коллекция ресторанов - Сервис")
class RestaurantServiceTest {
    private final Integer id = 1;
    private final Restaurant restaurant1 = new Restaurant(1, "City", "Name", 150, "1.1", 20);
    private final Restaurant restaurant2 = new Restaurant(2, "City1", "Name1", 120, "1.4", 30);
    @Mock
    private RestaurantRepository repository;
    @InjectMocks
    private RestaurantServiceImpl service;

    @Test
    @DisplayName("Поиск ресторана по ID")
    void findByIdTest() {
        Restaurant expected = new Restaurant();
        expected.setId(id);

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(expected));

        Restaurant actual = service.findById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Поиск всех ресторанов")
    void findAllTest() {
        List<Restaurant> list = List.of(restaurant1, restaurant2);

        Mockito.when(repository.findAll())
                .thenReturn(list);

        assertEquals(list, service.findAll());
    }

    @Test
    @DisplayName("Поиск всех ресторанов в городе")
    void findAllByCityTest() {
        List<Restaurant> list = List.of(restaurant1);

        Mockito.when(repository.findAllByCity("City"))
                .thenReturn(list);

        assertEquals(list, service.findAllByCity("City"));
    }

    @Test
    @DisplayName("Обновление рейтинга и кол-ва оценок, проверка на сохранение")
    void updateRatingAndVotesTest() {
        RestaurantDto dto = new RestaurantDto();
        dto.setAverageRating("4.5");
        dto.setVotes(100);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setAverageRating("3.2");
        restaurant.setVotes(90);

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(restaurant));
        Mockito.when(repository.save(restaurant))
                .thenReturn(restaurant);

        Restaurant updatedRestaurant = service.updateRatingAndVotes(id, dto);

        assertEquals(dto.getAverageRating(), updatedRestaurant.getAverageRating());
        assertEquals(dto.getVotes(), updatedRestaurant.getVotes());
        Mockito.verify(repository, Mockito.times(1)).save(restaurant);
    }

    @Test
    @DisplayName("Создание ресторана")
    void createTest() {
        Mockito.when(repository.save(restaurant1)).thenReturn(restaurant1);

        service.create(restaurant1);

        Mockito.verify(repository).save(restaurant1);
    }

    @Test
    @DisplayName("Удаление ресторана по ID")
    void deleteByIdTest() {
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(restaurant1));

        service.deleteById(id);

        Mockito.verify(repository).delete(restaurant1);
    }

    @Test
    @DisplayName("Поиск всех ресторанов отсортированных по рейтингу")
    void findAllSortedByRatingTest() {
        List<Restaurant> sortedList = Stream.of(restaurant1, restaurant2)
                .sorted(Comparator.comparing(Restaurant::getAverageRating).reversed()).toList();
        Mockito.when(repository.findAllSortedByRating())
                .thenReturn(sortedList);

        List<Restaurant> actualList = service.findAllSortedByRating();

        assertEquals(actualList, sortedList);
    }
}
