package com.springbook.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;

@Entity
@Table(name = "shipaddress")
public class ShipAddressEntity extends AbstractEntity {
	
	@Column(name = "province",nullable = false)
	private String province;
	
	@Column(name = "district",nullable = false)
	private String district;
	
	@Column(name = "ward",nullable = false)
	private String ward;
	
	@Column(name = "address",nullable = false)
	private String address;
	
	@Column(name = "number",nullable = false)
	private String number;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid",nullable = false)
	private UserEntity user;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
