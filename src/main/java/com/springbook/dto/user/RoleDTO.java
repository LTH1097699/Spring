package com.springbook.dto.user;

import java.util.List;
import java.util.Map;

import com.springbook.dto.AbstractDTO;

public class RoleDTO extends AbstractDTO<RoleDTO> {
	private String code;
	private String codeName;
	
	
	
	private List<PermissionDTO> listPerrr;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	public List<PermissionDTO> getListPerrr() {
		return listPerrr;
	}
	public void setListPerrr(List<PermissionDTO> listPerrr) {
		this.listPerrr = listPerrr;
	}

	
	
}
