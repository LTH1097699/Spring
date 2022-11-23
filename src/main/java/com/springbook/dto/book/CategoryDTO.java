package com.springbook.dto.book;

import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;

public class CategoryDTO extends AbstractDTO<CategoryDTO> {
	@NotEmpty(message = "Chưa nhập giá trị")
	private String codeCategory;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String nameCategory;
	
	public String getCodeCategory() {
		return codeCategory;
	}
	public void setCodeCategory(String codeCategory) {
		this.codeCategory = codeCategory;
	}
	public String getNameCategory() {
		return nameCategory;
	}
	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}
	
	
}
