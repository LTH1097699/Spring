package com.springbook.entity.book;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.springbook.entity.AbstractEntity;

@Entity
@Table(name = "publisher")
public class PublisherEntity extends AbstractEntity {
	@NaturalId(mutable=true)
	@Column(name = "code_publisher",nullable = false, unique = true)
	private String codePublisher;
	
	@Column(name = "name_publisher",nullable = false)
	private String namePublisher;
	
	@OneToMany(mappedBy = "publisher",fetch = FetchType.LAZY)
	private List<BookEntity> books = new ArrayList<>();

	public String getCodePublisher() {
		return codePublisher;
	}

	public void setCodePublisher(String codePublisher) {
		this.codePublisher = codePublisher;
	}

	public String getNamePublisher() {
		return namePublisher;
	}

	public void setNamePublisher(String namePublisher) {
		this.namePublisher = namePublisher;
	}

	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}

	

}
