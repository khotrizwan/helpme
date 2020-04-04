package com.helpme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.OrgBean;

@Repository
public interface OrgRepository extends CrudRepository<OrgBean, String>{

}
