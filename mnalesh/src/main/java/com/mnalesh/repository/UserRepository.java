package com.mnalesh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mnalesh.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

	 @Query("SELECT u FROM User u where u.ssoId = ?1") 
	    Optional<User> findBySSO(String sso);
	 @Query("delete from User u where u.ssoId = ?1") 
	 void deleteBySSO(String sso);
	
}