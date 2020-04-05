package com.helpme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.OrgBean;

@Repository
public interface OrgRepository extends CrudRepository<OrgBean, Integer>
{
	
	List<OrgBean> findByOrgType(String orgType);

}
