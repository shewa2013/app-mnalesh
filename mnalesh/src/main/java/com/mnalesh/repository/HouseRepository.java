package com.mnalesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnalesh.entity.House;


public interface HouseRepository extends JpaRepository<House, Long> {
}
