package com.simplilearn.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simplilearn.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
