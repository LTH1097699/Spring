package com.springbook.dto.user;

import java.util.Date;

import com.springbook.dto.AbstractDTO;
import com.springbook.dto.book.BookDTO;

public class CommentDTO extends AbstractDTO<CommentDTO> {
	
	private Long bookId;
	private Long userId;
	private String content;
	private Date dateComment;
	
	private BookDTO book;
	private UserDTO user;
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public BookDTO getBook() {
		return book;
	}
	public void setBook(BookDTO book) {
		this.book = book;
	}
	public Date getDateComment() {
		return dateComment;
	}
	public void setDateComment(Date dateComment) {
		this.dateComment = dateComment;
	}
	
	
}
