package com.selvesperer.knoeien.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.data.repository.custom.UserRepositoryCustom;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
	@Query("from User u where u.companyId = :companyID")
	List<User> findUsersByCompanyId(@Param("companyID") String companyID);

	@Query("from User u where u.email = :email")
	User findUserByEmail(@Param("email") String email);
	
	@Query("from User u where u.passwordResetToken = :key")
	User findUserByResetToken(@Param("key") String key);
	
	@Query("from User u where u.id = :id")
	User findUserById(@Param("id") String id);
	
	@Query(value="SELECT * FROM user u join job j on j.created_by_id=u.id where j.id=:jobId",nativeQuery=true)
	public User findUserByJobId(@Param("jobId") String jobId);
	
}