package com.mnalesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnalesh.entity.UserProfile;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	
	 UserProfile findByType(String type);

}
