//package com.boukyApps.myguep.controller;
//
//import com.boukyApps.myguep.model.User;
//import com.boukyApps.myguep.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @GetMapping("/users")
//    public String getUsers(){
////        User savedUser = userRepository.save(user);
//        return "savedUser";
//    }
//
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user){
//        System.out.println("sev de user "+user.toString());
//        System.out.println("sev de user "+user.getUsername());
//        User savedUser = userRepository.save(user);
//
//        return savedUser;
//    }
//}
