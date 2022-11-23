package com.springbook.entity.cart;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
public class DetailCartId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cartid")
	private Long cartId;

	@Column(name = "bookid")
	private Long bookId;

	public DetailCartId(Long cartId, Long bookId) {
		this.cartId = cartId;
		this.bookId = bookId;
	}
	public DetailCartId() {
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	
	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
	     if (!(obj instanceof DetailCartId)) return false;
	     
	        DetailCartId that = (DetailCartId) obj;
	        return cartId != null && cartId.equals(that.getCartId()) && bookId !=null && bookId.equals(that.getBookId()) ;
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	

}
