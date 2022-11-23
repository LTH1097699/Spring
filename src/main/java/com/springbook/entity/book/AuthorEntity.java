package com.springbook.entity.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.springbook.entity.AbstractEntity;

@Entity
@Table(name = "author")
public class AuthorEntity extends AbstractEntity {
	@NaturalId(mutable=true)
	@Column(name = "codeauthor",nullable = false, unique = true)
	private String codeAuthor;
	
	@Column(name = "nameAuthor",nullable = false)
	private String nameAuthor;
	
	@OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
	private List<BookEntity> books = new ArrayList<>();

	public String getCodeAuthor() {
		return codeAuthor;
	}

	public void setCodeAuthor(String codeAuthor) {
		this.codeAuthor = codeAuthor;
	}

	public String getNameAuthor() {
		return nameAuthor;
	}

	public void setNameAuthor(String nameAuthor) {
		this.nameAuthor = nameAuthor;
	}

	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}
	
	
}
