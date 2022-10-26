package com.springbook.dto.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;
import com.springbook.dto.order.OrderDTO;

public class UserDTO extends AbstractDTO<UserDTO> {
	
	private String userName;
	
	private String password;
	
	private String confirmPassword;
	
	private String fullName;
	private String fullNameValidator;
	private String roleCode;
	private RoleDTO role;
	
	private List<ShipAddressDTO> shipAddress;
	private List<OrderDTO> orders ;
	private OrderDTO order ;
	private ShipAddressDTO address;
	
	private String getTab;
	private int action;
	
	

	
	public String getFullNameValidator() {
		return fullNameValidator;
	}
	public void setFullNameValidator(String fullNameValidator) {
		this.fullNameValidator = fullNameValidator;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public OrderDTO getOrder() {
		return order;
	}
	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public RoleDTO getRole() {
		return role;
	}
	public void setRole(RoleDTO role) {
		this.role = role;
	}
	public List<ShipAddressDTO> getShipAddress() {
		return shipAddress;
	}
	public void setShipAddress(List<ShipAddressDTO> shipAddress) {
		this.shipAddress = shipAddress;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public ShipAddressDTO getAddress() {
		return address;
	}
	public void setAddress(ShipAddressDTO address) {
		this.address = address;
	}
	public List<OrderDTO> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}
	public String getGetTab() {
		return getTab;
	}
	public void setGetTab(String getTab) {
		this.getTab = getTab;
	}
	


	
	
}
