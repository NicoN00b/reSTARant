package dao;

import models.User;

import java.util.List;



public interface UserDao {

   void add (User user);


   List<User> getAll();

   User findById(int id);


   void update(int id, String name);

   void deleteById(int id);

   void clearAllUsers();

}
