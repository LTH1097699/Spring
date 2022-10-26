package com.springbook.dto.user;

import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;

public class ShipAddressDTO extends AbstractDTO<ShipAddressDTO> {
	@NotEmpty(message = "Chưa chọn giá trị")
	private String province;
	@NotEmpty(message = "Chưa chọn giá trị")
	private String district;
	@NotEmpty(message = "Chưa chọn giá trị")
	private String ward;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String address;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String number;
	
	private String str;

	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
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
	
}
