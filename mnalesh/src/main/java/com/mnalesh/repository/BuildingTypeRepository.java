package com.mnalesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnalesh.entity.BuildingType;


public interface BuildingTypeRepository extends JpaRepository<BuildingType, Long> {

}