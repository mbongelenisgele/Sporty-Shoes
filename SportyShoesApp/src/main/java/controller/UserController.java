package com.simplilearn.controller;

import com.simplilearn.entity.User;
import com.simplilearn.exception.UserException;
import com.simplilearn.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    // Accessible for All | End Point URL -> http://localhost:9090/api/users/registerNewUser
    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    // Accessible for All | End Point URL -> http://localhost:9090/api/users/getUserDetails/admin123
    @GetMapping({"/getUserDetails/{userName}"})
    @PreAuthorize("hasAnyRole('Admin','User')")
    public User getUserDetails(@PathVariable("userName") String userName) throws UserException{
        return userService.getUserDetails(userName);
    }
    
    // Accessible for Admin | End Point URL -> http://localhost:9090/api/users/updatePassowrd/admin123/232063
    @GetMapping({"/updatePassowrd/{userName}/{password}"})
    @PreAuthorize("hasRole('Admin')")
    public User updatePassowrd(@PathVariable("userName") String userName, @PathVariable("password") String password) throws UserException{
        return userService.updatePassword(userName, password);
    }
    
    
    
    
    // Authorization Testing Functionality
    
    // Accessible for Admin | End Point URL -> http://localhost:9090/api/users/forAdmin
    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    // Accessible for Admin | End Point URL -> http://localhost:9090/api/users/forUser
    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}
