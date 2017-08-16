package dao;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import java.util.List;


import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class Sql2OUserDaoTest {

    private Sql2oUserDao userDao;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        userDao = new Sql2oUserDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        con = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void addingUserSetsId() throws Exception {
        User user = setupNewUser();
        int originalUserId = user.getId();
        userDao.add(user);
        assertNotEquals(originalUserId, user.getId()); //how does this work?
    }

    @Test
    public void existingUsersCanBeFoundById() throws Exception {
        User user = setupNewUser();
        userDao.add(user); //add to dao (takes care of saving)
        User foundUser = userDao.findById(user.getId()); //retrieve
        assertEquals(user, foundUser); //should be the same
    }

    @Test
    public void addedUsersAreReturnedFromGetAll() throws Exception {
        User user = setupNewUser();
        User otherUser = setupNewUser();
        userDao.add(user);
        userDao.add(otherUser);
        assertEquals(2, userDao.getAll().size());
    }

    @Test
    public void noUsersReturnsEmptyList() throws Exception {
        assertEquals(0, userDao.getAll().size());
    }


    public User setupNewUser(){

        return new User("tommyPickleHands");
    }
}