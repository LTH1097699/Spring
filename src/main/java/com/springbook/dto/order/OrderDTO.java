package com.springbook.dto.order;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;
import com.springbook.dto.book.WareHouseDTO;
import com.springbook.dto.user.ShipAddressDTO;
import com.springbook.dto.user.UserDTO;

public class OrderDTO extends AbstractDTO<OrderDTO> {
	@NotEmpty(message = "Chưa nhập giá trị")
	private String email;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String username;
	
	private String number;
	private int totalQuantity;
	private double totalPrice;
	private String note;
	private int totalProduct;
	private UserDTO user;
	private Date shipDate;
	
	private int status;
	private String address;
	private String province;
	private String district;
	private String ward;
	private int action;
	
	private int shipMethod;
	private int paymentMethod;
	
	
	private List<OrderDetailDTO> orderDetails;
	private ShipAddressDTO shipAddress;
	private List<String> addressList;
	
	private List<WareHouseDTO> wareHouses;
	
	private List<Long> chosedBookIncart;
	
	private String start;
	private String end;
	private String searchStatus;
	
	
	private Long addressId;
	private Long deleteOrderDetail;
	
	private String keyword;
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getDeleteOrderDetail() {
		return deleteOrderDetail;
	}
	public void setDeleteOrderDetail(Long deleteOrderDetail) {
		this.deleteOrderDetail = deleteOrderDetail;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public List<Long> getChosedBookIncart() {
		return chosedBookIncart;
	}
	public void setChosedBookIncart(List<Long> chosedBookIncart) {
		this.chosedBookIncart = chosedBookIncart;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getTotalProduct() {
		return totalProduct;
	}
	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}
	
	public List<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getShipDate() {
		return shipDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
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
	public List<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}
	public ShipAddressDTO getShipAddress() {
		return shipAddress;
	}
	public void setShipAddress(ShipAddressDTO shipAddress) {
		this.shipAddress = shipAddress;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
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
	public List<WareHouseDTO> getWareHouses() {
		return wareHouses;
	}
	public void setWareHouses(List<WareHouseDTO> wareHouses) {
		this.wareHouses = wareHouses;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	

	
}
