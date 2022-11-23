package com.springbook.conveter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springbook.conveter.book.BookConverter;
import com.springbook.dto.user.CommentDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.CommentEntity;
import com.springbook.entity.user.UserEntity;
import com.springbook.repository.book.BookRepository;
import com.springbook.repository.user.UserRepository;

@Component
public class CommentConverter {
	@Autowired
	private BookConverter bookConverter;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	public CommentDTO toDTO(CommentEntity entity) {
		CommentDTO dto = new CommentDTO();
		dto.setBook(bookConverter.toDTO(entity.getBook()));
		dto.setUser(userConverter.toDTO(entity.getUser()));
		dto.setContent(entity.getContent());
		dto.setDateComment(entity.getCreateDate());
		
		return dto;
	}
	
	public List<CommentDTO> toDTOList(List<CommentEntity> entities){
		List<CommentDTO> dtos = new ArrayList<>();
		for(CommentEntity entity: entities) {
			CommentDTO dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
	
	public CommentEntity toEntity(CommentDTO dto) {
		CommentEntity entity = new CommentEntity();
		BookEntity bookEntity = bookRepository.findOne(dto.getBook().getId());
		UserEntity userEntity = userRepository.findOne(dto.getUser().getId());
		entity.setBook(bookEntity);
		entity.setUser(userEntity);
		entity.setContent(dto.getContent());
		
		return entity;
	}
}
