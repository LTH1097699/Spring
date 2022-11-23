package com.springbook.dto.order;

import java.util.List;

import com.springbook.dto.AbstractDTO;
import com.springbook.dto.book.BookDTO;
import com.springbook.dto.book.WareHouseDTO;

public class OrderDetailDTO extends AbstractDTO<OrderDetailDTO> {
		private BookDTO book;
		private OrderDTO order;
		private WareHouseDTO wareHouseDTO;
		private double unitPrice;
		private double totalPrice;
		private int quantity;
		private Long wareHouseId;
		private String wareHouseName;
		private String keyword;
		
		private Long orderId;
		
		private Long bookId;
		private List<WareHouseDTO> wareHouse;
		public Long getBookId() {
			return bookId;
		}
		public void setBookId(Long bookId) {
			this.bookId = bookId;
		}
		
		public BookDTO getBook() {
			return book;
		}
		public void setBook(BookDTO book) {
			this.book = book;
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
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public List<WareHouseDTO> getWareHouse() {
			return wareHouse;
		}
		public void setWareHouse(List<WareHouseDTO> wareHouse) {
			this.wareHouse = wareHouse;
		}
		public Long getWareHouseId() {
			return wareHouseId;
		}
		public void setWareHouseId(Long wareHouseId) {
			this.wareHouseId = wareHouseId;
		}
		public String getWareHouseName() {
			return wareHouseName;
		}
		public void setWareHouseName(String wareHouseName) {
			this.wareHouseName = wareHouseName;
		}
		public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public String getKeyword() {
			return keyword;
		}
		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
		public OrderDTO getOrder() {
			return order;
		}
		public void setOrder(OrderDTO order) {
			this.order = order;
		}
		public WareHouseDTO getWareHouseDTO() {
			return wareHouseDTO;
		}
		public void setWareHouseDTO(WareHouseDTO wareHouseDTO) {
			this.wareHouseDTO = wareHouseDTO;
		}


		
}
