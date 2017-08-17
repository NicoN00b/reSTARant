package dao;

import models.Review;

import java.util.List;


public interface ReviewDao {

    //create
    void add (Review review);
    //read
    List<Review> getAll();


//
    Review findById(int id);
//    //    //update
    void update(int id, int rating, String content, int restaurantId);
//    //    //delete
    void deleteById(int id);
//
    void clearAllReviews();

}

