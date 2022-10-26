package com.springbook.entity.cart;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.springbook.entity.book.BookEntity;

@Entity
@Table(name = "detailcart")
public class DetailCartEntity {
	
	@EmbeddedId
	private DetailCartId id = new DetailCartId();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("cartId")
	@JoinColumn(name = "cartid",nullable = false)
	private CartEntity cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("bookId")
	@JoinColumn(name = "bookid",nullable = false)
	private BookEntity book;
	
	@Column(name = "quantity",nullable = false)
	private int quantity;

	public DetailCartId getId() {
		return id;
	}

	public void setId(DetailCartId id) {
		this.id = id;
	}

	public CartEntity getCart() {
		return cart;
	}

	public void setCart(CartEntity cart) {
		this.cart = cart;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
