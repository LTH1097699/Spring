package com.springbook.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;

import com.springbook.dto.book.CategoryDTO;

public interface ICategoryService {
	Map<String, String> findAll();
	List<CategoryDTO> findAllList();
	
	int getTotalItems();
	List<CategoryDTO> findAll(Pageable pageable);
	CategoryDTO insert(CategoryDTO categoryDTO);
	CategoryDTO update(CategoryDTO categoryDTO);
	void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException;	
	CategoryDTO findById(Long id);
	List<CategoryDTO> search(String keyword,Pageable pageable);
	Map<Long, String> findAllId();
	int totalItemSearch(String keyword);
	
}
