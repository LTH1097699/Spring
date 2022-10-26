package com.springbook.dto.book;

import com.springbook.dto.AbstractDTO;

public class BookWareHouseDTO extends AbstractDTO<BookWareHouseDTO> {
	
	private int quantity;
	private BookDTO book;
	private WareHouseDTO wareHouse;
	
	private Long codeWareHouse;
	private String keyword;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public WareHouseDTO getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(WareHouseDTO wareHouse) {
		this.wareHouse = wareHouse;
	}
	public BookDTO getBook() {
		return book;
	}
	public void setBook(BookDTO book) {
		this.book = book;
	}
	public Long getCodeWareHouse() {
		return codeWareHouse;
	}
	public void setCodeWareHouse(Long codeWareHouse) {
		this.codeWareHouse = codeWareHouse;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	
}
