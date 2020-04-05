package com.helpme.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.UserBean;

@Repository
public interface UserRepository extends CrudRepository<UserBean, Integer> {
	Optional<UserBean> findByMobileno(String mobileno);
}
