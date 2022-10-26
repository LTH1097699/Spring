 package com.springbook.entity.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;
import com.springbook.entity.user.ShipAddressEntity;
import com.springbook.entity.user.UserEntity;

@Entity
@Table(name = "orderr")
public class OrderEntity extends AbstractEntity {	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserEntity user;
	
	@Column(name = "email",nullable = false)
	private String email;
	
	@Column(name = "fullname",nullable = false)
	private String fullname;
	
	@Column(name = "address",nullable = false)
	private String address;
	@Column(name = "province",nullable = false)
	private String province;
	@Column(name = "district",nullable = false)
	private String district;
	@Column(name = "ward",nullable = false)
	private String ward;
	
	@Column(name = "number",nullable = false)
	private String number;
	
	@Column(name = "totalproduct")
	private int totalProduct;
	
	@Column(name = "totalquantity")
	private int totalQuantity;

	@Column(name = "totalprice")
	private double totalPrice;
	
	@Column(name = "shipmethod",nullable = false)
	private int shipMethod;
	
	@Column(name = "paymentmethod")
	private int paymentMethod;
	
	@Column(name = "status",nullable = false)
	private int status;

	@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE,orphanRemoval = true)
	private List<OrderDetailEntity> orderDetails = new ArrayList<>();

	public List<OrderDetailEntity> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailEntity> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public int getShipMethod() {
		return shipMethod;
	}

	public void setShipMethod(int shipMethod) {
		this.shipMethod = shipMethod;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	

}
