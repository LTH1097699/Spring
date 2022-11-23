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
@Table(name = "category")
public class CategoryEntity extends AbstractEntity {
	@NaturalId(mutable=true)
	@Column(name = "code_category",nullable = false, unique = true)
	private String codeCategory;
	
	@Column(name = "name_category",nullable = false)
	private String nameCategory;
	
	@OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
	private List<BookEntity> books = new ArrayList<>();
	
	public String getCodeCategory() {
		return codeCategory;
	}

	public void setCodeCategory(String codeCategory) {
		this.codeCategory = codeCategory;
	}

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}
	
	
}
