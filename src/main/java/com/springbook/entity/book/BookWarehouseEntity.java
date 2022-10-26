package com.springbook.entity.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;
@Entity
@Table(name = "book_warehouse")
public class BookWarehouseEntity extends AbstractEntity  {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookid",nullable = false)
	private BookEntity book;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouseid",nullable = false)
	private WareHouseEntity wareHouse;
	
	@Column(name = "quantity",nullable = false)
	private int quantity;

	public WareHouseEntity getWareHouse() {
		return wareHouse;
	}

	public void setWareHouse(WareHouseEntity wareHouse) {
		this.wareHouse = wareHouse;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}
	
	
}
