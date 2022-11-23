package com.springbook.entity.book;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;

@Entity
@Table(name = "warehouse")
public class WareHouseEntity extends AbstractEntity {
	@Column(name = "code",nullable = false)
	private String code;
	
	@Column(name="name",nullable = false)
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@OneToMany(mappedBy = "wareHouse",fetch = FetchType.LAZY)
	private List<BookWarehouseEntity> bookWarehouse = new ArrayList<>();
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<BookWarehouseEntity> getBookWarehouse() {
		return bookWarehouse;
	}

	public void setBookWarehouse(List<BookWarehouseEntity> bookWarehouse) {
		this.bookWarehouse = bookWarehouse;
	}


	
}
