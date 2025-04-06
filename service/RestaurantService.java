package com.examly.service;

import com.examly.entity.Restaurant;
import java.util.List;

public interface RestaurantService {
    boolean createRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
}
