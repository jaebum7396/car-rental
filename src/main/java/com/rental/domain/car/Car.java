package com.rental.domain.car;

import com.rental.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer productionYear;

    private boolean available = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "car_category",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @Builder
    public Car(String manufacturer, String model, Integer productionYear, Set<Category> categories) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.productionYear = productionYear;
        if (categories != null) {
            this.categories = categories;
        }
    }

    public void update(String manufacturer, String model, Integer productionYear) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.productionYear = productionYear;
    }

    public void changeAvailability(boolean available) {
        this.available = available;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }
}