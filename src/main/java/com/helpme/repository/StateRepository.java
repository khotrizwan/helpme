package com.helpme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.State;

@Repository
public interface StateRepository extends CrudRepository<State, Integer>{
	
	   List<State> findByCountryId(Integer countryId);

}
