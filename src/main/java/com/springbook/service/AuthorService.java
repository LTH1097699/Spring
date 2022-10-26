package com.springbook.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import com.springbook.entity.book.AuthorEntity;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springbook.dto.book.AuthorDTO;

public interface AuthorService {
	Map<String, String> findAll();
	int countTotalItems();
	Page<AuthorEntity> findAll(Pageable pageable);
	AuthorDTO insert(AuthorDTO authorDTO);
	AuthorDTO update(AuthorDTO authorDTO);
	AuthorDTO findById(Long id);
	void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException;
	List<AuthorDTO> search(String keyword,Pageable pageable);
	Map<Long, String> findAllId();
	int totalItemSearch(String keyword);

}
