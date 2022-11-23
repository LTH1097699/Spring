package com.springbook.conveter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springbook.conveter.book.BookConverter;
import com.springbook.dto.user.CommentDTO;
import com.springbook.dto.user.ContactDTO;
import com.springbook.entity.book.BookEntity;
import com.springbook.entity.book.CommentEntity;
import com.springbook.entity.user.ContactEntity;
import com.springbook.entity.user.UserEntity;
import com.springbook.repository.book.BookRepository;
import com.springbook.repository.user.UserRepository;

@Component
public class ContactConverter {

	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	public ContactDTO toDTO(ContactEntity entity) {
		ContactDTO dto = new ContactDTO();
		
		if(entity.getUserId()!=null) {
			dto.setUserId(userConverter.toDTO(entity.getUserId()));
		}
		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setEmail(entity.getEmail());
		dto.setName(entity.getNameUser());
		dto.setTitle(entity.getTitle());
		
		return dto;
	}
	
	public List<ContactDTO> toDTOList(List<ContactEntity> entities){
		List<ContactDTO> dtos = new ArrayList<>();
		for(ContactEntity entity: entities) {
			ContactDTO dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}
	
	public ContactEntity toEntity(ContactDTO dto) {
		ContactEntity entity = new ContactEntity();
		UserEntity userEntity = userRepository.findOneByEmail(dto.getEmail());
		if(userEntity != null) {
			entity.setUserId(userEntity);
		}
		
		entity.setContent(dto.getContent());
		entity.setEmail(dto.getEmail());
		entity.setNameUser(dto.getName());
		entity.setTitle(dto.getTitle());
		return entity;
	}
	
}
