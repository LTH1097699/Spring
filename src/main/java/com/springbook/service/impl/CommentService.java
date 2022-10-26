package com.springbook.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbook.conveter.book.BookConverter;
import com.springbook.conveter.user.CommentConverter;
import com.springbook.conveter.user.UserConverter;
import com.springbook.dto.book.AuthorDTO;
import com.springbook.dto.book.BookDTO;
import com.springbook.dto.user.CommentDTO;
import com.springbook.dto.user.UserDTO;
import com.springbook.entity.book.AuthorEntity;
import com.springbook.entity.book.CommentEntity;
import com.springbook.repository.book.BookRepository;
import com.springbook.repository.user.CommentRepository;
import com.springbook.repository.user.UserRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommentConverter commentConverter;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookConverter bookConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConverter userConverter;
	
//	public Map<String, String> findAll() {
//		Map<String, String> authorMap = new HashMap<>();
//		
//		List<AuthorEntity> entities = authorRepository.findAll();
//		for(AuthorEntity entity: entities) {
//			authorMap.put(entity.getCodeAuthor(), entity.getNameAuthor());
//		}
//		return authorMap;
//	}

	
	public int getTotalItems(Long bookId) {
		
		return (int) commentRepository.findByBookId(bookId).size();
	}

	
	public List<CommentDTO> findAll(Pageable pageable,Long bookId) {
		List<CommentDTO> dtos  = new ArrayList<>();
		if(pageable!=null) {
			List<CommentEntity> entities = commentRepository.findByBookId(bookId,pageable);
			dtos = commentConverter.toDTOList(entities);
		}else {
			List<CommentEntity> entities = commentRepository.findByBookId(bookId);
			dtos = commentConverter.toDTOList(entities);
		}
		
		return dtos;
	}

	
	@Transactional
	public CommentDTO insert(String comment,Long bookId, Long userId){
		BookDTO bookDTO =bookConverter.toDTO(bookRepository.findOne(bookId)) ;
		CommentDTO commentDTO = new CommentDTO();
		if(userId != null) {
			UserDTO userDTO = userConverter.toDTO(userRepository.findOne(userId));
			commentDTO.setBook(bookDTO);
			commentDTO.setUser(userDTO);
			commentDTO.setContent(comment);
			
			
		}else {
			commentDTO.setBook(bookDTO);
			commentDTO.setContent(comment);
		}
		CommentEntity commentEntity = commentConverter.toEntity(commentDTO);
		return commentConverter.toDTO(commentRepository.save(commentEntity));
	}

	
//	@Transactional
//	public AuthorDTO update(AuthorDTO authorDTO) {
//		AuthorEntity oldAuthorEntity = authorRepository.findOne(authorDTO.getId());
//		AuthorEntity authorEntity = authorConverter.toEntity(oldAuthorEntity, authorDTO);
//		authorEntity = authorRepository.save(authorEntity);
//		return authorConverter.toDTO(authorEntity);
//	}

	
	public CommentDTO findById(Long id) {
		CommentEntity entity = commentRepository.findOne(id);
		return commentConverter.toDTO(entity);
	}

	
	public void delete(Long id) throws SQLIntegrityConstraintViolationException,ConstraintViolationException {
		commentRepository.delete(id);
	}
}
