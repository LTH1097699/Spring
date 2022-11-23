package com.springbook.entity.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;
import com.springbook.entity.book.CommentEntity;
import com.springbook.entity.order.OrderEntity;

@Entity
@Table(name="user")
public class UserEntity extends AbstractEntity {

	@Column(name = "email",nullable = false)
	private String email;

	@Column(name = "password",nullable = false)
	private String password;

	@Column(name = "fullname",nullable = false)
	private String fullName;
	

	@Column
	private Integer status;
	
	@OneToMany(mappedBy = "userId",fetch = FetchType.LAZY)
	private List<ContactEntity> contacts = new ArrayList<>();

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private List<ShipAddressEntity> shipAddress = new ArrayList<>();
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY, orphanRemoval = true)
	private List<CommentEntity> comments = new ArrayList<>();

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY, orphanRemoval = true)
	private List<OrderEntity>  order = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "roleid")
	private RoleEntity role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ShipAddressEntity> getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(List<ShipAddressEntity> shipAddress) {
		this.shipAddress = shipAddress;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public List<ContactEntity> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactEntity> contacts) {
		this.contacts = contacts;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public List<OrderEntity> getOrder() {
		return order;
	}

	public void setOrder(List<OrderEntity> order) {
		this.order = order;
	}
	
	
}
