package dao;

import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Guest on 8/16/17.
 */
public class Sql2oRestaurantDaoTest {

    private Sql2oRestaurantDao restaurantDao;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        con = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void addingRestaurantSetsId() throws Exception {
        Restaurant restaurant = setupNewRestaurant();
        int originalRestaurantId = restaurant.getId();
        restaurantDao.add(restaurant);
        assertNotEquals(originalRestaurantId, restaurant.getId()); //how does this work?
    }

    @Test
    public void existingRestaurantCanBeFoundById() throws Exception {
        Restaurant restaurant = setupNewRestaurant();
        restaurantDao.add( restaurant); //add to dao (takes care of saving)
        Restaurant foundRestaurant=  restaurantDao.findById( restaurant.getId()); //retrieve
        assertEquals(restaurant, foundRestaurant); //should be the same
    }
    @Test
    public void updateChangesRestaurantContent() throws Exception {
        String location = "burnside";
        Restaurant restaurant = new Restaurant ("pickle rick's pickles", location);
        restaurantDao.add(restaurant);

        restaurantDao.update(restaurant.getId(),"Dilbert's Tavern", "6th.");
        Restaurant updatedRestaurant = restaurantDao.findById(restaurant.getId()); //why do I need to refind this?
        assertNotEquals(location, updatedRestaurant.getLocation());
    }
    @Test
    public void deleteByIdDeletesCorrectRestaurant() throws Exception {
        Restaurant restaurant = setupNewRestaurant();
        restaurantDao.add(restaurant);
        restaurantDao.deleteById(restaurant.getId());
        assertEquals(0, restaurantDao.getAll().size());
    }

    public Restaurant setupNewRestaurant() {

        return new Restaurant("brrito", "nome, alaska");
    }


}