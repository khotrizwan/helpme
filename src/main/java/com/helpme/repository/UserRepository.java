package com.helpme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helpme.model.UserBean;

@Repository
public interface UserRepository extends CrudRepository<UserBean, Integer> {
	Optional<UserBean> findByMobileno(String mobileno);

	@Query(value = "SELECT u.* FROM user u, org_help_category_map c WHERE c.organization_id = u.organization_id and u.city_id = :cityId and c.help_category_id = :categoryId and u.user_type = :userType  ", nativeQuery = true)
	//Optional<List<UserBean>> getServiceProviders();
	Optional<List<UserBean>> getServiceProviders(int cityId, int categoryId, String userType);

	Optional<List<UserBean>> findByOrganizationId(int organizationId);
	
}
