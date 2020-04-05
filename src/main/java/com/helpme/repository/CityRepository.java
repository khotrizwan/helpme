package com.helpme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.City;

@Repository
public interface CityRepository extends CrudRepository<City, String>{

}
