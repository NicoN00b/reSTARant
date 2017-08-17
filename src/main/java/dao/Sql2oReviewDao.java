package dao;

import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 8/17/17.
 */
public class Sql2oReviewDao implements ReviewDao { //implementing our interface

    private final Sql2o sql2o;

    public Sql2oReviewDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    public void add(Review review) {

        String sql = "INSERT INTO reviews (rating, content, restaurantId) VALUES (:rating, :content, :restaurantid)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql) //make a new variable
                    .addParameter("rating", review.getRating())
                    .addParameter("content", review.getContent())
                    .addParameter("restaurantId", review.getRestaurantId())
                    .addColumnMapping("RATING", "rating")
                    .addColumnMapping("CONTENT", "content")
                    .addColumnMapping("RESTAURANTID", "restaurantId")
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            review.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    public List<Review> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM reviews") //raw sql
                    .executeAndFetch(Review.class); //fetch a list
        }
    }

    public Review findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM reviews WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Review.class);
        }
    }

    public void update(int id, int newRating, String newContent, int newRestaurantId){
        String sql = "UPDATE reviews SET (rating, content, restaurantId) = (:rating, :content, :restaurantId) WHERE id=:id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("rating", newRating)
                    .addParameter("content", newContent)
                    .addParameter("restaurantId", newRestaurantId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE from reviews WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    public void clearAllReviews() {
        String sql = "DELETE from reviews";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
