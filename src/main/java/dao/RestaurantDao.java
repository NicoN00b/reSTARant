package dao;

import models.Restaurant;

import java.util.List;

/**
 * Created by Guest on 8/16/17.
 */
public interface RestaurantDao {

    //create
    void add (Restaurant restaurant);
    //read
    List<Restaurant> getAll();

    Restaurant findById(int id);
//    //update
    void update(int id, String name, String location);
//    //delete
    void deleteById(int id);
//    void clearAllRestaurants();

}
