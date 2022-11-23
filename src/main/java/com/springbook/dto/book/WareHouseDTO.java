package com.springbook.dto.book;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.springbook.dto.AbstractDTO;

public class WareHouseDTO extends AbstractDTO<WareHouseDTO>{
	
	@NotEmpty(message = "Chưa nhập giá trị")
	private String code;
	
	@NotEmpty(message = "Chưa nhập giá trị")
	private String name;
	
	private String address;
	
	private List<BookDTO> books;
	
	private List<BookWareHouseDTO> bookWareHouseDTOs;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}

	public List<BookWareHouseDTO> getBookWareHouseDTOs() {
		return bookWareHouseDTOs;
	}

	public void setBookWareHouseDTOs(List<BookWareHouseDTO> bookWareHouseDTOs) {
		this.bookWareHouseDTOs = bookWareHouseDTOs;
	}
	
	
}
