package dao;

import models.User;

import java.util.List;



public interface UserDao {

    void add (User user);
    //read

    List<User> getAll();

   User findById(int id);

//    //update
  void update(int id, String name);
//    //delete
   void deleteById(int id);
//    void clearAllTasks();
//
//
}
