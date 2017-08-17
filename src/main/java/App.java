import dao.Sql2oRestaurantDao;
import dao.Sql2oReviewDao;
import dao.Sql2oUserDao;
import models.Restaurant;
import models.Review;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

/**
 * Created by Guest on 8/16/17.
 */
public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oRestaurantDao restaurantDao = new Sql2oRestaurantDao(sql2o);
        Sql2oReviewDao reviewDao = new Sql2oReviewDao(sql2o);
        Sql2oUserDao userDao = new Sql2oUserDao(sql2o);

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Restaurant> restaurants = restaurantDao.getAll();
            model.put("restaurant", restaurants);

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/restaurant/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editRestaurant", true);
            List<Restaurant> allRestaurantss = restaurantDao.getAll();
            model.put("restaurant", allRestaurantss);
            return new ModelAndView(model, "restaurant-form.hbs");
        }, new HandlebarsTemplateEngine());


        //post: process a form to update a restaurant and reviews it contains
        post("/restaurants/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfRestaurantToEdit = Integer.parseInt(req.queryParams("editRestaurantId"));
            String newName = req.queryParams("newRestaurantName");
            String newLocation = req.queryParams("newRestaurantName");
            restaurantDao.update(restaurantDao.findById(idOfRestaurantToEdit).getId(), newName, newLocation);

            List<Restaurant> restaurant = restaurantDao.getAll(); //refresh list of links for navbar.
            model.put("restaurant", restaurant);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/restaurants/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Restaurant> restaurants = restaurantDao.getAll();
            model.put("restaurant", restaurants);
            return new ModelAndView(model, "restaurant-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/restaurants/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String location = req.queryParams("location");
            Restaurant newRestaurant = new Restaurant(name, location);
            restaurantDao.add(newRestaurant);
            List<Restaurant> restaurants = restaurantDao.getAll();
            model.put("restaurant", restaurants);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/restaurants/:restaurant_id",(request, response) -> {
            Map<String,Object>model = new HashMap<>();
            int idOfRestaurantToFind = Integer.parseInt(request.params("restaurant_id"));
            List<Restaurant> restaurants =restaurantDao.getAll();
            model.put("restaurant", restaurants);
            Restaurant foundRestaurant = restaurantDao.findById(idOfRestaurantToFind);
            model.put("restaurant", foundRestaurant);
            List<Review> allReviewsByRestaurant= restaurantDao.getAllReviewsByRestaurant(idOfRestaurantToFind);
            model.put("review", allReviewsByRestaurant);
            return new ModelAndView(model, "restaurant-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new review form
        get("/:restaurant_id/reviews/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Restaurant> allRestaurants = restaurantDao.getAll();
            model.put("restaurant", allRestaurants);
            return new ModelAndView(model, "review.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new review form
        post("/:restaurant_id/reviews/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Restaurant> allRestaurants = restaurantDao.getAll();
            model.put("restaurant", allRestaurants);
            int rating = Integer.parseInt(request.queryParams("rating"));
            String content = request.queryParams("content");
            int restaurantId = Integer.parseInt(request.queryParams("restaurant"));
            Review newReview = new Review(rating, content, restaurantId);
            reviewDao.add(newReview);
            model.put("review", newReview);
            return new ModelAndView(model, "restaurant-detail.hbs");
        }, new HandlebarsTemplateEngine());

    }
}
