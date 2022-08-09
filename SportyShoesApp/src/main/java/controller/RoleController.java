package com.simplilearn.controller;

import com.simplilearn.entity.Role;
import com.simplilearn.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userRoles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Accessible for Admin | End Point URL -> http://localhost:9090/api/userRoles/createNewRole
    @PostMapping({"/createNewRole"})
    @PreAuthorize("hasRole('Admin')")
    public Role createNewRole(@RequestBody Role role) {
        return roleService.createNewRole(role);
    }
}
