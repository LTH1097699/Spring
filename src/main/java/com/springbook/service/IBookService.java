package com.springbook.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;

import com.springbook.dto.book.BookDTO;

public interface IBookService {
	List<BookDTO> findAll(Pageable pageable,short status);
	int getTotalItem();
	BookDTO findOneById(Long Id);
	BookDTO insert(BookDTO dto);
	BookDTO update(BookDTO dto);
	void delete(long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException;

	List<BookDTO> findByCodeTagOrderByModifiedDateDescLimit6();
	List<Long> updateQuantity(Long bookId, Long orderId);
	BookDTO search(String name, List<Long> categoryKey, List<Long> authorKey, List<Long> publisherKey, Pageable pageable);
	List<BookDTO> findAll(short status);
	int totalItemSearch(String keyword);
	List<BookDTO> searchInAdmin(String keyword, Pageable pageable);
}
