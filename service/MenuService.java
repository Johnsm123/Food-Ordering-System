package com.examly.service;

import com.examly.entity.MenuItem;
import com.examly.exception.RestaurantNotFoundException;
import java.util.List;

public interface MenuService {
    boolean createMenuItem(MenuItem menuItem) throws RestaurantNotFoundException;
    List<MenuItem> getMenuItemsByRestaurant(int restaurantId);
}
