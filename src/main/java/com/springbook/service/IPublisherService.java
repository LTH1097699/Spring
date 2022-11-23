package com.springbook.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;

import com.springbook.dto.book.PublisherDTO;

public interface IPublisherService {
	Map<String, String> findAll();
	int getTotalItems();
	List<PublisherDTO> findAll(Pageable pageable);
	PublisherDTO insert(PublisherDTO publisherDTO);
	PublisherDTO update(PublisherDTO publisherDTO);
	PublisherDTO findById(Long id);
	void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException;
	List<PublisherDTO> search(String keyword,Pageable pageable);
	Map<Long, String> findAllId();
	int totalitemSearch(String keyword);
}
