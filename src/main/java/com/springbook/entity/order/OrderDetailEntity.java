package com.springbook.entity.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;
import com.springbook.entity.book.BookEntity;

@Entity
@Table(name = "order_detail")
public class OrderDetailEntity extends AbstractEntity {
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "orderid",nullable = false)
	private OrderEntity order;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "book_id",nullable = false)
	private BookEntity book;
	
	@Column(name="quantity", nullable = false)
	private int quantity;
	
	@Column(name="unit_price", nullable = false)
	private double unitPrice;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "idwarehouse")
	private Long idWareHouse;
	
	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getIdWareHouse() {
		return idWareHouse;
	}

	public void setIdWareHouse(Long idWareHouse) {
		this.idWareHouse = idWareHouse;
	}
	
	

}
