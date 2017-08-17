package dao;


import models.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Guest on 8/17/17.
 */
public class Sql2oReviewDaoTest {

    private Sql2oReviewDao reviewDao;
    private Connection con;


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        con = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void addingReviewSetsId() throws Exception {
        Review review = setupNewReview();
        int originalReviewId = review.getId();
        reviewDao.add(review);
        assertNotEquals(originalReviewId, review.getId()); //how does this work?
    }

    @Test
    public void existingReviewCanBeFoundById() throws Exception {
        Review review = setupNewReview();
        reviewDao.add( review); //add to dao (takes care of saving)
        Review foundReview=  reviewDao.findById( review.getId()); //retrieve
        assertEquals(review, foundReview); //should be the same
    }

    @Test
    public void updateChangesReviewRating() throws Exception {
        int rating = 5;
        Review review = new Review (5, "meh");
        reviewDao.add(review);

        reviewDao.update(1, 8, "Meh, but the Chowder was good");
        Review updatedReview = reviewDao.findById(review.getId()); //why do I need to refind this?
        assertNotEquals(rating, updatedReview.getRating());
    }

    @Test
    public void deleteByIdDeletesCorrectReview() throws Exception {
        Review review = setupNewReview();
        reviewDao.add(review);
        reviewDao.deleteById(review.getId());
        assertEquals(0, reviewDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Review review = setupNewReview();
        Review otherReview = new Review(5, "totally banal");
        reviewDao.add(review);
        reviewDao.add(otherReview);
        int daoSize = reviewDao.getAll().size();
        reviewDao.clearAllReviews();
        assertTrue(daoSize > 0 && reviewDao.getAll().size() == 0); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }





    public Review setupNewReview() {

        return new Review(10, "amazing");
    }

}