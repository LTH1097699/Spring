package com.springbook.dto.user;

import javax.validation.constraints.NotEmpty;

import com.springbook.dto.AbstractDTO;

public class ContactDTO  extends AbstractDTO<ContactDTO>{
	private UserDTO userId;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String title;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String email;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String name;
	@NotEmpty(message = "Chưa nhập giá trị")
	private String content;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public UserDTO getUserId() {
		return userId;
	}
	public void setUserId(UserDTO userId) {
		this.userId = userId;
	}
	
	
}
