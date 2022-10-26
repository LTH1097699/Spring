package com.springbook.dto.cart;

import com.springbook.dto.AbstractDTO;
import com.springbook.dto.book.BookDTO;

public class CartDTO extends AbstractDTO<CartDTO> {
	private int quantity;
	private double totalPrice;
	private BookDTO book;

	public CartDTO() {
	}

	public CartDTO(int quantity, double totalPrice, BookDTO book) {
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.book = book;
		
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BookDTO getBook() {
		return book;
	}

	public void setBook(BookDTO book) {
		this.book = book;
	}

	
}
