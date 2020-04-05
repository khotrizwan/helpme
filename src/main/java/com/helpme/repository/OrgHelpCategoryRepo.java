package com.helpme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.OrgHelpCategory;

@Repository
public interface OrgHelpCategoryRepo extends CrudRepository<OrgHelpCategory, Integer>{
	
	List<OrgHelpCategory> findByOrgId(Integer organizationId);
	
	List<OrgHelpCategory> findByHelpCategory(Integer helpCategoryId);

}
