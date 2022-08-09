package com.simplilearn.service;

import com.simplilearn.entity.Role;
import com.simplilearn.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // This method will create New Role and persist it to DB
    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
