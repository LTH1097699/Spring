package com.springbook.dto.book;

import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;

public class PublisherDTO extends AbstractDTO<PublisherDTO> {
	@NotEmpty(message = "Chưa nhập giá trị")
	private String codePublisher;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String namePublisher;
	public String getCodePublisher() {
		return codePublisher;
	}
	public void setCodePublisher(String codePublisher) {
		this.codePublisher = codePublisher;
	}
	public String getNamePublisher() {
		return namePublisher;
	}
	public void setNamePublisher(String namePublisher) {
		this.namePublisher = namePublisher;
	}
}
