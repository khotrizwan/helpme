package com.helpme.repository;

import org.springframework.data.repository.CrudRepository;

import com.helpme.model.Country;
import com.helpme.model.HelpHistoryBean;

public interface HelpHistoryRepository extends CrudRepository<HelpHistoryBean, Integer> {

}
