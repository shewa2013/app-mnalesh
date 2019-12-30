package com.mnalesh.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class House implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
	private Set<Image> image;
	private double price;
	private String description;
	@Temporal(TemporalType.DATE)
	private Date posteddate;
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "address_id")
	private Address address;
	 @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	    @JoinTable(name = "HOUSE_AMENITIES",joinColumns = { 
	    	@JoinColumn(name = "HOUSE_ID") },inverseJoinColumns = { 
	    @JoinColumn(name = "AMENITIES_ID") })
	    private Set<Amenities> amenities = new HashSet<Amenities>();

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "room_type_id")
	private RoomType roomtype;
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "building_type_id")
	private BuildingType buildingtype;
	private String preferedroomate;
	@Temporal(TemporalType.DATE)
	private Date availablefrom;
	@ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long houseId) {
		this.id = houseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public RoomType getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(RoomType roomtype) {
		this.roomtype = roomtype;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Amenities> getAmenities() {
		return amenities;
	}

	public void setAmenities(Set<Amenities> amenities) {
		this.amenities = amenities;
	}

	public BuildingType getBuildingtype() {
		return buildingtype;
	}

	public void setBuildingtype(BuildingType buildingtype) {
		this.buildingtype = buildingtype;
	}

	public String getPreferedroomate() {
		return preferedroomate;
	}

	public void setPreferedroomate(String preferedroomate) {
		this.preferedroomate = preferedroomate;
	}

	public Date getAvailablefrom() {
		return availablefrom;
	}

	public void setAvailablefrom(Date availablefrom) {
		this.availablefrom = availablefrom;
	}

	public Date getPosteddate() {
		return posteddate;
	}

	public void setPosteddate(Date posteddate) {
		this.posteddate = posteddate;
	}

	public Set<Image> getImage() {
		return image;
	}

	public void setImage(Set<Image> image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
