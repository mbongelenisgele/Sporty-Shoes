package com.simplilearn.service;

import com.simplilearn.entity.Role;
import com.simplilearn.entity.User;
import com.simplilearn.exception.UserException;
import com.simplilearn.repository.RoleRepository;
import com.simplilearn.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This method will create Admin user
    public void initRoleAndUser() {
    	// Create Role object for Admin Role and persisting it to DB.
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

    	// Create Role object for User Role and persisting it to DB.
        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);

    	// Create User object for Admin and assigning it with Admin role and persisting it to DB.
        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);
    }

    // This method will create new user and assign it with User Role.
    public User registerNewUser(User user) {
        Role role = roleRepository.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userRepository.save(user);
    }
    
    // This method will fetch User details based on user name.
    public User getUserDetails(String userName) throws UserException {
    	return userRepository.findById(userName).orElseThrow(() -> new UserException("User not found."));
    }
    
    // This method will fetch update password for user
    public User updatePassword(String userName, String password) throws UserException {
    	User user = userRepository.findById(userName).orElseThrow(() -> new UserException("User not found."));
    	user.setUserPassword(getEncodedPassword(password));
    	return userRepository.save(user);
    }
    
    // This method will encode the raw string password provided.
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
