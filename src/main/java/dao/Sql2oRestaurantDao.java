package dao;

import models.Restaurant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 8/16/17.
 */
public class Sql2oRestaurantDao implements RestaurantDao { //implementing our interface

    private final Sql2o sql2o;

    public Sql2oRestaurantDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    public void add(Restaurant restaurant) {

        String sql = "INSERT INTO restaurants (name, location) VALUES (:name, :location)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql) //make a new variable
                    .addParameter("name", restaurant.getName())
                    .addParameter("location", restaurant.getLocation())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("LOCATION", "location")
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            restaurant.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    public List<Restaurant> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM restaurants") //raw sql
                    .executeAndFetch(Restaurant.class); //fetch a list
        }
    }

    public Restaurant findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM restaurants WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Restaurant.class);
        }
    }
    @Override
    public void update(int id, String newName, String newLocation){
        String sql = "UPDATE restaurants SET (name, location) = (:name, :location) WHERE id=:id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("location", newLocation)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}
