package com.mnalesh.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Amenities {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	/*@OneToOne(mappedBy = "amenities", cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY, optional = false)
	private House house;*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
