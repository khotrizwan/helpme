package com.helpme.repository;

import org.springframework.data.repository.CrudRepository;

import com.helpme.model.LoginBean;

public interface LoginRepository extends CrudRepository<LoginBean, String>{

}
