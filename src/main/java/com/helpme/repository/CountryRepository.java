package com.helpme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, String>{

}
