package com.springbook.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;

import com.springbook.dto.book.AuthorDTO;

public interface IAuthorService {
	Map<String, String> findAll();
	int getTotalItems();
	List<AuthorDTO> findAll(Pageable pageable);
	AuthorDTO insert(AuthorDTO authorDTO);
	AuthorDTO update(AuthorDTO authorDTO);
	AuthorDTO findById(Long id);
	void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException;
	List<AuthorDTO> search(String keyword,Pageable pageable);
	Map<Long, String> findAllId();
	int totalItemSearch(String keyword);

}
