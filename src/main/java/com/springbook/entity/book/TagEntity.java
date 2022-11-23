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
@Table(name = "tag")
public class TagEntity extends AbstractEntity {
	@NaturalId(mutable=true)
	@Column(name = "codetag",nullable = false, unique = true)
	private String codeTag;
	
	@Column(name = "nametag",nullable = false)
	private String nameTag;
	
	@OneToMany(mappedBy = "tag",fetch = FetchType.LAZY)
	private List<BookEntity> books = new ArrayList<>();
	
	public String getCodeTag() {
		return codeTag;
	}

	public void setCodeTag(String codeTag) {
		this.codeTag = codeTag;
	}

	public String getNametag() {
		return nameTag;
	}

	public void setNametag(String nametag) {
		this.nameTag = nametag;
	}

	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}
	
	
	
}
