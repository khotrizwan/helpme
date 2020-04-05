package com.helpme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.State;

@Repository
public interface StateRepository extends CrudRepository<State, String>{

}
