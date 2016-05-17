package com.technoirarts;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {

    List<User> findAll();

    User findById(Long id);
}
