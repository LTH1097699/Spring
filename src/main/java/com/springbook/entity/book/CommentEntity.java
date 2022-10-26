package com.springbook.entity.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.springbook.entity.AbstractEntity;
import com.springbook.entity.user.UserEntity;
@Entity
@Table(name = "commentt")
public class CommentEntity extends AbstractEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId",nullable = false)
	private BookEntity book;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId",nullable = false)
	private UserEntity user;
	
	@Column(name = "content", columnDefinition = "TEXT",nullable = false)
	private String content;
	

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	

}
