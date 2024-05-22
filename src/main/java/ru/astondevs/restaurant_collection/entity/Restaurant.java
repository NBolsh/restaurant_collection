package ru.astondevs.restaurant_collection.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "estimated_cost", nullable = false)
    @Min(value = 0)
    private Integer estimatedCost;

    @Column(name = "average_rating", nullable = false, length = 3)
    private String averageRating;

    @Column(name = "votes", nullable = false)
    @Min(value = 0)
    private Integer votes;
}
