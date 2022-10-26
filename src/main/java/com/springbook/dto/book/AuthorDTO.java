package com.springbook.dto.book;

import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;

public class AuthorDTO extends AbstractDTO<AuthorDTO> {
	@NotEmpty(message = "Chưa nhập giá trị")
	private String codeAuthor;
	
	@NotEmpty(message = "Chưa nhập giá trị")
	private String nameAuthor;
	
	public String getCodeAuthor() {
		return codeAuthor;
	}
	public void setCodeAuthor(String codeAuthor) {
		this.codeAuthor = codeAuthor;
	}
	public String getNameAuthor() {
		return nameAuthor;
	}
	public void setNameAuthor(String nameAuthor) {
		this.nameAuthor = nameAuthor;
	}
	
	
	
}
