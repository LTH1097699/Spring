package com.springbook.dto.user;

import com.springbook.dto.AbstractDTO;

public class PermissionDTO extends AbstractDTO<PermissionDTO> {
	private String code;
	
	private String url;
	
	public PermissionDTO(String code, String url) {
		this.code = code;
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
