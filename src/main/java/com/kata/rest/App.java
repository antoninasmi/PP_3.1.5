package com.kata.rest;

import com.kata.rest.config.MyConfig;
import com.kata.rest.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
        //getting all users
        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);
        //adding new user
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 22);
        communication.saveUser(user);
        //updating user
        user.setName("Thomas");
        user.setLastName("Shelby");
        communication.updateUser(user);
        //deleting user
        communication.deleteUser(user);
    }
}
