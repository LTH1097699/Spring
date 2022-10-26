package com.springbook.dto.book;

import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;

public class TagDTO extends AbstractDTO<TagDTO> {
	@NotEmpty(message = "Chưa nhập giá trị")
	private String codeTag;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String nameTag;

	public String getCodeTag() {
		return codeTag;
	}

	public void setCodeTag(String codeTag) {
		this.codeTag = codeTag;
	}

	public String getNameTag() {
		return nameTag;
	}

	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}

	
	
	
	
}
