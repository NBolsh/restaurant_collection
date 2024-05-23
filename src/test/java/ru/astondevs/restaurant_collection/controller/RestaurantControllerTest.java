package ru.astondevs.restaurant_collection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.astondevs.restaurant_collection.dto.RestaurantDto;
import ru.astondevs.restaurant_collection.entity.Restaurant;
import ru.astondevs.restaurant_collection.service.RestaurantService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(RestaurantController.class)
@DisplayName("Коллекция ресторанов - контроллер")
public class RestaurantControllerTest {

    @MockBean
    private RestaurantService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final String URI = "/api/v1/restaurant";
    private final Integer id = 1;
    private final Restaurant restaurant1 = new Restaurant(1, "City", "Name", 150, "1.1", 20);
    private final Restaurant restaurant2 = new Restaurant(2, "City1", "Name1", 120, "1.4", 30);

    @Test
    @DisplayName("Поиск всех ресторанов")
    void findAllTest() throws Exception {
        List<Restaurant> list = List.of(restaurant1, restaurant2);

        Mockito.when(service.findAll()).thenReturn(list);

        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    @DisplayName("Поиск всех ресторанов по городу")
    void findAllByCityTest() throws Exception {
        String city = "City";

        Mockito.when(service.findAllByCity(city)).thenReturn((List.of(restaurant1)));

        mockMvc.perform(get(URI + "/query").param("city", city))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(restaurant1))));
    }

    @Test
    @DisplayName("Поиск ресторана по ID")
    void findByIdTest() throws Exception {
        Mockito.when(service.findById(id)).thenReturn(restaurant1);

        mockMvc.perform(get(URI + "/query").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(restaurant1)));
    }

    @Test
    @DisplayName("Получение отсортированного списка ресторанов")
    void findAllSortedByRatingTest() throws Exception {
        List<Restaurant> list = Stream.of(restaurant1, restaurant2)
                .sorted(Comparator.comparing(Restaurant::getAverageRating).reversed()).toList();

        Mockito.when(service.findAllSortedByRating()).thenReturn(list);

        mockMvc.perform(get(URI + "/sort"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    @DisplayName("Обновление параметров ресторана по ID")
    void updateRatingAndVotesTest() throws Exception {
        RestaurantDto dto = new RestaurantDto();
        dto.setAverageRating("5.0");
        dto.setVotes(5000);

        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setId(id);
        updatedRestaurant.setAverageRating(dto.getAverageRating());
        updatedRestaurant.setVotes(dto.getVotes());

        Mockito.when(service.updateRatingAndVotes(id, dto)).thenReturn(updatedRestaurant);

        mockMvc.perform(put(URI + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedRestaurant)));
    }

    @Test
    @DisplayName("Добавление нового ресторана")
    void createTest() throws Exception {
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(restaurant1)));
        Mockito.verify(service).create(restaurant1);
    }

    @Test
    @DisplayName("Удаление ресторана по ID")
    void deleteByIdTest() throws Exception {
        mockMvc.perform(delete(URI + "/" + id))
                .andExpect(status().isOk());
        Mockito.verify(service).deleteById(id);
    }
}
