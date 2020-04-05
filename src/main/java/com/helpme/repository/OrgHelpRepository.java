package com.helpme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.City;

@Repository
public interface OrgHelpRepository extends CrudRepository<City, Integer>{
	
	List<City> findByStateId(Integer stateId);

}
