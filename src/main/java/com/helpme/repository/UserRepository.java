package com.helpme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.UserBean;

@Repository
public interface UserRepository extends CrudRepository<UserBean, String> {
}
