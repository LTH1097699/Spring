package com.springbook.entity.book;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.springbook.entity.AbstractEntity;
import com.springbook.entity.cart.DetailCartEntity;
import com.springbook.entity.order.OrderDetailEntity;

@Entity
@Table(name = "book")
public class BookEntity extends AbstractEntity {	
	@NaturalId(mutable = true)
	@Column(name = "bookcode",nullable = false, unique = true)
	private String bookCode;
	
	@Column(name = "title",nullable = false)
	private String title;

	@Column(name = "price",nullable = false)
	private Double price;
	
	@Column(name = "discount")
	private Double discount;

	@Column(name = "image")
	private String image;
	
	@Column(name = "qunatity")
	private int quantity;
	
	@Column(name = "releaseyear")
	private int releaseYear;
	
	@Column(name = "shortcontent", columnDefinition = "TEXT")
	private String shortContent;
	
	@Column(name = "context", columnDefinition = "TEXT")
	private String context;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private AuthorEntity author;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publisher_id")
	private PublisherEntity publisher;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private TagEntity tag;
	
	@Column(name = "status")
	private short status;
	
	@OneToMany(mappedBy = "book")
	private List<OrderDetailEntity> orderDetail = new ArrayList<>();
	
	@OneToMany(mappedBy = "book", orphanRemoval = true)
	private List<DetailCartEntity> detailCarts = new ArrayList<>();
	
	@OneToMany(mappedBy = "book",fetch = FetchType.LAZY, orphanRemoval = true)
	private List<CommentEntity> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "book",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,orphanRemoval = true)
	private List<BookWarehouseEntity> bookWarehouse = new ArrayList<>();
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getShortContent() {
		return shortContent;
	}

	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}

	public PublisherEntity getPublisher() {
		return publisher;
	}

	public void setPublisher(PublisherEntity publisher) {
		this.publisher = publisher;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	public List<OrderDetailEntity> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetailEntity> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public AuthorEntity getAuthor() {
		return author;
	}

	public void setAuthor(AuthorEntity authorEntity) {
		this.author = authorEntity;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public TagEntity getTag() {
		return tag;
	}

	public void setTag(TagEntity tag) {
		this.tag = tag;
	}

	public List<DetailCartEntity> getDetailCarts() {
		return detailCarts;
	}

	public void setDetailCarts(List<DetailCartEntity> detailCarts) {
		this.detailCarts = detailCarts;
	}

//	public StockEntity getStock() {
//		return stock;
//	}
//
//	public void setStock(StockEntity stock) {
//		this.stock = stock;
//	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<BookWarehouseEntity> getBookWarehouse() {
		return bookWarehouse;
	}

	public void setBookWarehouse(List<BookWarehouseEntity> bookWarehouse) {
		this.bookWarehouse = bookWarehouse;
	}

	
	
	
	

}
