package com.mnalesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnalesh.entity.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {

}

