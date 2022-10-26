package com.springbook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbook.conveter.user.ContactConverter;
import com.springbook.dto.book.AuthorDTO;
import com.springbook.dto.user.ContactDTO;
import com.springbook.entity.book.AuthorEntity;
import com.springbook.entity.book.PublisherEntity;
import com.springbook.entity.user.ContactEntity;
import com.springbook.repository.user.ContactRepository;
import com.springbook.repository.user.UserRepository;

@Service
public class ContactService {
	
	@Autowired
	private ContactConverter contactConverter;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository repository;
	
	public int getTotalItems() {	
		return (int) contactRepository.count();
	}
	
	public ContactDTO insert(ContactDTO dto){
		ContactEntity entity = contactConverter.toEntity(dto);
		contactRepository.save(entity);	
		return contactConverter.toDTO(entity);
	}

	public List<ContactDTO> findAll() {
		List<ContactEntity> entities  = contactRepository.findAll();
		return contactConverter.toDTOList(entities);
	}

	public List<ContactDTO> search(String keyword,Pageable pageable) {
		List<ContactEntity> entities = contactRepository.search(keyword);
		if(pageable!= null) {
			List<ContactEntity> pageList=entities.stream().skip(pageable.getPageNumber()*pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
			Page<ContactEntity> page = new PageImpl<>(pageList,pageable,entities.size());
			entities = page.getContent();
		}
		return contactConverter.toDTOList(entities);
	}
	public int totalItemSearch(String keyword) {
		List<ContactEntity> entities = contactRepository.search(keyword);
		return entities.size();
	}

	public List<ContactDTO> findAll(Pageable pageable) {
		List<ContactEntity> entities  = contactRepository.findAll(pageable).getContent();
		return contactConverter.toDTOList(entities);
	}

	public void delete(Long id) {
		contactRepository.delete(id);	
	}
	
}
