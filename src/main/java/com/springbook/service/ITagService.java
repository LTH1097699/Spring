package com.springbook.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;

import com.springbook.dto.book.TagDTO;

public interface ITagService {
	Map<String, String> findAll();
	int getTotalItems();
	List<TagDTO> findAll(Pageable pageable);
	TagDTO insert(TagDTO tagDTO);
	TagDTO update(TagDTO tagDTO);
	void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException;	
	TagDTO findById(Long id);
	List<TagDTO> search(String keyword,Pageable pageable);
	int totalItemSearch(String keyword);
}
